package com.workspaceit.dccpos.aop.service;


import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.service.ProductService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Aspect
@Component
public class ProductServiceAop {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    @AfterReturning(pointcut = "execution(* com.workspaceit.dccpos.service.ShipmentService.create(..))",returning="shipmentReturnObj")
    public void purchase(Object shipmentReturnObj){

        Shipment shipment =   ((Shipment)shipmentReturnObj);



        List<Inventory> inventories = shipment.getInventories();

        if(inventories==null || inventories.size()==0)return;

        this.productService.resolveProductProperties(inventories);



    }

}