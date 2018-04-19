package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PurchaseValidator {
    private InventoryValidator inventoryValidator;
    private ShipmentValidator shipmentValidator;

    @Autowired
    public void setInventoryValidator(InventoryValidator inventoryValidator) {
        this.inventoryValidator = inventoryValidator;
    }

    @Autowired
    public void setShipmentValidator(ShipmentValidator shipmentValidator) {
        this.shipmentValidator = shipmentValidator;
    }

    public void validate(PurchaseForm purchaseForm, Errors errors){
        this.shipmentValidator.validate("shipment",purchaseForm.getShipment(),errors);
        this.inventoryValidator.validate("inventories",purchaseForm.getInventories(),errors);
    }

}