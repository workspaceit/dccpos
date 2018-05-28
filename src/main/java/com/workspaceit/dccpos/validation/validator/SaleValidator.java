package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.constant.SALE_TYPE;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.entity.Wholesaler;
import com.workspaceit.dccpos.service.PersonalInformationService;
import com.workspaceit.dccpos.service.WholesalerService;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoForm;
import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SaleValidator {
    private WholesalerService wholesalerService;
    private PersonalInformationService personalInformationService;

    private PersonalInfoValidator personalInfoValidator;

    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }

    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    @Autowired
    public void setPersonalInfoValidator(PersonalInfoValidator personalInfoValidator) {
        this.personalInfoValidator = personalInfoValidator;
    }

    public void validate(SaleForm saleForm, Errors error){
        SALE_TYPE type = saleForm.getType();

        if(error.hasFieldErrors("type"))return;

        switch (type){
            case WHOLESALE:
                this.validateWholesaler(saleForm.getWholesalerId(),error);
            case CONSUMER_SALE:
                this.validateConsumer(saleForm.getConsumerInfoId(),saleForm.getConsumerInfo(),error);
        }
    }
    private void validateWholesaler(Integer wholesalerId, Errors error){

        if(wholesalerId==null || wholesalerId==0) {
            error.rejectValue("wholesalerId", "Wholesaler required");
            return;
        }

        Wholesaler wholesaler =  this.wholesalerService.getById(wholesalerId);
        if(wholesaler==null){
            error.rejectValue("wholesalerId","Wholesaler not found by id : "+wholesalerId);
        }
    }
    private void validateConsumer(Integer consumerInfoId, PersonalInfoCreateForm consumerInfo, Errors error){

        if(consumerInfoId!=null){
            PersonalInformation personalInformation =  this.personalInformationService.getById(consumerInfoId);
            if(personalInformation==null){
                error.rejectValue("consumerInfoId","Consumer not found by id : "+consumerInfoId);
            }
        }


        if(consumerInfo==null && (consumerInfoId==null || consumerInfoId>0)) {
            error.rejectValue("consumerInfoId", "Consumer required");
        }
        if(consumerInfo!=null){
            this.validateConsumer(consumerInfo,error);
        }
    }
    private void validateConsumer(PersonalInfoCreateForm consumerInfo, Errors error){
        this.personalInfoValidator.validate("consumerInfo",consumerInfo,error);

    }
}