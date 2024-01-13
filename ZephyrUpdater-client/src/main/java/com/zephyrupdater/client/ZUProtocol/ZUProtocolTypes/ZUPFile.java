package com.zephyrupdater.client.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPEndPointCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPFileCore;

import java.io.FileOutputStream;
import java.io.InputStream;


public class ZUPFile extends ZUPFileCore implements ZUPStruct {
    public ZUPFile(JsonObject dataHeader) {
        super(dataHeader);
    }

    private static final int BUFFER_SIZE = 1024;

    @Override
    public void execute() {
        long totalBytes = 0;

        //get multi chunks data
        try {
            InputStream inputStream = AppClient.getServerSocket().getInputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            FileOutputStream fileOutputStream = new FileOutputStream(this.fileName);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String serverResp = new String(buffer, 0, bytesRead);
                if (serverResp.trim().equals(ZUPEndPointCore.endPointFlag)) {
                    break;
                }

                totalBytes += bytesRead;
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (totalBytes != this.getDataSize()) {
                                /*
                                    TODO

                                    Delete the file or create a tmp folder (if the data is corrupted)
                                */

            System.err.println("Error: Data corrupted: " + (this.getDataSize() - totalBytes) + " bytes missing");
        }
    }
}
