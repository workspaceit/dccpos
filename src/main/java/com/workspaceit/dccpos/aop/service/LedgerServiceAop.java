package com.workspaceit.dccpos.aop.service;


import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Component
public class LedgerServiceAop {
    private LedgerService ledgerService;


    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }


    /**
     * Only Payment and receipt
     * */
    @AfterReturning(pointcut = "execution(* com.workspaceit.dccpos.service.accounting.EntryService.createPaymentEntry(..))" +
                           " || execution(* com.workspaceit.dccpos.service.accounting.EntryService.createReceiptEntry(..))",returning="entryObj")
    public void create(JoinPoint joinPoint,Object entryObj) {
        System.out.println("APO");

        Entry entry = null;


        if(entryObj instanceof Entry){
            entry = (Entry)entryObj;
        }

        if(entry==null)return;

       List<EntryItem> entryItems =  entry.getEntryItems();

        this.ledgerService.resolveCurrentBalance(entryItems,true);

    }

}