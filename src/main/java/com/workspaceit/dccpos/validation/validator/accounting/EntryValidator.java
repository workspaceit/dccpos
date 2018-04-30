package com.workspaceit.dccpos.validation.validator.accounting;

import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.validation.form.accounting.LedgerEntryForm;
import com.workspaceit.dccpos.validation.form.accounting.TransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EntryValidator {
    private LedgerService ledgerService;

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    public void validatePayment(TransactionForm paymentForm, Errors errors){
        if( paymentForm.getBeneficial()==null || paymentForm.getCashOrBank() == null)return;

        Integer beneficialLedgerId =   paymentForm.getBeneficial().getLedgerId();
        LedgerEntryForm[] ledgerEntryForms = paymentForm.getCashOrBank();

        this.validateLedger("beneficial.ledgerId",beneficialLedgerId,errors);

        for(int i=0;i<ledgerEntryForms.length;i++){
            String fieldName = "cashOrBank["+i+"].ledgerId";

            Integer cashBankLedgerId =  ledgerEntryForms[i].getLedgerId();
            Double amount  = ledgerEntryForms[i].getAmount();

            if(!errors.hasFieldErrors(fieldName)){
                this.validateLedger(fieldName,cashBankLedgerId,errors);
            }

            if(!errors.hasFieldErrors(fieldName)){
               this.validateCashOrBankLedgerForPayment(fieldName,cashBankLedgerId,amount,errors);
            }
        }
    }
    public void validateReceipt(TransactionForm paymentForm, Errors errors){
        if( paymentForm.getBeneficial()==null || paymentForm.getCashOrBank() == null)return;

        Integer beneficialLedgerId =   paymentForm.getBeneficial().getLedgerId();
        LedgerEntryForm[] ledgerEntryForms = paymentForm.getCashOrBank();

        this.validateLedger("beneficial.ledgerId",beneficialLedgerId,errors);

        for(int i=0;i<ledgerEntryForms.length;i++){
            String fieldName = "cashOrBank["+i+"].ledgerId";

            Integer cashBankLedgerId =  ledgerEntryForms[i].getLedgerId();
            Double amount  = ledgerEntryForms[i].getAmount();

            if(!errors.hasFieldErrors(fieldName)){
                this.validateLedger(fieldName,cashBankLedgerId,errors);
            }

            if(!errors.hasFieldErrors(fieldName)){
                this.validateCashOrBankLedgerForReceipt(fieldName,cashBankLedgerId,amount,errors);
            }
        }
    }
    public void validateDcAmountBalance(TransactionForm paymentForm,Errors errors){
        if( paymentForm.getBeneficial()==null || paymentForm.getCashOrBank() == null)return;

        Integer beneficialLedgerId =   paymentForm.getBeneficial().getLedgerId();
        Double  beneficialAmount = paymentForm.getBeneficial().getAmount();
        LedgerEntryForm[] ledgerEntryForms = paymentForm.getCashOrBank();
        double totalDrAmount = (beneficialAmount!=null)?beneficialAmount:0;
        double totalCrAmount = 0;

        for(int i=0;i<ledgerEntryForms.length;i++){
            Double amount  = ledgerEntryForms[i].getAmount();
            totalCrAmount += (amount!=null)?amount:0;

        }

        if(totalDrAmount!=totalCrAmount) {
            String fieldName = "cashOrBank[0].ledgerId";
            errors.rejectValue(fieldName, "Payment amount is not equal");
        }
    }
    private void validateLedger(String fieldName,Integer ledgerId,Errors errors){
        if(ledgerId==null)return;


        Ledger ledger =  this.ledgerService.getById(ledgerId);
        if(ledger==null){
            errors.rejectValue(fieldName,"Ledger account not found by id:"+ledgerId);
        }
    }
    private void validateCashOrBankLedger(String fieldName,Integer ledgerId,Double amount,Errors errors){
        if(ledgerId==null || amount==null)return;

        boolean flag;
        Ledger ledger;
        try {
            ledger =  this.ledgerService.getLedger(ledgerId);
            flag = this.ledgerService.isCashOrBankAccount(ledger);

        } catch (EntityNotFound entityNotFound) {
            errors.rejectValue(fieldName,"Ledger account not found by id:"+ledgerId);
            return;
        }

        if(!flag){
            errors.rejectValue(fieldName,"Ledger account is not cash or bank");
            return;
        }


        // this.ledgerService.is
    }
    private void validateCashOrBankLedgerForPayment(String fieldName,Integer ledgerId,Double amount,Errors errors){
        this.validateCashOrBankLedger( fieldName, ledgerId, amount, errors);
        boolean flag;
        try {
            flag = this.ledgerService.isCashOrBankAccountWillOverDrawn(ledgerId,amount,false);
        } catch (EntityNotFound entityNotFound) {
            errors.rejectValue(fieldName,entityNotFound.getMessage());
            return;
        }
        if(flag){
            errors.rejectValue(fieldName,"Account don't have sufficient balance");
        }

       // this.ledgerService.is
    }
    private void validateCashOrBankLedgerForReceipt(String fieldName,Integer ledgerId,Double amount,Errors errors){
        this.validateCashOrBankLedger( fieldName, ledgerId, amount, errors);
    }

}
