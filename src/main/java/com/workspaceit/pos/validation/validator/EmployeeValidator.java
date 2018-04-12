package com.workspaceit.pos.validation.validator;

import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.service.EmployeeService;
import com.workspaceit.pos.validation.form.employee.EmployeeCreateForm;
import com.workspaceit.pos.validation.form.employee.EmployeeUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EmployeeValidator {
    private PersonalInfoValidator personalInfoValidator;
    private AuthCredentialValidator authCredentialValidator;
    private EmployeeService employeeService;

    @Autowired
    public void setPersonalInfoValidator(PersonalInfoValidator personalInfoValidator) {
        this.personalInfoValidator = personalInfoValidator;
    }

    @Autowired
    public void setAuthCredentialValidator(AuthCredentialValidator authCredentialValidator) {
        this.authCredentialValidator = authCredentialValidator;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void validate(EmployeeCreateForm employeeForm, Errors error){

        this.validateUniqueEmployeeId(employeeForm.getEmployeeId(),error);

        if(employeeForm.getPersonalInfo()!=null){
            this.personalInfoValidator.validate("personalInfo",employeeForm.getPersonalInfo(),error);
        }

        if(employeeForm.getAuthCredential()!=null){
            this.authCredentialValidator.validate("authCredential",employeeForm.getAuthCredential(),error);
        }
    }
    public void validateUpdate(int id, EmployeeUpdateForm employeeForm, Errors error){
        this.validateEmployeeIdUsedByOthers(employeeForm.getEmployeeId(),id,error);
        if(employeeForm.getPersonalInfo()!=null){
            this.personalInfoValidator.validateUpdate("personalInfo",employeeForm.getPersonalInfo(),error);
        }
    }
    public void validateUniqueEmployeeId(String employeeId, Errors error){
        Employee employee =  this.employeeService.getByEmployeeId(employeeId);
        if(employee!=null){
            error.rejectValue("employeeId","Employee Id already been used by employee : "+employee.getPersonalInformation().getFullName());
        }
    }
    public void validateEmployeeIdUsedByOthers(String employeeId,int id, Errors error){
        Employee employee =  this.employeeService.getByEmployeeIdAndNotById(employeeId,id);
        if(employee!=null){
            error.rejectValue("employeeId","Employee Id already been used by employee : "+employee.getPersonalInformation().getFullName());
        }
    }
}