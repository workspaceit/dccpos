package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.CompanyService;
import com.workspaceit.dccpos.validation.form.company.CompanyCreateForm;
import com.workspaceit.dccpos.validation.form.company.CompanyUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CompanyValidator {
    private CompanyService companyService;

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void validate(String prefix, CompanyCreateForm companyForm, Errors errors){

        prefix  = ValidationHelper.preparePrefix(prefix);

        if(!errors.hasFieldErrors(prefix+"email") && companyForm.getEmail()!=null && !companyForm.getEmail().trim().equals("")){
            this.validateEmail(companyForm.getEmail(),errors,prefix);
        }


    }
    public void validateUpdate(String prefix,int id, CompanyUpdateForm companyUpdateForm, Errors errors){

        prefix  = ValidationHelper.preparePrefix(prefix);


        if(!errors.hasFieldErrors(prefix+"email") && companyUpdateForm.getEmail()!=null && !companyUpdateForm.getEmail().trim().equals("")){
            this.validateEmail(companyUpdateForm.getEmail(),errors,prefix);
        }

    }
    public void validateEmail(String email ,Errors errors,String prefix){
        boolean flag = ValidationHelper.isValidEmailAddress(email);
        if(!flag){
            errors.rejectValue(prefix+"email","Email is not valid");
        }
    }


}
