package com.workspaceit.pos.api;

import com.workspaceit.pos.constant.EndpointRequestUriPrefix;
import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.service.EmployeeService;
import com.workspaceit.pos.util.ServiceResponse;
import com.workspaceit.pos.validation.form.EmployeeForm;
import com.workspaceit.pos.validation.form.PersonalInfoForm;
import com.workspaceit.pos.validation.form.accounting.LedgerForm;
import com.workspaceit.pos.validation.validator.EmployeeValidator;
import com.workspaceit.pos.validation.validator.PersonalInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/employee")
public class EmployeeEndPoint {
    private EmployeeService employeeService;
    private PersonalInfoValidator personalInfoValidator;
    private EmployeeValidator employeeValidator;


    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setPersonalInfoValidator(PersonalInfoValidator personalInfoValidator) {
        this.personalInfoValidator = personalInfoValidator;
    }

    @Autowired
    public void setEmployeeValidator(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    @RequestMapping("/create")
    public ResponseEntity<?> create(@Valid EmployeeForm employeeForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();




        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        this.employeeValidator.validate(employeeForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        Employee employee =  this.employeeService.create(employeeForm);

        return ResponseEntity.ok(employee);
    }
    @RequestMapping("/create1")
    public ResponseEntity<?> create(@Valid EmployeeForm employeeForm,
                                    @Valid PersonalInfoForm personalInfoForm,
                                    @Valid LedgerForm ledgerForm,
                                    BindingResult bindingResult){

        //Employee employee =  this.employeeService.create(employeeForm);
        HashMap<String,Object> map = new HashMap<>();
        map.put("a",employeeForm);
        map.put("b",personalInfoForm);
        map.put("c",ledgerForm);
        return ResponseEntity.ok(map);
    }
    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Employee> employees = this.employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

}