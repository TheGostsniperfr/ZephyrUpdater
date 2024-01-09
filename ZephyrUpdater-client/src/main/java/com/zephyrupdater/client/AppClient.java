package com.zephyrupdater.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCList.*;
import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPEndPoint;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class AppClient {

    private static final int BUFFER_SIZE = 1024;

    private static Socket serverSocket = null;
    private static Thread listenToServerThread = null;
    private static Boolean isConnect = false;

    public static void launchClient() {
        Thread consoleListenerThread = new Thread(() -> listenToConsole());
        consoleListenerThread.start();
    }

    private static void listenToServer()
    {
        try {
            InputStream inputStream;
            try {
                inputStream = serverSocket.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(isConnect) {
                JsonObject dataHeader = ZUPManager.readJsonFromStream(inputStream);
                if(dataHeader == null){
                    disconnectFromServer(false);
                    break;
                }
                //System.out.println("dataHeader: " + dataHeader.toString());
                String dataStrType = CommonUtil.getValueFromJson(
                        ZUPKeys.STRUCT_TYPE.getKey(),
                        dataHeader,
                        String.class
                );

                ZUPTypes dataType = null;

                for (ZUPTypes cType : ZUPTypes.values()) {
                    if (cType.toString().equals(dataStrType)) {
                        dataType = cType;
                        break;
                    }
                }

                if (dataType == null) {
                    System.err.println("Unknown ZUPStruct type: " + dataStrType);
                    continue;
                }

                switch (dataType) {
                    case COMMAND:
                        executeServerCmd(new ZUPCommand(dataHeader));
                        break;

                    case FILE:
                        ZUPFile zupFile = new ZUPFile(dataHeader);
                        long totalBytes = 0;

                        //get multi chunks data
                        try {
                            byte[] buffer = new byte[BUFFER_SIZE];
                            int bytesRead;

                            FileOutputStream fileOutputStream = new FileOutputStream(zupFile.fileName);

                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                String serverResp = new String(buffer, 0, bytesRead);
                                if (serverResp.trim().equals(ZUPEndPoint.endPointFlag)) {
                                    break;
                                }

                                totalBytes += bytesRead;
                                fileOutputStream.write(buffer, 0, bytesRead);
                            }

                            fileOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (totalBytes != zupFile.dataSize) {
                                /*
                                    TODO

                                    Delete the file or create a tmp folder (if the data is corrupted)
                                */

                            System.err.println("Error: Data corrupted: " + (zupFile.dataSize - totalBytes) + " bytes missing");
                        }

                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private static void executeServerCmd(ZUPCommand zupCommand){
        System.out.println(zupCommand.content);
        ZUCTypes zucTypes = zupCommand.cmdStructType;
        JsonObject data = JsonParser.parseString(zupCommand.content).getAsJsonObject();

        switch (zucTypes){
            case MESSAGE:
                ZUCMessage zucMessage = new ZUCMessage(data);

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");

                System.out.println(dateFormat.format(currentDate)
                        + " from server"
                        + " -> " + zucMessage.content);

                break;
            case DISCONNECTION:
                disconnectFromServer(false);
                System.out.println("Server close.");
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static void listenToConsole(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String msg = scanner.nextLine();

            //input struct -> cmd [options] (ex: msg hello world -> print: hello world)

            msg = msg.replace("  ", " ");
            List<String> argv = List.of(msg.split(" "));

            if (argv.isEmpty()) {
                continue;
            }

            ZUCTypes cmdType = null;

            for (ZUCTypes zucTypes : ZUCTypes.values()) {
                if (argv.get(0).trim().equals(zucTypes.getCmdName())) {
                    cmdType = zucTypes;
                    break;
                }
            }

            if (cmdType == null) {
                System.out.println("Unknown Command: " + argv.get(0));
                System.out.println("Type help for help.");
                continue;
            }

            ZUCStruct zucStruct = null;

            switch (cmdType) {
                case LOGIN:
                    if (argv.size() < 3) {
                        ZUCLogin.printHelp();
                        continue;
                    }

                    zucStruct = new ZUCLogin(argv.get(1), argv.get(2));
                    break;
                case MESSAGE:
                    if (argv.size() < 2) {
                        ZUCMessage.printHelp();
                        continue;
                    }

                    StringBuilder stringBuilder = new StringBuilder(argv.get(1));

                    for (int i = 2; i < argv.size(); i++) {
                        stringBuilder.append(" ").append(argv.get(2));
                    }

                    zucStruct = new ZUCMessage(stringBuilder.toString());
                    break;
                case HELP:
                    ZUCHelp.printHelp();
                    continue;
                case CONNECT:
                    if(argv.size() <= 2){
                        ZUCConnect.printHelp();
                        continue;
                    }

                    if(serverSocket != null){
                        System.out.println("You are already connect to a server.");
                        continue;
                    }
                    int serverPort;

                    try {
                        serverPort = Integer.parseInt(argv.get(2));
                    } catch (NumberFormatException e){
                        System.out.println("Invalid port format.");
                        continue;
                    }

                    ZUCConnect zucConnect = new ZUCConnect(argv.get(1), serverPort);

                    for(int nbTry = 0; nbTry < CommonUtil.nbTry; nbTry++) {
                        try {
                            serverSocket = new Socket(CommonUtil.HOST, CommonUtil.SERVER_PORT); /* TODO */
                            System.out.println("Successful server connection.");
                            isConnect = true;
                            listenToServerThread = new Thread(() -> listenToServer());
                            listenToServerThread.start();
                            break;

                        } catch (Exception e) {
                            try {
                                Thread.sleep(CommonUtil.timeRetryInterval);
                            }catch (Exception eWait){
                                eWait.printStackTrace();
                            }
                        }
                    }
                    if(!isConnect){
                        System.out.println(
                                "No further information from server: "
                                        + zucConnect.host + ":"
                                        + zucConnect.serverPort
                        );
                    }
                    continue;
                case DISCONNECTION:
                    disconnectFromServer(true);

                    //Send to server (deco)
                    continue;
                case QUIT:
                    if(isConnect){
                        disconnectFromServer(true);
                    }

                    System.exit(0);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            if(serverSocket == null) {
                System.out.println("Please connect before to a server.");
                continue;
            }

            ZUPManager.sendData(serverSocket, new ZUPCommand(zucStruct));
        }
    }

    private static void disconnectFromServer(Boolean propagate){

        if(!isConnect){
            System.out.println("You are already disconnected.");
            return;
        }

        System.out.println("Disconnecting from the server...");

        try{
            if(listenToServerThread != null) {
                listenToServerThread.interrupt();
            }
            if (propagate) {
                ZUPManager.sendData(serverSocket, new ZUPCommand(new ZUCDisconnection()));
            }
            isConnect = false;
        } catch (Exception e){
            e.printStackTrace();
        }

        serverSocket = null;
        System.out.println("You have been disconnected.");
    }
}