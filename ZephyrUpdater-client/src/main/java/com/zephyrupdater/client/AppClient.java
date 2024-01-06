package com.zephyrupdater.client;

import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCHelp;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCLogin;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCMessage;
import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
public class AppClient {

    private static final int BUFFER_SIZE = 1024;

    public static void launchClient() {

        try {
            Socket socket = new Socket(CommonUtil.HOST, CommonUtil.SERVER_PORT);
            System.out.println("Successful server connection.");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner scanner = new Scanner(System.in);
            while(true){
                String msg = scanner.nextLine();

                //input struct -> cmd [options] (ex: msg hello world -> print: hello world)

                msg = msg.replace("  ", " ");
                List<String> argv = List.of(msg.split(" "));

                if(argv.isEmpty()){
                    continue;
                }

                ZUCTypes cmdType = null;

                for(ZUCTypes zucTypes : ZUCTypes.values()){
                    if(argv.get(0).trim().equals(zucTypes.getCmdName())){
                        cmdType = zucTypes;
                        break;
                    }
                }

                if(cmdType == null){
                    System.out.println("Unknown Command: " + argv.get(0));
                    System.out.println("Type help for help.");
                    continue;
                }

                ZUCStruct dataToSend = null;

                switch (cmdType){
                    case LOGIN:
                        if(argv.size() < 3){
                            ZUCLogin.printHelp();
                            continue;
                        }

                        dataToSend = new ZUCLogin(argv.get(1), argv.get(2));
                        break;
                    case DISCONNECTION:
                        System.exit(0);
                        break;
                    case MESSAGE:
                        if(argv.size() < 2){
                            ZUCMessage.printHelp();
                            continue;
                        }

                        StringBuilder stringBuilder = new StringBuilder(argv.get(1));

                        for (int i = 2; i < argv.size(); i++){
                            stringBuilder.append(" ").append(argv.get(2));
                        }

                        dataToSend = new ZUCMessage(stringBuilder.toString());
                        break;
                    case HELP:
                        ZUCHelp.printHelp();
                        continue;
                    default:
                        throw new IllegalArgumentException();
                }

                ZUPManager.sendData(socket, new ZUPCommand(dataToSend));
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}