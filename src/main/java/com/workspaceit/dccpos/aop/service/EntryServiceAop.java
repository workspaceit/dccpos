package com.workspaceit.dccpos.aop.service;


import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.service.ShipmentService;
import com.workspaceit.dccpos.service.accounting.EntryService;
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
    public void purchase(Object shipmentReturnObj){
        Shipment shipment = null;
        if(shipmentReturnObj instanceof Shipment){
            shipment =   ((Shipment)shipmentReturnObj);
        }

        if(shipment==null)return;



        Entry  entry = entryService.createShipmentEntryOnDue(shipment);

        shipment.setEntry(entry);
        this.shipmentService.update(shipment);

    }

}