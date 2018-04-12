package com.workspaceit.pos.helper;

public class ValidationHelper {
    public static String preparePrefix(String prefix){
        if(prefix!=null && !prefix.trim().equals("")){
            prefix +=".";
        }else{
            prefix = "";
        }
        return prefix;
    }
}
