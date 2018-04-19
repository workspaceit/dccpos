package com.workspaceit.dccpos.validation.form.inventory;


import com.workspaceit.dccpos.validation.form.inventoryDetails.InventoryDetailsCreateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class InventoryCreateFrom extends InventoryFrom {
    @Valid
    @NotNull
    private InventoryDetailsCreateForm[] details;

    public InventoryDetailsCreateForm[] getDetails() {
        return details;
    }

    public void setDetails(InventoryDetailsCreateForm[] details) {
        this.details = details;
    }
}
