package com.zephyrupdater.server.utils;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.zephyrupdater.server.database.DBStruct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ServerUtils {
    public static void sendRespToConn(HttpExchange conn, int headerCode, String resp) throws IOException {
        conn.sendResponseHeaders(headerCode, resp.length());
        OutputStream respBody = conn.getResponseBody();
        respBody.write(resp.getBytes());
        respBody.close();
    }

    public static void sendJsonObjToConn(HttpExchange conn, JsonObject responseObj) throws IOException {
        Headers headers = conn.getResponseHeaders();
        headers.set("Content-Type", "application/json");
        sendRespToConn(conn, HttpURLConnection.HTTP_OK, responseObj.toString());
    }

    public static void sendFileToConn(HttpExchange conn, File file) throws IOException {
        if(!file.isFile() || !file.exists()){
            throw new RuntimeException("Invalid file.");
        }

        Headers headers = conn.getResponseHeaders();
        headers.set("Content-Type", "application/octet-stream");
        conn.sendResponseHeaders(200, file.length());

        try (OutputStream outputStream = conn.getResponseBody();
             FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static JsonObject getRequestObjFromConn(HttpExchange conn, DBStruct db) throws IOException {
        String requestMethod = conn.getRequestMethod();
        if(!requestMethod.equalsIgnoreCase("GET")){
            ServerUtils.sendRespToConn(conn, HttpURLConnection.HTTP_BAD_METHOD, "");
            return null;
        }

        List<String> argv = List.of(conn.getRequestURI().toString().split("/"));
        argv = argv.subList(2, argv.size());
        System.out.println("Receive request from: " + conn.getLocalAddress().getHostName() + " request: " + argv);

        JsonObject requestObj = DBStructUtils.getRequestObj(db, argv.getFirst());

        if(requestObj == null){
            sendRespToConn(conn, HttpURLConnection.HTTP_BAD_REQUEST, "");
            return null;
        }

        if(!DBStructUtils.isSharedRequest(requestObj)){
            sendRespToConn(conn, HttpURLConnection.HTTP_FORBIDDEN, "");
            return null;
        }

        return requestObj;
    }
}
