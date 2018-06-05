package com.workspaceit.dccpos.dataModel;

import java.util.Date;
import java.util.List;

public class Invoice {
    private String invoiceTackingId;
    private InvoiceBilling billTo;
    private List<InvoiceDetails> details;
    private double discount;
    private Date issueDate;
    private double total;
    private double vat;
    private double paidOrReceive;
    private double due;

    public String getInvoiceTackingId() {
        return invoiceTackingId;
    }

    public void setInvoiceTackingId(String invoiceTackingId) {
        this.invoiceTackingId = invoiceTackingId;
    }

    public InvoiceBilling getBillTo() {
        return billTo;
    }

    public void setBillTo(InvoiceBilling billTo) {
        this.billTo = billTo;
    }

    public List<InvoiceDetails> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceDetails> details) {
        this.details = details;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getPaidOrReceive() {
        return paidOrReceive;
    }

    public void setPaidOrReceive(double paidOrReceive) {
        this.paidOrReceive = paidOrReceive;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }
}