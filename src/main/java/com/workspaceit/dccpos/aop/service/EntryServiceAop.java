package com.workspaceit.dccpos.aop.service;


import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.entity.Sale;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.service.ShipmentService;
import com.workspaceit.dccpos.service.accounting.EntryService;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Component
public class EntryServiceAop {
    private EntryService entryService;
    private ShipmentService shipmentService;
    private LedgerService ledgerService;


    @Autowired
    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }
    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @AfterReturning(pointcut = "execution(* com.workspaceit.dccpos.service.ShipmentService.create(..))",returning="shipmentReturnObj")
    public void purchase(JoinPoint joinPoint, Object shipmentReturnObj) throws Exception {

        Shipment shipment = null;
        Object[] args = joinPoint.getArgs();
        PurchaseForm purchaseForm = null;

        if(shipmentReturnObj instanceof Shipment){
            shipment =   ((Shipment)shipmentReturnObj);
        }

        if(args!=null && args.length==2){

            purchaseForm =(PurchaseForm)  args[1];
        }

        if(shipment==null || purchaseForm==null)return;

        /**
         * Shipment related Accounting entry
         * */
        Entry  entry = this.entryService.createShipmentEntry(shipment,purchaseForm);
        double paidAmount = this.entryService.getTotalEntryItemsAmount(entry, GROUP_CODE.ASSET, ACCOUNTING_ENTRY.CR);

        shipment.setTotalPaid(paidAmount);
        shipment.setEntry(entry);
        this.shipmentService.update(shipment);

        /**
         * Calculate Current balance of ledger and update
         * */
        List<EntryItem> entryItems =  entry.getEntryItems();

        if(entryItems==null)return;

        this.ledgerService.resolveCurrentBalance(entryItems,true);
    }
    @AfterReturning(pointcut = "execution(* com.workspaceit.dccpos.service.SaleService.create(..))",returning="saleReturnObj")
    public void sale(JoinPoint joinPoint, Object saleReturnObj) throws Exception {

        Sale sale = null;
        if(saleReturnObj instanceof Sale){
            sale =   ((Sale)saleReturnObj);
        }


        if(sale==null)return;

      /*
        Entry  entry = this.entryService.createShipmentEntry(shipment,purchaseForm);
        double paidAmount = this.entryService.getTotalEntryItemsAmount(entry, GROUP_CODE.ASSET, ACCOUNTING_ENTRY.CR);

        shipment.setTotalPaid(paidAmount);
        shipment.setEntry(entry);
        this.shipmentService.update(shipment);

        *//**
         * Calculate Current balance of ledger and update
         * *//*
        List<EntryItem> entryItems =  entry.getEntryItems();

        if(entryItems==null)return;*/

       // this.ledgerService.resolveCurrentBalance(entryItems,true);
    }
}