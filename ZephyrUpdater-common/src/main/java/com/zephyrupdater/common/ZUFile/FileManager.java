package com.zephyrupdater.common.ZUFile;

import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPEndPointCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPFileCore;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public static int createFileFromStream(Socket socket, ZUPFileCore zupFile) {
        Path filePath = Paths.get(zupFile.fileName);
        long totalBytes = 0;

        try {
            if(!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            InputStream inputStream = socket.getInputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            BufferedOutputStream bufferedOutputStream =
                         new BufferedOutputStream(new FileOutputStream(zupFile.fileName));

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (Arrays.equals(buffer, ZUPEndPointCore.endPointFlagByte) |
                        totalBytes > zupFile.getDataSize()) {
                    break;
                }

                totalBytes += bytesRead;
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }


            if (totalBytes != zupFile.getDataSize()) {
                Files.deleteIfExists(filePath);
                System.err.println("Error: Data corrupted: " + (zupFile.getDataSize() - totalBytes) + " bytes missing");
                return -1;
            }

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
        ZUPManager.sendData(socket, new ZUPFileCore(file.getAbsolutePath(), file.length()));

        try {
            OutputStream outputStream = socket.getOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1){
                size += bytesRead;
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.write(ZUPEndPointCore.endPointFlagByte, 0, ZUPEndPointCore.endPointFlagByte.length);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static File findFolderStartingByWith(Path pathToSearch, String startName){
        File dir = new File(pathToSearch.toUri());
        if(dir.exists() && dir.isDirectory()) {
            for (File currentFile : dir.listFiles()) {
                if (currentFile.isDirectory() && currentFile.getName().startsWith(startName)){
                    return currentFile;
                }
            }
        }

        return null;
    }
}