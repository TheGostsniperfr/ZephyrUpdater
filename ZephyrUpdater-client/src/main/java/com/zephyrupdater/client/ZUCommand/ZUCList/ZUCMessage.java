package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCMessageCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ZUCMessage extends ZUCMessageCore implements ZUCStruct {


    public ZUCMessage(String message) {
        super(message);
    }

    public ZUCMessage(JsonObject data) {
        super(data);
    }

    @Override
    public void executeServerCmd() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");

        System.out.println(dateFormat.format(currentDate)
                + " from server"
                + " -> " + this.content);
    }

    public static void executeClientCmd(List<String> argv) {
        if (argv.size() < 2) {
            printHelp();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder(argv.get(1));

        for (int i = 2; i < argv.size(); i++) {
            stringBuilder.append(" ").append(argv.get(i));
        }

        ZUPManager.sendData(
                AppClient.getServerSocket(),
                new ZUPCommandCore(new ZUCMessage(stringBuilder.toString())));
    }

    public static void printHelp(){
        System.out.println(getStructType().getCmdAlias() + " [message]");
    }
}
