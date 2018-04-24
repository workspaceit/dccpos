package com.workspaceit.dccpos.validation.form.purchase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.workspaceit.dccpos.validation.form.inventory.InventoryCreateFrom;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentCreateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseForm {
    @Valid
    @NotNull
    private InventoryCreateFrom[] inventories;

    @Valid
    @NotNull
    private ShipmentCreateForm shipment;

    @Valid
    private AccountPaymentForm[] productPricePaymentAccount;

    @Valid
    private AccountPaymentForm shippingCostPaymentAccount;




    public InventoryCreateFrom[] getInventories() {
        return inventories;
    }

    public void setInventories(InventoryCreateFrom[] inventories) {
        this.inventories = inventories;
    }

    public ShipmentCreateForm getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentCreateForm shipment) {
        this.shipment = shipment;
    }


    public AccountPaymentForm[] getProductPricePaymentAccount() {
        return productPricePaymentAccount;
    }

    public void setProductPricePaymentAccount(AccountPaymentForm[] productPricePaymentAccount) {
        this.productPricePaymentAccount = productPricePaymentAccount;
    }

    public AccountPaymentForm getShippingCostPaymentAccount() {
        return shippingCostPaymentAccount;
    }

    public void setShippingCostPaymentAccount(AccountPaymentForm shippingCostPaymentAccount) {
        this.shippingCostPaymentAccount = shippingCostPaymentAccount;
    }

    @Override
    public String toString() {
        return "PurchaseForm{" +
                "inventories=" + Arrays.toString(inventories) +
                ", productPricePaymentAccount=" + Arrays.toString(productPricePaymentAccount) +
                ", shippingCostPaymentAccount=" + shippingCostPaymentAccount +
                ", shipment=" + shipment +
                '}';
    }
}
