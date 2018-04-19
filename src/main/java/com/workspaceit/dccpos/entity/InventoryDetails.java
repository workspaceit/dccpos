package com.workspaceit.dccpos.entity;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inventory_details")
public class InventoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "purchased_quantity")
    private int purchasedQuantity;

    @Column(name = "sold_quantity")
    private int soldQuantity;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private PRODUCT_CONDITION condition;

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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public PRODUCT_CONDITION getCondition() {
        return condition;
    }

    public void setCondition(PRODUCT_CONDITION condition) {
        this.condition = condition;
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

        InventoryDetails that = (InventoryDetails) o;

        if (id != that.id) return false;
        if (Double.compare(that.sellingPrice, sellingPrice) != 0) return false;
        if (purchasedQuantity != that.purchasedQuantity) return false;
        if (soldQuantity != that.soldQuantity) return false;
        if (availableQuantity != that.availableQuantity) return false;
        if (condition != that.condition) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(sellingPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + purchasedQuantity;
        result = 31 * result + soldQuantity;
        result = 31 * result + availableQuantity;
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}