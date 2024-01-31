package com.zephyrupdater.server.utils.commands;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.utils.commands.cmdlist.CmdPrintHelp;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CmdManager {
    private final ZephyrServerManager server;

    public CmdManager(ZephyrServerManager server){
        this.server = server;
        new Thread(this::listenToConsole).start();
    }
    public void listenToConsole(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            String cmd = scanner.nextLine();

            cmd = cmd.replace("  ", " ");
            List<String> argv = List.of(cmd.split(" "));

            if (argv.isEmpty()) {
                continue;
            }

            Class<? extends ICmd> cmdClass = findCmdByName(argv.get(0));

            if(cmdClass == null) {
                System.out.println(new CmdPrintHelp().getHelp());
            }

            try {
                cmdClass.getDeclaredConstructor().newInstance().execute(this.server, argv.subList(1, argv.size()));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static Class<? extends ICmd> findCmdByName(String cmdName){
        List<Class<? extends ICmd>> allClasses = getAllServerCmdClasses();

        for (Class<? extends ICmd> clazz : allClasses){
            try{
                ICmd instance = clazz.getDeclaredConstructor().newInstance();
                if(cmdName.trim().equals(instance.getCmdName())){
                    return clazz;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public static List<Class<? extends ICmd>> getAllServerCmdClasses() {
        Reflections reflections = new Reflections("com.zephyrupdater.server.utils.commands");

        Set<Class<? extends ICmd>> allClasses = reflections.getSubTypesOf(ICmd.class);

        return new ArrayList<>(allClasses);
    }
}
