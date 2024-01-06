package com.zephyrupdater.common.ZUProtocol;

import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPEndPoint;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;

public enum ZUPTypes {
    COMMAND(ZUPCommand.class),
    FILE(ZUPFile.class),
    END_POINT(ZUPEndPoint.class);

    private final Class <? extends ZUPStruct> associateClass;

    ZUPTypes(Class <? extends ZUPStruct> associateClass) { this.associateClass = associateClass; }

    public Class <? extends ZUPStruct> getAssociateClassClass() { return this.associateClass; }
}
