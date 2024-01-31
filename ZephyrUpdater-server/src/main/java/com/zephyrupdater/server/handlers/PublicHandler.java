package com.zephyrupdater.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Path;

public class PublicHandler implements HttpHandler {

    private final Path publicDirPath;
    public PublicHandler(Path publicDirPath){

        this.publicDirPath = publicDirPath;
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
