package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.util.purchase.InventoryFormUtil;
import com.workspaceit.dccpos.util.validation.PaymentLedgerFormUtil;
import com.workspaceit.dccpos.util.purchase.ShipmentFormUtil;
import com.workspaceit.dccpos.validation.form.accounting.PaymentLedgerForm;
import com.workspaceit.dccpos.validation.form.inventory.InventoryCreateFrom;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PurchaseValidator {
    private InventoryValidator inventoryValidator;
    private ShipmentValidator shipmentValidator;
    private PaymentLedgerValidator purchasePaymentValidator;

    private ShipmentFormUtil shipmentFormUtil;
    private InventoryFormUtil inventoryFormUtil;
    private PaymentLedgerFormUtil ledgerEntryFormUtil;

    @Autowired
    public void setInventoryValidator(InventoryValidator inventoryValidator) {
        this.inventoryValidator = inventoryValidator;
    }

    @Autowired
    public void setShipmentValidator(ShipmentValidator shipmentValidator) {
        this.shipmentValidator = shipmentValidator;
    }

    @Autowired
    public void setPurchasePaymentValidator(PaymentLedgerValidator purchasePaymentValidator) {
        this.purchasePaymentValidator = purchasePaymentValidator;
    }

    @Autowired
    public void setShipmentFormUtil(ShipmentFormUtil shipmentFormUtil) {
        this.shipmentFormUtil = shipmentFormUtil;
    }

    @Autowired
    public void setInventoryFormUtil(InventoryFormUtil inventoryFormUtil) {
        this.inventoryFormUtil = inventoryFormUtil;
    }


    @Autowired
    public void setLedgerEntryFormUtil(PaymentLedgerFormUtil ledgerEntryFormUtil) {
        this.ledgerEntryFormUtil = ledgerEntryFormUtil;
    }

    public void validate(PurchaseForm purchaseForm, Errors errors){
        boolean validatePaymentAmountFlag=true;
        boolean validateShipmentAmountFlag=true;

        ShipmentForm shipmentForm = purchaseForm.getShipment();
        PaymentLedgerForm shippingCostPaymentAccount =  purchaseForm.getShippingCostPaymentAccount();
        PaymentLedgerForm[] pricePaymentAccounts = purchaseForm.getProductPricePaymentAccount();
        InventoryCreateFrom[] inventoryCreateFroms =  purchaseForm.getInventories();

        this.shipmentValidator.validate("shipment",purchaseForm.getShipment(),errors);
        this.inventoryValidator.validate("inventories",purchaseForm.getInventories(),errors);
        this.purchasePaymentValidator.validatePayment("productPricePaymentAccount",purchaseForm.getProductPricePaymentAccount(),errors);
        this.purchasePaymentValidator.validateAccountPayment("shippingCostPaymentAccount",purchaseForm.getShippingCostPaymentAccount(),errors);




        if(pricePaymentAccounts==null || pricePaymentAccounts.length==0){
            validatePaymentAmountFlag=false;
        }
        if(inventoryCreateFroms==null || inventoryCreateFroms.length==0){
            validatePaymentAmountFlag=false;
        }


        if(shipmentForm==null || shippingCostPaymentAccount==null){
            validateShipmentAmountFlag = false;
        }

        if(validatePaymentAmountFlag){
            this.validatePaymentAmount(inventoryCreateFroms,pricePaymentAccounts,errors);
        }
        if(validateShipmentAmountFlag) {
            this.validateShipmentCost(shipmentForm,shippingCostPaymentAccount,errors);
        }
    }
    private void validatePaymentAmount(InventoryCreateFrom[] inventoryCreateFroms,
                                       PaymentLedgerForm[] ledgerEntryForms, Errors errors){
        double totalPrice =   this.inventoryFormUtil.getTotalProductPrice(inventoryCreateFroms);
        double totalPaidAmount = this.ledgerEntryFormUtil.sumAmount(ledgerEntryForms);


        if(totalPaidAmount>totalPrice){
          String amountFieldName = "productPricePaymentAccount[0]";
          errors.rejectValue(amountFieldName,"Payment amount is greater then product price");
        }

    }
    private void validateShipmentCost(ShipmentForm shipmentForm,
                                       PaymentLedgerForm ledgerEntryForm,
                                       Errors errors){
        double totalCost = this.shipmentFormUtil.getTotalCost(shipmentForm);
        double totalPaidAmount = ledgerEntryForm.getAmount();
        if(totalPaidAmount>totalCost){
            String amountFieldName = "shippingCostPaymentAccount";
            errors.rejectValue(amountFieldName,"Payment amount is greater then shipping cost price");
        }

    }
}