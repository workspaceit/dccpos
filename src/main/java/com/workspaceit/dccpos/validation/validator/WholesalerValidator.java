package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Wholesaler;
import com.workspaceit.dccpos.service.WholesalerService;
import com.workspaceit.dccpos.validation.form.wholesaler.WholesalerCreateForm;
import com.workspaceit.dccpos.validation.form.wholesaler.WholesalerUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class WholesalerValidator {
    private CompanyValidator companyValidator;
    private WholesalerService wholesalerService;

    @Autowired
    public void setCompanyValidator(CompanyValidator companyValidator) {
        this.companyValidator = companyValidator;
    }


    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }

    public void validate(WholesalerCreateForm wholesalerCreateForm, Errors error){

        this.validateUniqueWholesalerId(wholesalerCreateForm.getWholesalerId(),error);

        if(wholesalerCreateForm.getCompany()!=null){
            this.companyValidator.validate("company",wholesalerCreateForm.getCompany(),error);
        }

    }
    public void validateUpdate(int id, WholesalerUpdateForm WholesalerUpdateForm, Errors error){
        this.validateWholesalerIdUsedByOthers(WholesalerUpdateForm.getWholesalerId(),id,error);
        if(WholesalerUpdateForm.getCompany()!=null){
            this.companyValidator.validateUpdate("company",WholesalerUpdateForm.getCompany(),error);
        }
    }
    public void validateUniqueWholesalerId(String employeeId, Errors error){
        Wholesaler Wholesaler =  this.wholesalerService.getByWholesalerId(employeeId);
        if(Wholesaler!=null){
            error.rejectValue("WholesalerId","Wholesaler Id already been used by : "+Wholesaler.getCompany().getTitle());
        }
    }
    public void validateWholesalerIdUsedByOthers(String employeeId, int id, Errors error){
        Wholesaler employee =  this.wholesalerService.getByWholesalerIdAndNotById(employeeId,id);
        if(employee!=null){
            error.rejectValue("WholesalerId","Wholesaler Id already been used by : "+employee.getCompany().getTitle());
        }
    }
}