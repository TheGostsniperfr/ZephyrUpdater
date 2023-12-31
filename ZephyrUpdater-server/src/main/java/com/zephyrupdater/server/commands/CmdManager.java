package com.zephyrupdater.server.commands;

import com.zephyrupdater.server.AppServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.reflections.Reflections;
public class CmdManager {

    public static void listenToConsole(ServerSocket serverSocket) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            String cmd = scanner.nextLine();

            Class<? extends ServerCmd> clazz = findCmdByName(cmd);

            if(clazz == null){
                System.out.println("Unknown command.");
                continue;
            }

            try {
                clazz.getDeclaredConstructor().newInstance().execute();
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static List<Class<? extends ServerCmd>> getAllServerCmdClasses() {
        Reflections reflections = new Reflections("com.zephyrupdater.server.commands");

        Set<Class<? extends ServerCmd>> allClasses = reflections.getSubTypesOf(ServerCmd.class);

        return new ArrayList<>(allClasses);
    }

    public static Class<? extends ServerCmd> findCmdByName(String cmdName){
        List<Class<? extends ServerCmd>> allClasses = getAllServerCmdClasses();

        for (Class<? extends ServerCmd> clazz : allClasses){
            try{
                ServerCmd instance = clazz.getDeclaredConstructor().newInstance();

                if(cmdName.trim().equals(instance.getCmdName())){
                    return clazz;
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        return null;
    }

    public static void sendCmdToAllClients(String cmd) {
        for(Socket socket : AppServer.activeConnections){
            try{
                if (!socket.isClosed()) {
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(cmd.getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}