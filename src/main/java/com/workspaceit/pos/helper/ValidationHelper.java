package com.workspaceit.pos.helper;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ValidationHelper {
    public static String preparePrefix(String prefix){
        if(prefix!=null && !prefix.trim().equals("")){
            prefix +=".";
        }else{
            prefix = "";
        }
        return prefix;
    }
    public static boolean isValidEmailAddress(String email){

        try {
            InternetAddress emailAddress = new InternetAddress(email);
             emailAddress.validate();
             return true;
        } catch (AddressException e) {
            return false;
        }
    }


}
