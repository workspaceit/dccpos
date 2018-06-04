package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.AuthCredential;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.Sale;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.EmployeeService;
import com.workspaceit.dccpos.service.SaleService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import com.workspaceit.dccpos.validation.validator.SaleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/sale")
@CrossOrigin
public class SaleEndPoint {
    private SaleValidator saleValidator;
    private SaleService saleService;
    private EmployeeService employeeService;

    @Autowired
    public void setSaleValidator(SaleValidator saleValidator) {
        this.saleValidator = saleValidator;
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(Authentication authentication,
                                    @Valid @RequestBody SaleForm saleForm, BindingResult bindingResult){
        AuthCredential currentUser = (AuthCredential)authentication.getPrincipal();
        Employee employee = this.employeeService.getByAuthCredential(currentUser);
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        //
        this.saleValidator.validate(saleForm,bindingResult);
        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());

        }
        Sale sale = null;
        try {
            sale = this.saleService.create(saleForm,employee);
        } catch (EntityNotFound entityNotFound) {
            throw new InternalError(entityNotFound.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.saleService.getById(sale.getId()));
    }
    @RequestMapping(value = "/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(ServiceResponse.getListResult(0,saleService.getAll()));
    }

}