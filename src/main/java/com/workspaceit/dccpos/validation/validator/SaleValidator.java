package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.constant.SALE_TYPE;
import com.workspaceit.dccpos.entity.Consumer;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.entity.Wholesaler;
import com.workspaceit.dccpos.service.ConsumerService;
import com.workspaceit.dccpos.service.PersonalInformationService;
import com.workspaceit.dccpos.service.WholesalerService;
import com.workspaceit.dccpos.util.validation.InventorySaleUtil;
import com.workspaceit.dccpos.util.validation.PaymentLedgerFormUtil;
import com.workspaceit.dccpos.util.validation.SaleFormUtil;
import com.workspaceit.dccpos.validation.form.consumer.ConsumerForm;
import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class SaleValidator {
    private WholesalerService wholesalerService;
    private ConsumerService consumerService;

    private ConsumerValidator consumerValidator;
    private InventoryValidator inventoryValidator;
    private PaymentLedgerValidator ledgerEntryValidator;
    private PaymentLedgerFormUtil paymentLedgerValidatorUtil;
    private InventorySaleUtil inventorySaleValidatorUtil;
    private SaleFormUtil saleValidatorUtil;

    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Autowired
    public void setConsumerValidator(ConsumerValidator consumerValidator) {
        this.consumerValidator = consumerValidator;
    }

    @Autowired
    public void setInventoryValidator(InventoryValidator inventoryValidator) {
        this.inventoryValidator = inventoryValidator;
    }

    @Autowired
    public void setLedgerEntryValidator(PaymentLedgerValidator ledgerEntryValidator) {
        this.ledgerEntryValidator = ledgerEntryValidator;
    }

    @Autowired
    public void setPaymentLedgerValidatorUtil(PaymentLedgerFormUtil paymentLedgerValidatorUtil) {
        this.paymentLedgerValidatorUtil = paymentLedgerValidatorUtil;
    }

    @Autowired
    public void setInventorySaleValidatorUtil(InventorySaleUtil inventorySaleValidatorUtil) {
        this.inventorySaleValidatorUtil = inventorySaleValidatorUtil;
    }

    @Autowired
    public void setSaleValidatorUtil(SaleFormUtil saleValidatorUtil) {
        this.saleValidatorUtil = saleValidatorUtil;
    }

    public void validate(SaleForm saleForm, Errors error){
        SALE_TYPE type = saleForm.getType();

        if(error.hasFieldErrors("type"))return;

        switch (type){
            case WHOLESALE:
                this.validateWholesaler(saleForm.getWholesalerId(),error);
                break;
            case CONSUMER_SALE:
                this.validateConsumer(saleForm.getConsumerInfoId(),saleForm.getConsumerInfo(),error);
                break;
        }
        this.inventoryValidator.validate("inventories",saleForm.getInventories(),error);
        this.ledgerEntryValidator.validateReceived("paymentAccount",saleForm.getPaymentAccount(),error);

        double discount = saleForm.getDiscount()!=null?saleForm.getDiscount():0;
        double totalInventoryPrice =  this.inventorySaleValidatorUtil.sumAmount(saleForm.getInventories());
        double totalPaymentAmount = this.paymentLedgerValidatorUtil.sumAmount(saleForm.getPaymentAccount());
        double totalPayablePrice = this.saleValidatorUtil.getTotalPayablePrice(saleForm);


        if(totalPaymentAmount > totalPayablePrice){
            error.rejectValue("paymentAccount[0].amount", "Paid amount is higher then total payable price");
            return;
        }
        if(discount > totalInventoryPrice){
            error.rejectValue("discount", "Discount amount is higher then inventory price");
            return;
        }

    }
    private void validateWholesaler(Integer wholesalerId, Errors error){

        if(wholesalerId==null || wholesalerId==0) {
            error.rejectValue("wholesalerId", "Wholesaler required");
            return;
        }

        Wholesaler wholesaler =  this.wholesalerService.getById(wholesalerId);
        if(wholesaler==null){
            error.rejectValue("wholesalerId","Wholesaler not found by id : "+wholesalerId);
        }
    }
    private void validateConsumer(Integer consumerInfoId, ConsumerForm consumerInfo, Errors error){

        /**
         * Ether consumerIfo for new consumer
         * Or
         * Existing consumerInfoId Required
        * */
        if(consumerInfo!=null){
            this.validateConsumer(consumerInfo,error);
            return;
        }

        if(consumerInfoId==null || consumerInfoId<=0){
            error.rejectValue("consumerInfoId", "Consumer required");
            return;
        }

        Consumer consumer =  this.consumerService.getById(consumerInfoId);
        if(consumer==null){
            error.rejectValue("consumerInfoId","Consumer not found by id : "+consumerInfoId);
        }

    }
    private void validateConsumer(ConsumerForm consumerInfo, Errors error){
        this.consumerValidator.validate("consumerInfo",consumerInfo,error);

    }
}