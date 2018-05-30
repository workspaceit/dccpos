package com.workspaceit.dccpos.helper;

public class TrackingIdGenerator {
    private static final String shipmentPrefix = "SP";
    private static final String salePrefix = "SL";
    public static String getShipmentTrackingId(long id){
        return shipmentPrefix+String.valueOf(id);
    }
    public static String getSaleTrackingId(long id){
        return salePrefix+String.valueOf(id);
    }

}
