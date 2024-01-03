package com.zephyrupdater.client;

import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
                ZUPManager.sendData(socket, new ZUPMessage(msg));

                if(msg.trim().equals("exit")){
                    System.exit(0);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}