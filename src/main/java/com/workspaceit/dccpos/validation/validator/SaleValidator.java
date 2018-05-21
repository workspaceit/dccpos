package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.constant.SALE_TYPE;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.entity.Wholesaler;
import com.workspaceit.dccpos.service.PersonalInformationService;
import com.workspaceit.dccpos.service.WholesalerService;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class SaleValidator {
    private WholesalerService wholesalerService;
    private PersonalInformationService personalInformationService;

    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }

    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    public void validate(SaleForm saleForm, Errors error){
        SALE_TYPE type = saleForm.getType();
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
    private void validateConsumer(Integer consumerId, PersonalInfoCreateForm consumerForm, Errors error){

        if(consumerId!=null){
            PersonalInformation personalInformation =  this.personalInformationService.getById(consumerId);
            if(personalInformation==null){
                error.rejectValue("consumerId","Consumer not found by id : "+consumerId);
            }
        }

        if(consumerForm==null && (consumerId==null || consumerId>0)) {
            error.rejectValue("consumerId", "Consumer required");
        }
    }
}