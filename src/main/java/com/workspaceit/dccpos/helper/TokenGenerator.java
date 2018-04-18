package com.workspaceit.dccpos.helper;

import java.util.Random;

/**
 * Created by mi_rafi on 12/28/17.
 */
public class TokenGenerator {
    public static int generateTempFileToken(){
        Random rnd = new Random();
        int token = 1000000000 + rnd.nextInt(900000);
        return token;
    }
}
