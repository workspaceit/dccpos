package com.workspaceit.dccpos.validation.form.consumer;

import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ConsumerForm {
    @NotBlank(message = "Consumer Id Required")
    private String consumerId;
    @Valid
    @NotNull(message = "Personal information required")
    private PersonalInfoCreateForm personalInfo;


    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public PersonalInfoCreateForm getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoCreateForm personalInfo) {
        this.personalInfo = personalInfo;
    }
}
