package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dataModel.Invoice;
import com.workspaceit.dccpos.dataModel.InvoiceBilling;
import com.workspaceit.dccpos.dataModel.InvoiceBillingAddress;
import com.workspaceit.dccpos.dataModel.InvoiceDetails;
import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceGenerateService {
    private SaleService saleService;
    private ShipmentService shipmentService;

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }
    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    public Invoice generateSaleInvoice(long id) throws EntityNotFound{
        Sale sale =  this.saleService.getSale(id);

        Invoice invoice = new Invoice();
        InvoiceBilling billTo = new InvoiceBilling();
        InvoiceBillingAddress billingAddress = new InvoiceBillingAddress();
        List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();



        billTo.setAddress(billingAddress);

        switch (sale.getType()){
            case WHOLESALE:
               Wholesaler wholesaler =  sale.getWholesaler();
               Company company =  wholesaler.getCompany();
               Address wholesalerAddress =  company.getAddress();

               billTo.setName(company.getTitle());
               billingAddress.setFormattedAddress(wholesalerAddress.getFormattedAddress());
               break;
            case CONSUMER_SALE:
                PersonalInformation consumer = sale.getConsumer();
                Address consumerAddress =  consumer.getAddress();

                billTo.setName(consumer.getFullName());
                billingAddress.setFormattedAddress(consumerAddress.getFormattedAddress());
                break;

        }
        Set<SaleDetails> saleDetailsList  = sale.getSaleDetails();
        if(saleDetailsList==null){
            return invoice;
        }
        for(SaleDetails saleDetails : saleDetailsList){
            InvoiceDetails invoiceDetails  =  new InvoiceDetails();
            Inventory inventory =   saleDetails.getInventory();
            Product product =   inventory.getProduct();
            invoiceDetails.setProductName(product.getName());
            invoiceDetails.setQuantity(saleDetails.getQuantity());
            invoiceDetails.setPerQuantityPrice(saleDetails.getPerQuantityPrice());
            invoiceDetails.setTotalPrice(saleDetails.getTotalPrice());

            invoiceDetailsList.add(invoiceDetails);
        }




        invoice.setInvoiceTackingId(sale.getTrackingId());
        invoice.setDetails(invoiceDetailsList);

        invoice.setIssueDate(sale.getDate());
        invoice.setBillTo(billTo);
        invoice.setVat(sale.getVat());
        invoice.setDiscount(sale.getDiscount());
        invoice.setPaidOrReceive(sale.getTotalReceive());
        invoice.setDue(sale.getTotalDue());
        invoice.setTotal(sale.getTotalPrice());
        return invoice;
    }
}
