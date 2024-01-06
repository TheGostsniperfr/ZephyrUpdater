package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCHelp extends ZUCStruct {
    public ZUCHelp(){
        this.structType = ZUCTypes.HELP;
    }
    public static void printHelp(){
        int n = 0;
        for(ZUCTypes zucTypes : ZUCTypes.values()){
            System.out.println(n++ + ": " + zucTypes.getCmdName());
        }
    }

    public static String getCmdName() {
        return "help";
    }

    @Override
    public String getJson() {
        return "";
    }
}