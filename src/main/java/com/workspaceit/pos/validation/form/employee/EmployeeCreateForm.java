package com.workspaceit.pos.validation.form.employee;

import com.workspaceit.pos.validation.form.authcredential.AuthCredentialCreateForm;
import com.workspaceit.pos.validation.form.personalIformation.PersonalInfoCreateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EmployeeCreateForm extends EmployeeForm{
    @Valid
    @NotNull
    private PersonalInfoCreateForm personalInfo;

    @NotNull
    @Valid
    private AuthCredentialCreateForm authCredential;


    public AuthCredentialCreateForm getAuthCredential() {
        return authCredential;
    }
    public void setAuthCredential(AuthCredentialCreateForm authCredential) {
        this.authCredential = authCredential;
    }

    public PersonalInfoCreateForm getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoCreateForm personalInfo) {
        this.personalInfo = personalInfo;
    }
}