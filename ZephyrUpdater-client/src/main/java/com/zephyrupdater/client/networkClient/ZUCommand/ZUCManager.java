package com.zephyrupdater.client.networkClient.ZUCommand;

import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ZUCManager {
    public static Class<? extends ZUCStruct> getClassByAlias(String cmdAlias) {
        List<Class<? extends ZUCStruct>> allClasses = getAllZUCClasses();

        for (Class<? extends ZUCStruct> clazz : allClasses) {
            try {
                ZUCTypes structType = getStructType(clazz);

                if (structType != null && structType.getCmdAlias().equals(cmdAlias)) {
                    return clazz;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void executeClientCmd(Class<? extends ZUCStruct> clazz, List<String> argv) {
        try {
            Method executeClientCmdMethod = clazz.getMethod("executeClientCmd", List.class);
            executeClientCmdMethod.invoke(null, argv);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<Class<? extends ZUCStruct>> getAllZUCClasses(){
        Reflections reflections = new Reflections("com.zephyrupdater.client.networkClient.ZUCommand");

        Set<Class<? extends ZUCStruct>> allClasses = reflections.getSubTypesOf(ZUCStruct.class);

        return new ArrayList<>(allClasses);
    }

    private static ZUCTypes getStructType(Class<? extends ZUCStruct> clazz) {
        try {
            Method getStructTypeMethod = clazz.getMethod("getStructType");
            return (ZUCTypes) getStructTypeMethod.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
