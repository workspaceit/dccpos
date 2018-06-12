package com.workspaceit.dccpos.validation.form.inventory;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;

public class InventorySearchForm {
    PRODUCT_CONDITION condition;

    public PRODUCT_CONDITION getCondition() {
        return condition;
    }

    public void setCondition(PRODUCT_CONDITION condition) {
        this.condition = condition;
    }
}
