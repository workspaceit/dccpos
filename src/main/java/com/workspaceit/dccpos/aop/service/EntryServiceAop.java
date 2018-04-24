package com.workspaceit.dccpos.aop.service;


import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.ShipmentService;
import com.workspaceit.dccpos.service.accounting.EntryService;
import com.workspaceit.dccpos.service.accounting.EntryTypeService;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class EntryServiceAop {
    private EntryService entryService;
    private ShipmentService shipmentService;


    @Autowired
    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }
    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }


    @AfterReturning(pointcut = "execution(* com.workspaceit.dccpos.service.ShipmentService.create(..))",returning="shipmentReturnObj")
    public void purchase(JoinPoint joinPoint, Object shipmentReturnObj) throws Exception {
        System.out.println("AOP HERE");
        Shipment shipment = null;
        Object[] args = joinPoint.getArgs();
        PurchaseForm purchaseForm = null;

        if(shipmentReturnObj instanceof Shipment){
            shipment =   ((Shipment)shipmentReturnObj);
        }

        if(args!=null && args.length==2){
            System.out.println("AOP HERE args");
            purchaseForm =(PurchaseForm)  args[1];
        }
        System.out.println("AOP HERE "+args.length);
        if(shipment==null || purchaseForm==null)return;
        System.out.println("AOP HERE");

        Entry  entry = entryService.createShipmentEntry(shipment,purchaseForm);

        shipment.setEntry(entry);
        this.shipmentService.update(shipment);

    }

}