package com.zephyrupdater.server.ZUCommand;

import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ZUCManager {

    public static Class<? extends ZUCStruct> getClassByType(ZUCTypes zucTypes) {
        List<Class<? extends ZUCStruct>> allClasses = getAllZUCClasses();

        for (Class<? extends ZUCStruct> clazz : allClasses) {
            try {
                ZUCTypes structType = getStructType(clazz);

                if (structType == zucTypes) {
                    return clazz;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    public static List<Class<? extends ZUCStruct>> getAllZUCClasses(){
        Reflections reflections = new Reflections("com.zephyrupdater.server.ZUCommand");

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
