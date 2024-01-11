package com.zephyrupdater.common.ZUFile;

import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPEndPoint;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class FileManager {

    private static final String TMP_EXT = ".TMP";
    private static final int BUFFER_SIZE = 1024;

    /**
     * Read stream and create the associate file.
     *
     * @param socket
     * @param zupFile
     *
     * @return -1 on error, else 0
     */
    public static int createFileFromStream(Socket socket, ZUPFile zupFile) {
        String tmpFilename = zupFile.fileName + TMP_EXT;
        Path filePath = Paths.get(zupFile.fileName);
        Path tmpFilePath = Paths.get(tmpFilename);
        long totalBytes = 0;

        try {
            Files.createDirectories(tmpFilePath.getParent());
            InputStream inputStream = socket.getInputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            BufferedOutputStream bufferedOutputStream =
                         new BufferedOutputStream(new FileOutputStream(tmpFilename));

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (Arrays.equals(buffer, ZUPEndPoint.endPointFlagByte) |
                        totalBytes > zupFile.dataSize) {
                    break;
                }

                totalBytes += bytesRead;
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }


            if (totalBytes != zupFile.dataSize) {
                Files.deleteIfExists(tmpFilePath);
                System.err.println("Error: Data corrupted: " + (zupFile.dataSize - totalBytes) + " bytes missing");
                return -1;
            }

            Files.move(tmpFilePath, filePath, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tmpFilePath);

            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void sendFileFromStream(Socket socket, File file){
        if(!file.exists()){
            System.err.println("File does not exist.");
            return;
        }

        long size = 0;
        ZUPManager.sendData(socket, new ZUPFile(file.getAbsolutePath(), file.length()));

        try {
            OutputStream outputStream = socket.getOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1){
                size += bytesRead;
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.write(ZUPEndPoint.endPointFlagByte, 0, ZUPEndPoint.endPointFlagByte.length);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}