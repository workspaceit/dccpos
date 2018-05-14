package com.workspaceit.dccpos.helper;

public class TrackingIdGenerator {
    private static final String supplyPrefix = "SP";
    public static String getShipmentTrackingId(long id){
        return supplyPrefix+String.valueOf(id);
    }
}
