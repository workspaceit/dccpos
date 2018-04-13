package com.workspaceit.pos.validation.form.supplier;

import com.workspaceit.pos.validation.form.company.CompanyUpdateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SupplierUpdateForm extends SupplierForm {
    @Valid
    @NotNull
    private CompanyUpdateForm company;

    public CompanyUpdateForm getCompany() {
        return company;
    }

    public void setCompany(CompanyUpdateForm company) {
        this.company = company;
    }
}
