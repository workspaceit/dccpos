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
    private InventoryCreateFrom[] inventory;

    @Valid
    @NotNull
    private ShipmentCreateForm shipment;

    public InventoryCreateFrom[] getInventory() {
        return inventory;
    }

    public void setInventory(InventoryCreateFrom[] inventory) {
        this.inventory = inventory;
    }

    public ShipmentCreateForm getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentCreateForm shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return "PurchaseForm{" +
                "inventory=" + Arrays.toString(inventory) +
                ", shipment=" + shipment +
                '}';
    }
}
