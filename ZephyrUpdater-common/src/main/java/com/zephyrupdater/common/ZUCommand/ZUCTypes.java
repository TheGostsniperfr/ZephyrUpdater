package com.zephyrupdater.common.ZUCommand;

import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCLogin;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCMessage;

public enum ZUCTypes {
    LOGIN(ZUCLogin.class),
    DISCONNECTION(ZUCDisconnection.class),
    MESSAGE(ZUCMessage.class);

    private final Class <? extends ZUCStruct> associateClass;

    ZUCTypes(Class <? extends ZUCStruct> associateClass) { this.associateClass = associateClass; }

    public Class <? extends  ZUCStruct> getAssociateClass() { return this.associateClass; }
}
