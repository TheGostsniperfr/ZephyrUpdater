package com.zephyrupdater.server.commands;


import org.reflections.Reflections;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
            }
        }
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
            }
        }

        return null;
    }

    public static List<Class<? extends ServerCmd>> getAllServerCmdClasses() {
        Reflections reflections = new Reflections("com.zephyrupdater.server.commands");

        Set<Class<? extends ServerCmd>> allClasses = reflections.getSubTypesOf(ServerCmd.class);

        return new ArrayList<>(allClasses);
    }
}