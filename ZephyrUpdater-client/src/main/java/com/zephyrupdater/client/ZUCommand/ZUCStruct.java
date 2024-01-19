package com.zephyrupdater.client.ZUCommand;

import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCList.*;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

import java.util.List;

public interface ZUCStruct extends ZUCStructCore {
    void executeServerCmd();

    static void executeClientCmd(List<String> argv) {
        ZUCTypes cmdType = getCmdTypeFromArg(argv);
        if (cmdType == null) return;

        if (AppClient.getServerSocket() == null
                && cmdType != ZUCTypes.CONNECT
                && cmdType != ZUCTypes.STOP
                && cmdType != ZUCTypes.HELP) {
            System.out.println("Please connect before to a server.");
            return;
        }

        switch (cmdType) {
            case LOGIN:
                ZUCLogin.executeClientCmd(argv);
                break;
            case MESSAGE:
                ZUCMessage.executeClientCmd(argv);
                break;
            case HELP:
                ZUCHelp.executeClientCmd(argv);
                break;
            case CONNECT:
                ZUCConnect.executeClientCmd(argv);
                break;
            case DISCONNECTION:
                ZUCDisconnection.executeClientCmd(argv);
                break;
            case STOP:
                ZUCStop.executeClientCmd(argv);
                break;
            case UPDATE:
                ZUCUpdate.executeClientCmd(argv);
                break;
            case GET_FILE:
                ZUCGetFile.executeClientCmd(argv);
                break;
            case GET_FILES:
                ZUCGetFiles.executeClientCmd(argv);
                break;
            default:
                throw new IllegalArgumentException(cmdType.toString());
        }
    }

    static ZUCTypes getCmdTypeFromArg(List<String> argv){
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
            return null;
        }

        return cmdType;
    }
}
