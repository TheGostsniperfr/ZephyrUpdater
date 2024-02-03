package com.zephyrupdater.server;

import com.sun.net.httpserver.HttpServer;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.server.database.PublicFilesDB;
import com.zephyrupdater.server.handlers.PublicHandler;
import com.zephyrupdater.server.handlers.RequestHandler;
import com.zephyrupdater.server.commands.CmdManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZephyrServerManager {
    private static final int SERVER_HTTP_PORT = 8080;
    private final HttpServer server;
    private final PublicFilesDB publicFilesDB;
    private final CmdManager cmdManager;
    private Path publicDirPath;
    private Path cacheDirPath;

    public ZephyrServerManager() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(SERVER_HTTP_PORT), 0);
            this.initServerDir();
            this.publicFilesDB = new PublicFilesDB(this.cacheDirPath);
            this.initServerContext();

            this.cmdManager = new CmdManager(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initServerContext(){
        this.server.createContext("/public/", new PublicHandler(this.publicDirPath));
        this.server.createContext("/request/", new RequestHandler(this.publicFilesDB));
    }

    private void initServerDir() {
        String path = ZephyrServerManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File serverClassFile = new File(path);
        this.publicDirPath = Paths.get(serverClassFile.getParentFile().getAbsolutePath()).resolve("public");
        this.cacheDirPath = Paths.get(serverClassFile.getParentFile().getAbsolutePath()).resolve("cache");

        FileUtils.createDirIfNotExist(this.publicDirPath);
        FileUtils.createDirIfNotExist(this.cacheDirPath);
    }

    public void start(){
        System.out.println("Starting server.");
        this.server.start();
    }

    public void stop(){
        System.out.println("Stopping server.");
        this.server.stop(3);
        System.out.println("Server is correctly stopped");
        System.exit(0);
    }

    public HttpServer getServer() {
        return server;
    }

    public PublicFilesDB getPublicFilesDB() {
        return publicFilesDB;
    }

    public Path getPublicDirPath() {
        return publicDirPath;
    }

    public Path getCacheDirPath() {
        return cacheDirPath;
    }

    public CmdManager getCmdManager() {
        return cmdManager;
    }
}
