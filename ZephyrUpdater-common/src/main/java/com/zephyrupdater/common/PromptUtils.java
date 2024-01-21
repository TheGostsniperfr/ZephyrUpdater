package com.zephyrupdater.common;

import java.util.Scanner;

public class PromptUtils {
    public static Boolean getUserChoice(String ask){
        String rep = getUserRep(ask + " [yes/no]").trim().toLowerCase();
        return rep.equals("yes") || rep.equals("y");
    }

    public static String getUserRep(String ask){
        System.out.println(ask);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
