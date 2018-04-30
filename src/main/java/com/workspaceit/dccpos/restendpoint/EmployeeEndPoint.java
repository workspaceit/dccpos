package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.EmployeeService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.employee.EmployeeCreateForm;
import com.workspaceit.dccpos.validation.form.employee.EmployeeUpdateForm;
import com.workspaceit.dccpos.validation.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/employee")
@CrossOrigin
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
        this.employeeValidator.validateUpdate(id,employeeForm,bindingResult);

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
    @RequestMapping(value = "/get-all",method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        List<Employee> employees = this.employeeService.getAll();
        return ResponseEntity.ok(employees);
    }
    @RequestMapping(value = "/get-by-id/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        Employee employees = this.employeeService.getById(id);
        return ResponseEntity.ok(employees);
    }
    @RequestMapping(value = "/get-by-employee-id/{employeeId}",method = RequestMethod.GET)
    public ResponseEntity<?> getByEmployeeId(@PathVariable("employeeId") String employeeId){
        Employee employees = this.employeeService.getByEmployeeId(employeeId);
        return ResponseEntity.ok(employees);
    }
}