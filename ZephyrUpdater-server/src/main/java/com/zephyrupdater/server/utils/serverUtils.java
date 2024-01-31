package com.zephyrupdater.server.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class serverUtils {
    public static void sendRespToConn(HttpExchange conn, int headerCode, String resp) throws IOException {
        conn.sendResponseHeaders(headerCode, resp.length());
        OutputStream respBody = conn.getResponseBody();
        respBody.write(resp.getBytes());
        respBody.close();
    }
}
