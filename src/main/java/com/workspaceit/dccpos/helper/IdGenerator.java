package com.workspaceit.dccpos.helper;

public class IdGenerator {
    private static final String shipmentPrefix = "SP";
    private static final String salePrefix = "SL";
    private static final String consumerPrefix = "CON";

    public static String getShipmentTrackingId(long id){
        return shipmentPrefix+String.valueOf(id);
    }
    public static String getSaleTrackingId(long id){
        return salePrefix+String.valueOf(id);
    }
    public static String getConsumerId(long id){
        return consumerPrefix+String.valueOf(id);
    }
}
