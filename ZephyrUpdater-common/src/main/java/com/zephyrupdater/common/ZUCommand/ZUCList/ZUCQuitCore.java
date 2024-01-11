package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.zephyrupdater.common.ZUCommand.ZUCStructCore;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCQuitCore extends ZUCStructCore {

    public static String getCmdName(){
        return "quit";
    }

    @Override
    public String getJson() {
        return "ZUCQuit is a local cmd";
    }

    public ZUCQuitCore(){
        this.structType = ZUCTypes.QUIT;
    }
    public static void printHelp(){
        System.out.println("quit");
    }
}
