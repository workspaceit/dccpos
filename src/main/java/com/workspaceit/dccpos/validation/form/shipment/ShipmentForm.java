package com.workspaceit.dccpos.validation.form.shipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentForm {

    @Length(max = 100,message = "Value too large")
    private String trackingId;

    @NotNull(message = "Supplier Required")
    @Min(value = 1,message = "Supplier Required")
    private Integer supplierId;

    private Double cfCost;
    private Double carryingCost;
    private Double laborCost;
    private Double otherCost;

    /**
     * Receive Timestamp in milliseconds
     * */
    @NotNull(message = "PurchasedDate required")
    private Date purchaseDate;


    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Double getCfCost() {
        return cfCost;
    }

    public void setCfCost(Double cfCost) {
        this.cfCost = cfCost;
    }

    public Double getCarryingCost() {
        return carryingCost;
    }

    public void setCarryingCost(Double carryingCost) {
        this.carryingCost = carryingCost;
    }

    public Double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Double laborCost) {
        this.laborCost = laborCost;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "ShipmentForm{" +
                "trackingId='" + trackingId + '\'' +
                ", supplierId=" + supplierId +
                ", cfCost=" + cfCost +
                ", carryingCost=" + carryingCost +
                ", laborCost=" + laborCost +
                ", otherCost=" + otherCost +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
