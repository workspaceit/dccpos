package com.workspaceit.pos.validation.form.employee;

import com.workspaceit.pos.validation.form.personalIformation.PersonalInfoUpdateForm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EmployeeUpdateForm extends EmployeeForm{


    @Valid
    @NotNull
    private PersonalInfoUpdateForm personalInfo;

    public PersonalInfoUpdateForm getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoUpdateForm personalInfo) {
        this.personalInfo = personalInfo;
    }

}