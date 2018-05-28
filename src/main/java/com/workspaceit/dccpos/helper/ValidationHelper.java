package com.workspaceit.dccpos.helper;

import org.springframework.validation.Errors;

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

    public static void mergeError(Errors mainErrors, Errors errors){

    }
}
