package com.workspaceit.dccpos.validation.form.consumer;

import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;

import javax.validation.Valid;

public class ConsumerForm {

    private String consumerId;
    @Valid
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
