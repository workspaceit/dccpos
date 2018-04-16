package com.workspaceit.pos.validation.form.wholesaler;

import com.workspaceit.pos.validation.form.company.CompanyCreateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class WholesalerCreateForm extends WholesalerForm {
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
