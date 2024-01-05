package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCDisconnection extends ZUCStruct {
    public String content;
    public ZUCDisconnection(){
        this.structType = ZUCTypes.DISCONNECTION;
        this.content = CommonUtil.getFormatCmd("Exit");
    }
}
