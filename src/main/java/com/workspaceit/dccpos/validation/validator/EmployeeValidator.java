package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.EmployeeService;
import com.workspaceit.dccpos.validation.form.employee.EmployeeCreateForm;
import com.workspaceit.dccpos.validation.form.employee.EmployeeUpdateForm;
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
        if(!error.hasFieldErrors("employeeId")
                && employeeForm.getEmployeeId()!=null
                && !employeeForm.getEmployeeId().trim().equals("")){
            this.validateUniqueEmployeeId(employeeForm.getEmployeeId(),error);
        }

        if(employeeForm.getPersonalInfo()!=null){
            this.personalInfoValidator.validate("personalInfo",employeeForm.getPersonalInfo(),error);
        }

        if(employeeForm.getAuthCredential()!=null){
            this.authCredentialValidator.validate("authCredential",employeeForm.getAuthCredential(),error);
        }
    }
    public void validateUpdate(int id, EmployeeUpdateForm employeeForm, Errors error) throws EntityNotFound {
        Employee employee = this.employeeService.getEmployee(id);
        PersonalInformation personalInformation = employee.getPersonalInformation();
        if(personalInformation==null)throw new EntityNotFound("Personal Information not found on employee id"+id);

        if(!error.hasFieldErrors("employeeId")
                && employeeForm.getEmployeeId()!=null
                && !employeeForm.getEmployeeId().trim().equals("")){

            this.validateEmployeeIdUsedByOthers(employeeForm.getEmployeeId(),id,error);
        }
        if(employeeForm.getPersonalInfo()!=null){
            this.personalInfoValidator.validateUpdate("personalInfo",personalInformation.getId(),employeeForm.getPersonalInfo(),error);
        }
    }
    public void validateUniqueEmployeeId(String employeeId, Errors error){
        if(employeeId==null)return;

        Employee employee =  this.employeeService.getByEmployeeId(employeeId.trim());
        if(employee!=null){
            error.rejectValue("employeeId","Employee Id already been used by employee : "+employee.getPersonalInformation().getFullName());
        }
    }
    public void validateEmployeeIdUsedByOthers(String employeeId,int id, Errors error){
        if(employeeId==null)return;

        Employee employee =  this.employeeService.getByEmployeeIdAndNotById(employeeId.trim(),id);
        if(employee!=null){
            error.rejectValue("employeeId","Employee Id already been used by employee : "+employee.getPersonalInformation().getFullName());
        }
    }
}