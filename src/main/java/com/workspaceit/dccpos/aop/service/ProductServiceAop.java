package com.workspaceit.dccpos.aop.service;


import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.service.ProductService;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentForm;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Aspect
@Component
public class ProductServiceAop {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    @AfterReturning(pointcut = "execution(* com.workspaceit.dccpos.service.ShipmentService.create(..)) " +
            "|| execution(* com.workspaceit.dccpos.service.SaleService.create(..))",returning="returnedObj")
    public void purchase(Object returnedObj){

        if(returnedObj instanceof Shipment) {
            Shipment shipment = ((Shipment) returnedObj);


        }else if(returnedObj instanceof Sale){
            Sale sale = ((Sale) returnedObj);

        }

        this.productService.resolveProductProperties();
    }

}