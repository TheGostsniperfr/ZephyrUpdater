package com.zephyrupdater.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static com.zephyrupdater.server.utils.serverUtils.sendRespToConn;

public class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange conn) throws IOException {
        String requestMethod = conn.getRequestMethod();

        if(requestMethod.equalsIgnoreCase("GET")){
            List<String> argv = List.of(conn.getRequestURI().toString().split("/"));
            argv = argv.subList(2, argv.size());
            System.out.println("list : " + argv);

            StringBuilder respString = new StringBuilder();
            argv.forEach(respString::append);
            System.out.println("Raw request: " + respString);

            sendRespToConn(conn, HttpURLConnection.HTTP_OK, respString.toString());
        } else {
            sendRespToConn(conn, HttpURLConnection.HTTP_BAD_METHOD, "");
        }
    }
}
