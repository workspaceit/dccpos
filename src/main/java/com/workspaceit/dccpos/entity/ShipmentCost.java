package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "shipment_cost")
public class ShipmentCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shipment_id",referencedColumnName = "id")
    private Shipment shipment;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private SHIPMENT_COST name;

    @Column(name = "amount")
    private double amount;

    @JsonIgnore
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    public ShipmentCost() {

    }

    public ShipmentCost(SHIPMENT_COST name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public SHIPMENT_COST getName() {
        return name;
    }

    public void setName(SHIPMENT_COST name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

        ShipmentCost that = (ShipmentCost) o;

        if (id != that.id)
            return true;
        return false;
    }

    @Override
    public int hashCode() {

        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);

        return hcb.hashCode();
    }
}
