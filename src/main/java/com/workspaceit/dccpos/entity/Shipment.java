package com.workspaceit.dccpos.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tracking_id")
    private String trackingId;

    @ManyToOne
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private Supplier supplier;


    @OneToMany
    @JoinColumn(name = "shipment_id",referencedColumnName = "id")
    private List<Inventory> inventories;

    @Column(name = "cf_cost")
    private double cfCost;

    @Column(name = "carrying_cost")
    private double carryingCost;

    @Column(name = "labor_cost")
    private double laborCost;

    @Column(name = "other_cost")
    private double otherCost;

    @ManyToOne
    @JoinColumn(name = "purchased_by")
    private Employee purchasedBy;

    @Column(name = "purchased_date")
    private Date purchasedDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public double getCfCost() {
        return cfCost;
    }

    public void setCfCost(double cfCost) {
        this.cfCost = cfCost;
    }

    public double getCarryingCost() {
        return carryingCost;
    }

    public void setCarryingCost(double carryingCost) {
        this.carryingCost = carryingCost;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(double otherCost) {
        this.otherCost = otherCost;
    }

    public Employee getPurchasedBy() {
        return purchasedBy;
    }

    public void setPurchasedBy(Employee purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        if (id != shipment.id) return false;
        if (Double.compare(shipment.cfCost, cfCost) != 0) return false;
        if (Double.compare(shipment.carryingCost, carryingCost) != 0) return false;
        if (Double.compare(shipment.laborCost, laborCost) != 0) return false;
        if (Double.compare(shipment.otherCost, otherCost) != 0) return false;
        if (trackingId != null ? !trackingId.equals(shipment.trackingId) : shipment.trackingId != null) return false;
        if (supplier != null ? !supplier.equals(shipment.supplier) : shipment.supplier != null) return false;
        if (inventories != null ? !inventories.equals(shipment.inventories) : shipment.inventories != null)
            return false;
        if (purchasedBy != null ? !purchasedBy.equals(shipment.purchasedBy) : shipment.purchasedBy != null)
            return false;
        if (purchasedDate != null ? !purchasedDate.equals(shipment.purchasedDate) : shipment.purchasedDate != null)
            return false;
        return createdAt != null ? createdAt.equals(shipment.createdAt) : shipment.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (trackingId != null ? trackingId.hashCode() : 0);
        result = 31 * result + (supplier != null ? supplier.hashCode() : 0);
        result = 31 * result + (inventories != null ? inventories.hashCode() : 0);
        temp = Double.doubleToLongBits(cfCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carryingCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(laborCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(otherCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (purchasedBy != null ? purchasedBy.hashCode() : 0);
        result = 31 * result + (purchasedDate != null ? purchasedDate.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
