package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCHelpCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

import java.util.List;

public class ZUCHelp extends ZUCHelpCore implements ZUCStruct {
    @Override
    public void executeServerCmd() {

    }

    public static void executeClientCmd(List<String> argv){
        printHelp();
    }


    public static void printHelp(){
        int n = 0;
        for(ZUCTypes zucTypes : ZUCTypes.values()){
            System.out.println(n++ + ": " + zucTypes.getCmdName());
        }
    }

}
