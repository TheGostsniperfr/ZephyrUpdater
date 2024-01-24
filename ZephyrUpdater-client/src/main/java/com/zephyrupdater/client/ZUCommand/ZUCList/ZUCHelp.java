package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.zephyrupdater.client.ZUCommand.ZUCManager;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCHelpCore;

import java.util.List;

public class ZUCHelp extends ZUCHelpCore implements ZUCStruct {
    @Override
    public void executeServerCmd() {

    }

    public static void executeClientCmd(List<String> argv) {
        List<Class<? extends ZUCStruct>> allClasses = ZUCManager.getAllZUCClasses();

        for (Class<? extends ZUCStruct> clazz : allClasses) {
            try {
                clazz.getMethod("printHelp").invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void printHelp(){ }
}
