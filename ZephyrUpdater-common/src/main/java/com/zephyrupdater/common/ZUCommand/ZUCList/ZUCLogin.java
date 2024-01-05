package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCLogin extends ZUCStruct {
    public String id;
    public String password;

    public ZUCLogin(String id, String password){
        this.structType = ZUCTypes.LOGIN;
        this.id = id;
        this.password = password;
    }
}