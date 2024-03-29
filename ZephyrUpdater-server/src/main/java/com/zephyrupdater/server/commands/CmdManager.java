package com.zephyrupdater.server.commands;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.cmdlist.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CmdManager {
    private final ZephyrServerManager server;
    private final List<ICmd> registeredCmd;
    public CmdManager(ZephyrServerManager server){
        this.registeredCmd = new ArrayList<>();
        this.addServerCmd(new CmdAddModToModList());
        this.addServerCmd(new CmdAddRequest());
        this.addServerCmd(new CmdPrintHelp());
        this.addServerCmd(new CmdStartServer());
        this.addServerCmd(new CmdStopServer());
        this.addServerCmd(new CmdListRequest());
        this.addServerCmd(new CmdReloadDB());
        this.addServerCmd(new CmdSaveDB());
        this.addServerCmd(new CmdSetSharedState());

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

            ICmd cmdClass = findCmdByName(argv.getFirst());

            if(cmdClass == null) {
                System.out.println(new CmdPrintHelp().getHelp());
                continue;
            }

            try {
                cmdClass.execute(this.server, argv.subList(1, argv.size()));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ICmd findCmdByName(String cmdName){
        for(ICmd cmdClass : this.getAllServerCmdClasses()){
            if(cmdClass.getCmdName().equals(cmdName)){
                return cmdClass;
            }
        }

        return null;
    }

    public List<ICmd> getAllServerCmdClasses() {
        return this.registeredCmd;
    }

    public void addServerCmd(ICmd cmd){
        this.registeredCmd.add(cmd);
    }
}
