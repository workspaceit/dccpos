package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workspaceit.dccpos.config.PersistenceConfig;
import com.workspaceit.dccpos.constant.SALE_TYPE;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "employee_id",referencedColumnName = "id")
    private Employee soldBy;

    @ManyToOne
    @JoinColumn(name = "wholesaler_id",referencedColumnName = "id")
    private Wholesaler wholesaler;

    @ManyToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInformation consumer;

    @OneToMany
    @JoinColumn(name = "sale_id",referencedColumnName = "id")
    private Set<SaleDetails> saleDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SALE_TYPE type;

    @Column(name = "discount")
    private double discount;

    @Column(name = "vat")
    private double vat;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "total_returned_quantity")
    private int totalReturnedQuantity;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_due")
    private double totalDue;

    @Column(name = "total_receive")
    private double totalReceive;

    @Column(name = "total_refund_amount")
    private double totalRefundAmount;

    @Column(name = "total_refund_amount_paid")
    private double totalRefundAmountPaid;

    @Column(name = "total_refund_amount_due")
    private double totalRefundAmountDue;

    @Column(name = "note")
    private String note;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = PersistenceConfig.DateConfig.timeZone)
    @Column(name = "date")
    private Date date;



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

    public Employee getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(Employee soldBy) {
        this.soldBy = soldBy;
    }

    public Wholesaler getWholesaler() {
        return wholesaler;
    }

    public void setWholesaler(Wholesaler wholesaler) {
        this.wholesaler = wholesaler;
    }

    public PersonalInformation getConsumer() {
        return consumer;
    }

    public void setConsumer(PersonalInformation consumer) {
        this.consumer = consumer;
    }

    public Set<SaleDetails> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(Set<SaleDetails> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public SALE_TYPE getType() {
        return type;
    }

    public void setType(SALE_TYPE type) {
        this.type = type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalReturnedQuantity() {
        return totalReturnedQuantity;
    }

    public void setTotalReturnedQuantity(int totalReturnedQuantity) {
        this.totalReturnedQuantity = totalReturnedQuantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }

    public double getTotalReceive() {
        return totalReceive;
    }

    public void setTotalReceive(double totalReceive) {
        this.totalReceive = totalReceive;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public double getTotalRefundAmountPaid() {
        return totalRefundAmountPaid;
    }

    public void setTotalRefundAmountPaid(double totalRefundAmountPaid) {
        this.totalRefundAmountPaid = totalRefundAmountPaid;
    }

    public double getTotalRefundAmountDue() {
        return totalRefundAmountDue;
    }

    public void setTotalRefundAmountDue(double totalRefundAmountDue) {
        this.totalRefundAmountDue = totalRefundAmountDue;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

        Sale sale = (Sale) o;

        if (id != sale.id) return false;
        if (Double.compare(sale.discount, discount) != 0) return false;
        if (Double.compare(sale.vat, vat) != 0) return false;
        if (totalQuantity != sale.totalQuantity) return false;
        if (totalReturnedQuantity != sale.totalReturnedQuantity) return false;
        if (Double.compare(sale.totalPrice, totalPrice) != 0) return false;
        if (Double.compare(sale.totalDue, totalDue) != 0) return false;
        if (Double.compare(sale.totalReceive, totalReceive) != 0) return false;
        if (Double.compare(sale.totalRefundAmount, totalRefundAmount) != 0) return false;
        if (Double.compare(sale.totalRefundAmountPaid, totalRefundAmountPaid) != 0) return false;
        if (Double.compare(sale.totalRefundAmountDue, totalRefundAmountDue) != 0) return false;
        if (soldBy != null ? !soldBy.equals(sale.soldBy) : sale.soldBy != null) return false;
        if (wholesaler != null ? !wholesaler.equals(sale.wholesaler) : sale.wholesaler != null) return false;
        if (consumer != null ? !consumer.equals(sale.consumer) : sale.consumer != null) return false;
        if (saleDetails != null ? !saleDetails.equals(sale.saleDetails) : sale.saleDetails != null) return false;
        if (type != sale.type) return false;
        if (note != null ? !note.equals(sale.note) : sale.note != null) return false;
        if (date != null ? !date.equals(sale.date) : sale.date != null) return false;
        return createdAt != null ? createdAt.equals(sale.createdAt) : sale.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (soldBy != null ? soldBy.hashCode() : 0);
        result = 31 * result + (wholesaler != null ? wholesaler.hashCode() : 0);
        result = 31 * result + (consumer != null ? consumer.hashCode() : 0);
        result = 31 * result + (saleDetails != null ? saleDetails.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(discount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + totalQuantity;
        result = 31 * result + totalReturnedQuantity;
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalDue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalReceive);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalRefundAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalRefundAmountPaid);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalRefundAmountDue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}