package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCMessage extends ZUCStruct {
    public String content;
    public ZUCMessage(String message){
        this.structType = ZUCTypes.MESSAGE;
        this.content = message;
    }
}
