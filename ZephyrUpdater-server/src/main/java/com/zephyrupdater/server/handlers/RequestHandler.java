package com.zephyrupdater.server.handlers;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zephyrupdater.server.database.PublicFilesDB;
import com.zephyrupdater.server.utils.PublicFilesUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static com.zephyrupdater.server.utils.serverUtils.sendRespToConn;

public class RequestHandler implements HttpHandler {

    private final PublicFilesDB publicFilesDB;

    public RequestHandler(PublicFilesDB publicFilesDB){
        this.publicFilesDB = publicFilesDB;
    }
    @Override
    public void handle(HttpExchange conn) throws IOException {
        String requestMethod = conn.getRequestMethod();

        if(requestMethod.equalsIgnoreCase("GET")){
            List<String> argv = List.of(conn.getRequestURI().toString().split("/"));
            argv = argv.subList(2, argv.size());
            System.out.println("Receive request from: " + conn.getLocalAddress().getHostName() + " request: " + argv);

            JsonObject requestObj = PublicFilesUtils.getRequestObj(publicFilesDB, argv.getFirst());

            if(requestObj == null){
                sendRespToConn(conn, HttpURLConnection.HTTP_BAD_REQUEST, "");
                return;
            }

            if(!PublicFilesUtils.isPublicRequest(requestObj)){
                sendRespToConn(conn, HttpURLConnection.HTTP_FORBIDDEN, "");
                return;
            }

            JsonObject responseObj = PublicFilesUtils.getResponseFilesFromRequest(requestObj);
            if(responseObj == null){
                sendRespToConn(conn, HttpURLConnection.HTTP_INTERNAL_ERROR, "");
                return;
            }

            sendRespToConn(conn, HttpURLConnection.HTTP_OK, responseObj.toString());
        } else {
            sendRespToConn(conn, HttpURLConnection.HTTP_BAD_METHOD, "");
        }
    }
}
