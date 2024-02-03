package com.zephyrupdater.server.utils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServerUtils {
    public static void sendRespToConn(HttpExchange conn, int headerCode, String resp) throws IOException {
        conn.sendResponseHeaders(headerCode, resp.length());
        OutputStream respBody = conn.getResponseBody();
        respBody.write(resp.getBytes());
        respBody.close();
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
}
