package com.workspaceit.dccpos.validation.form.wholesaler;

import com.workspaceit.dccpos.validation.form.company.CompanyUpdateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class WholesalerUpdateForm extends WholesalerForm {
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
