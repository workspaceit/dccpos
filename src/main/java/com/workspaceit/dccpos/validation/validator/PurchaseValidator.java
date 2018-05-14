package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PurchaseValidator {
    private InventoryValidator inventoryValidator;
    private ShipmentValidator shipmentValidator;
    private PurchasePaymentValidator purchasePaymentValidator;



    @Autowired
    public void setInventoryValidator(InventoryValidator inventoryValidator) {
        this.inventoryValidator = inventoryValidator;
    }

    @Autowired
    public void setShipmentValidator(ShipmentValidator shipmentValidator) {
        this.shipmentValidator = shipmentValidator;
    }

    @Autowired
    public void setPurchasePaymentValidator(PurchasePaymentValidator purchasePaymentValidator) {
        this.purchasePaymentValidator = purchasePaymentValidator;
    }

    public void validate(PurchaseForm purchaseForm, Errors errors){
        this.shipmentValidator.validate("shipment",purchaseForm.getShipment(),errors);
        this.inventoryValidator.validate("inventories",purchaseForm.getInventories(),errors);
        this.purchasePaymentValidator.validate("productPricePaymentAccount",purchaseForm.getProductPricePaymentAccount(),errors);
        this.purchasePaymentValidator.validateAccountPayment("shippingCostPaymentAccount",purchaseForm.getShippingCostPaymentAccount(),0,errors);
    }

}