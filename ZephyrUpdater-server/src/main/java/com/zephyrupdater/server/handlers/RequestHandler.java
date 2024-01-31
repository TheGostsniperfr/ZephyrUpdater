package com.zephyrupdater.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zephyrupdater.server.database.PublicFilesRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static com.zephyrupdater.server.utils.serverUtils.sendRespToConn;

public class RequestHandler implements HttpHandler {

    private final PublicFilesRequest publicFilesRequest;

    public RequestHandler(PublicFilesRequest publicFilesRequest){
        this.publicFilesRequest = publicFilesRequest;
    }
    @Override
    public void handle(HttpExchange conn) throws IOException {
        String requestMethod = conn.getRequestMethod();

        if(requestMethod.equalsIgnoreCase("GET")){
            List<String> argv = List.of(conn.getRequestURI().toString().split("/"));
            argv = argv.subList(2, argv.size());
            System.out.println("list : " + argv);

            String response = this.publicFilesRequest.getResponseFromRequest(argv.getFirst());
            if(response == null){
                sendRespToConn(conn, HttpURLConnection.HTTP_BAD_REQUEST, "");
                return;
            }

            sendRespToConn(conn, HttpURLConnection.HTTP_OK, response);
        } else {
            sendRespToConn(conn, HttpURLConnection.HTTP_BAD_METHOD, "");
        }
    }
}
