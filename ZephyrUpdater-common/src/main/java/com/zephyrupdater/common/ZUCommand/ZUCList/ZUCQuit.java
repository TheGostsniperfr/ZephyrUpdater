package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCQuit extends ZUCStruct {

    public static String getCmdName(){
        return "quit";
    }

    @Override
    public String getJson() {
        return "ZUCQuit is a local cmd";
    }

    public ZUCQuit(){
        this.structType = ZUCTypes.QUIT;
    }
    public static void printHelp(){
        System.out.println("quit");
    }
}
