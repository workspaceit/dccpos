package com.workspaceit.pos.validation.validator;

import com.workspaceit.pos.helper.ValidationHelper;
import com.workspaceit.pos.validation.form.company.CompanyCreateForm;
import com.workspaceit.pos.validation.form.company.CompanyUpdateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CompanyValidator {

    public void validate(String prefix, CompanyCreateForm companyForm, Errors errors){

        prefix  = ValidationHelper.preparePrefix(prefix);

        if(!errors.hasFieldErrors(prefix+"email") && companyForm.getEmail()!=null && !companyForm.getEmail().trim().equals("")){
            this.validateEmail(companyForm.getEmail(),errors,prefix);
        }


    }
    public void validateUpdate(String prefix, CompanyUpdateForm companyUpdateForm, Errors errors){

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
