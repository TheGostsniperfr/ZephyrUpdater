package com.zephyrupdater.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.zephyrupdater.server.utils.ServerUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class PublicHandler implements HttpHandler {

    private final Path publicDirPath;
    public PublicHandler(Path publicDirPath){

        this.publicDirPath = publicDirPath;
    }
    @Override
    public void handle(HttpExchange conn) throws IOException {
        List<String> argv = List.of(conn.getRequestURI().toString().split("/"));
        argv = argv.subList(2, argv.size());

        Path fileAbsPath = this.publicDirPath;

        for(String subPath : argv){
            fileAbsPath = fileAbsPath.resolve(subPath);
        }

        if(!fileAbsPath.startsWith(this.publicDirPath)){
            // Check traversal attack
            ServerUtils.sendRespToConn(conn, HttpURLConnection.HTTP_FORBIDDEN, "");
            return;
        }

        System.out.println(conn.getLocalAddress().getHostName() + ": Requesting file: " + fileAbsPath);

        if(!Files.exists(fileAbsPath) || Files.isDirectory(fileAbsPath)){
            ServerUtils.sendRespToConn(conn, HttpURLConnection.HTTP_NOT_FOUND, "");
            return;
        }

        ServerUtils.sendFileToConn(conn, fileAbsPath.toFile());
    }
}
