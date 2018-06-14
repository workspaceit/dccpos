package com.workspaceit.dccpos.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberHelper {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static long getLong(Object obj){
        long count = 0;
        if(obj!=null){
            if(obj instanceof Long){
                count=(long)obj;
            }else if(obj instanceof Integer){
                Integer id = (Integer)obj;
                count=(long)id;
            }

        }
        return count;

    }
}
