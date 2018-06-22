package com.workspaceit.dccpos.validation.form.shipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentForm {

    @Length(max = 100,message = "Value too large")
    private String trackingId;

    @NotNull(message = "Supplier Required")
    @Min(value = 1,message = "Supplier Required")
    private Integer supplierId;

    private Map<SHIPMENT_COST,Double> cost;

    /**
     * Receive Timestamp in milliseconds
     * */
    @NotNull(message = "Purchased date required")
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

    public Map<SHIPMENT_COST, Double> getCost() {
        return cost;
    }

    public void setCost(Map<SHIPMENT_COST, Double> cost) {
        this.cost = cost;
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
                ", cost=" + cost +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
