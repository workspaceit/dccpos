package com.workspaceit.pos.validation.form.supplier;

import com.workspaceit.pos.validation.form.company.CompanyCreateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SupplierCreateForm extends SupplierForm {
    @Valid
    @NotNull
    private CompanyCreateForm company;

    public CompanyCreateForm getCompany() {
        return company;
    }

    public void setCompany(CompanyCreateForm company) {
        this.company = company;
    }
}
