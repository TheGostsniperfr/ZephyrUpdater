package com.zephyrupdater.client;

import com.zephyrupdater.common.CommonUtil;

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

            Thread readThread  = new Thread(() -> readResponses(inputStream));
            readThread.start();

            Scanner scanner = new Scanner(System.in);
            while(true){
                String msg = scanner.nextLine();
                outputStream.write(msg.getBytes(StandardCharsets.UTF_8));

                if(msg.trim().equals("exit")){
                    System.exit(0);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void readResponses(InputStream inputStream){
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String serverResp = new String(buffer, 0, bytesRead);
                if(serverResp.trim().equals("serverStop")){
                    System.out.println("Server close.");
                    System.exit(0);
                }

                System.out.println("Server Resp: " + serverResp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}