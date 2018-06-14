package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Consumer;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.ConsumerService;
import com.workspaceit.dccpos.validation.form.consumer.ConsumerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ConsumerValidator {
    private PersonalInfoValidator personalInfoValidator;
    private ConsumerService consumerService;

    @Autowired
    public void setPersonalInfoValidator(PersonalInfoValidator personalInfoValidator) {
        this.personalInfoValidator = personalInfoValidator;
    }

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    public void validate(String prefix , ConsumerForm consumerForm, Errors errors){
        prefix  = ValidationHelper.preparePrefix(prefix);
        this.personalInfoValidator.validate(prefix+"personalInfo",consumerForm.getPersonalInfo(),errors);
        this.validateConsumerId(prefix+"consumerId",consumerForm.getConsumerId(),errors);
    }
     private void validateConsumerId(String field ,String consumerId, Errors errors){
        if(consumerId==null || errors.hasFieldErrors(consumerId)  || consumerId.trim().equals("")){
            return;
        }
        Consumer consumer = this.consumerService.getByConsumerId(consumerId);
        if(consumer!=null){
            errors.rejectValue(field,"Consumer Id already exist");
        }
    }
}