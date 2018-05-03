package com.workspaceit.dccpos.validation.form.shipment;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ShipmentSearchForm {
    @DateTimeFormat(pattern="MM-dd-yyyy")
    private Date fromDate;

    @DateTimeFormat(pattern="MM-dd-yyyy")
    private Date toDate;

    private Integer supplierId;
    private String trackingId;


    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }
}