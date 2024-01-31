package com.zephyrupdater.server;

public class MainServer {
    public static void main(String[] args){
        ZephyrServerManager serverManager = new ZephyrServerManager();
        serverManager.start();
    }
}