package com.workspaceit.dccpos.entity;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sale_details")
public class SaleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sale_id",referencedColumnName = "id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "per_quantity_price")
    private double perQuantityPrice;

    @Column(name = "total_price")
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_condition")
    private PRODUCT_CONDITION productCondition;

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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPerQuantityPrice() {
        return perQuantityPrice;
    }

    public void setPerQuantityPrice(double perQuantityPrice) {
        this.perQuantityPrice = perQuantityPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PRODUCT_CONDITION getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(PRODUCT_CONDITION productCondition) {
        this.productCondition = productCondition;
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

        SaleDetails that = (SaleDetails) o;

        if (id != that.id) return false;
        if (quantity != that.quantity) return false;
        if (Double.compare(that.perQuantityPrice, perQuantityPrice) != 0) return false;
        if (Double.compare(that.totalPrice, totalPrice) != 0) return false;
        if (sale != null ? !sale.equals(that.sale) : that.sale != null) return false;
        if (inventory != null ? !inventory.equals(that.inventory) : that.inventory != null) return false;
        if (productCondition != that.productCondition) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (sale != null ? sale.hashCode() : 0);
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(perQuantityPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (productCondition != null ? productCondition.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}