package com.workspaceit.pos.restendpoint;

import com.workspaceit.pos.constant.EndpointRequestUriPrefix;
import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.service.EmployeeService;
import com.workspaceit.pos.util.ServiceResponse;
import com.workspaceit.pos.validation.form.employee.EmployeeCreateForm;
import com.workspaceit.pos.validation.form.employee.EmployeeUpdateForm;
import com.workspaceit.pos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.pos.validation.form.accounting.LedgerForm;
import com.workspaceit.pos.validation.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/employee")
public class EmployeeEndPoint {
    private EmployeeService employeeService;
    private EmployeeValidator employeeValidator;


    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Autowired
    public void setEmployeeValidator(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid EmployeeCreateForm employeeForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.employeeValidator.validate(employeeForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        Employee employee =  this.employeeService.create(employeeForm);

        return ResponseEntity.ok(employee);
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> create(@PathVariable("id") int id,
                                    @Valid EmployeeUpdateForm employeeForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.employeeValidator.validateForUpdate(employeeForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        Employee employee;
        try {
            employee = this.employeeService.edit(id,employeeForm);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }

        return ResponseEntity.ok(employee);
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Employee> employees = this.employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

}