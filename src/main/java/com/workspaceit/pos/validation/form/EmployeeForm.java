package com.workspaceit.pos.validation.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EmployeeForm {
    @NotNull
    @Valid
    private PersonalInfoForm personalInfo;

    @NotBlank(message = "Emp Id Required is required")
    @Length(max = 50,message = "Value too large")
    private String employeeId;

    @NotNull(message = "Salary required")
    @Min(value = 0,message = "Salary can't be less then zero")
    private Float salary;


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public PersonalInfoForm getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoForm personalInfo) {
        this.personalInfo = personalInfo;
    }
}