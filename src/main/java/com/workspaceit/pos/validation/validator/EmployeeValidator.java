package com.workspaceit.pos.validation.validator;

import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.service.EmployeeService;
import com.workspaceit.pos.validation.form.EmployeeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EmployeeValidator {
    private PersonalInfoValidator personalInfoValidator;
    private EmployeeService employeeService;

    @Autowired
    public void setPersonalInfoValidator(PersonalInfoValidator personalInfoValidator) {
        this.personalInfoValidator = personalInfoValidator;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void validate(EmployeeForm employeeForm, Errors error){
        System.out.println(employeeForm.getPersonalInfo());
        this.validateEmployeeId(employeeForm.getEmployeeId(),error);
        this.personalInfoValidator.validate("personalInfo",employeeForm.getPersonalInfo(),error);
    }
    public void validateEmployeeId(String employeeId,Errors error){
        Employee employee =  this.employeeService.getByEmployeeId(employeeId);
        if(employee!=null){
            error.rejectValue("employeeId","Employee Id must be unique");
        }
    }
}