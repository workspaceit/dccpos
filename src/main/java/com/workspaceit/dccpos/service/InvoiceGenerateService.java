package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import com.workspaceit.dccpos.dataModel.invoice.Invoice;
import com.workspaceit.dccpos.dataModel.invoice.InvoiceBilling;
import com.workspaceit.dccpos.dataModel.invoice.InvoiceBillingAddress;
import com.workspaceit.dccpos.dataModel.invoice.InvoiceDetails;
import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.exception.EntityNotFound;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InvoiceGenerateService {
    private SaleService saleService;
    private ShipmentService shipmentService;
    private ProductService productService;
    private ShopInformationService shopInformationService;

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }
    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setShopInformationService(ShopInformationService shopInformationService) {
        this.shopInformationService = shopInformationService;
    }



    public Invoice generateSaleInvoice(long id) throws EntityNotFound{
        Sale sale =  this.saleService.getSale(id);

        Invoice invoice = new Invoice();
        InvoiceBilling billTo = new InvoiceBilling();
        InvoiceBillingAddress billingAddress = new InvoiceBillingAddress();
        List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();

        ShopInformation shopInformation = this.shopInformationService.getShopInformation();

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

        billTo.setAddress(billingAddress);


        Set<SaleDetails> saleDetailsList  = sale.getSaleDetails();
        if(saleDetailsList==null){
            return invoice;
        }
        for(SaleDetails saleDetails : saleDetailsList){
            InvoiceDetails invoiceDetails  =  new InvoiceDetails();
            Inventory inventory =   saleDetails.getInventory();
            Product product =   inventory.getProduct();

            if(!Hibernate.isInitialized(product))
                product =  this.productService.getByInventoryId(inventory.getId());

            invoiceDetails.setProductName(product.getName());
            invoiceDetails.setQuantity(saleDetails.getQuantity());
            invoiceDetails.setPerQuantityPrice(saleDetails.getPerQuantityPrice());
            invoiceDetails.setTotalPrice(saleDetails.getTotalPrice());

            invoiceDetailsList.add(invoiceDetails);
        }



        invoice.setShopInformation(shopInformation);
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
    public Invoice generateShipmentInvoice(long id) throws EntityNotFound {
        Invoice invoice = new Invoice();
        InvoiceBilling billTo = new InvoiceBilling();
        InvoiceBillingAddress billingAddress = new InvoiceBillingAddress();
        List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();
        Map<SHIPMENT_COST,Double> invoiceShipmentCost = new HashMap<>();
        Shipment shipment = this.shipmentService.getShipment(id);
        Map<SHIPMENT_COST,ShipmentCost> shipmentCosts = shipment.getCosts();
        Set<SHIPMENT_COST> keySet = shipmentCosts.keySet();

        ShopInformation shopInformation = this.shopInformationService.getShopInformation();

        Supplier supplier = shipment.getSupplier();
        Company company = supplier.getCompany();
        Address companyAddress = company.getAddress();

        billTo.setName(company.getTitle());
        billingAddress.setFormattedAddress(companyAddress.getFormattedAddress());
        billTo.setAddress(billingAddress);


        for(SHIPMENT_COST key :keySet){
            ShipmentCost shipmentCost = shipmentCosts.get(key);
            invoiceShipmentCost.put(key,shipmentCost.getAmount());
        }


        List<Inventory> inventories =  shipment.getInventories();

        for(Inventory inventory :inventories){
            InvoiceDetails invoiceDetails  =  new InvoiceDetails();
            Product product  = inventory.getProduct();

            if(!Hibernate.isInitialized(product))
                product = this.productService.getByInventoryId(inventory.getId());

            invoiceDetails.setProductName(product.getName());
            invoiceDetails.setQuantity(inventory.getPurchaseQuantity());
            invoiceDetails.setPerQuantityPrice(inventory.getPurchasePrice());
            invoiceDetails.setTotalPrice(inventory.getPurchasePrice()*inventory.getPurchaseQuantity());

            invoiceDetailsList.add(invoiceDetails);
        }

        invoice.setShopInformation(shopInformation);
        invoice.setInvoiceTackingId(shipment.getTrackingId());
        invoice.setDetails(invoiceDetailsList);
        invoice.setShipmentCost(invoiceShipmentCost);
        invoice.setIssueDate(shipment.getPurchasedDate());
        invoice.setBillTo(billTo);
        invoice.setPaidOrReceive(shipment.getTotalPaid());
        invoice.setTotal(shipment.getTotalProductPrice());
        return invoice;

    }
    public Invoice getInvoiceByTrackingId(String trackingId) throws EntityNotFound {
        Sale sale = this.saleService.getByTrackingId(trackingId,false);
        Shipment shipment = this.shipmentService.getByTrackingId(trackingId,false);
        Invoice invoice;

        if(sale!=null)
            invoice = this.generateSaleInvoice(sale.getId());
        else if(shipment!=null)
            invoice = this.generateShipmentInvoice(shipment.getId());
        else
            throw new EntityNotFound("Invoice can't be generated by tracking id:"+trackingId);

        return invoice;
    }
}
