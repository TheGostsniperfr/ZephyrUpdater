package com.zephyrupdater.server;

import com.sun.net.httpserver.HttpServer;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.server.commands.CmdManager;
import com.zephyrupdater.server.database.FilesDB;
import com.zephyrupdater.server.database.ModListDB;
import com.zephyrupdater.server.handlers.FilesHandler;
import com.zephyrupdater.server.handlers.ModListHandler;
import com.zephyrupdater.server.handlers.PublicHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZephyrServerManager {
    private static final int SERVER_HTTP_PORT = 8080;
    private final HttpServer server;
    private final FilesDB filesDB;
    private final ModListDB modListDB;
    private final CmdManager cmdManager;
    private Path publicDirPath;
    private Path cacheDirPath;

    public ZephyrServerManager() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(SERVER_HTTP_PORT), 0);
            this.initServerDir();
            this.filesDB = new FilesDB(this.cacheDirPath);
            this.modListDB = new ModListDB(this.cacheDirPath);
            this.initServerContext();

            this.cmdManager = new CmdManager(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initServerContext(){
        this.server.createContext("/public/", new PublicHandler(this.publicDirPath)); //base url of public repo for download files
        this.server.createContext("/files/", new FilesHandler(this.filesDB)); //base url for request files
        this.server.createContext("/modlist/", new ModListHandler(this.modListDB)); //base url for request a modList
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

        System.out.println("Saving files database.");
        this.filesDB.saveDB();
        System.out.println("Saving modList database.");
        this.modListDB.saveDB();

        System.out.println("Stopping http server.");
        this.server.stop(3);

        System.out.println("Server is correctly stopped.");
        System.exit(0);
    }

    public HttpServer getServer() {
        return server;
    }

    public FilesDB getFilesDB() {
        return filesDB;
    }
    public ModListDB getModListDB() {
        return modListDB;
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
