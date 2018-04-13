package com.workspaceit.pos.restendpoint;

import com.workspaceit.pos.constant.EndpointRequestUriPrefix;
import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.entity.Supplier;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.service.SupplierService;
import com.workspaceit.pos.util.ServiceResponse;
import com.workspaceit.pos.validation.form.employee.EmployeeCreateForm;
import com.workspaceit.pos.validation.form.employee.EmployeeUpdateForm;
import com.workspaceit.pos.validation.form.supplier.SupplierCreateForm;
import com.workspaceit.pos.validation.form.supplier.SupplierUpdateForm;
import com.workspaceit.pos.validation.validator.SupplierValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/supplier")
public class SupplierEndPoint {
    private SupplierService supplierService;
    private SupplierValidator supplierValidator;

    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Autowired
    public void setSupplierValidator(SupplierValidator supplierValidator) {
        this.supplierValidator = supplierValidator;
    }

    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Supplier> supplierList = this.supplierService.getAll();
        return ResponseEntity.ok(supplierList);
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid SupplierCreateForm supplierForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.supplierValidator.validate(supplierForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        Supplier supplier =  this.supplierService.create(supplierForm);

        return ResponseEntity.ok(supplier);
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> create(@PathVariable("id") int id,
                                    @Valid SupplierUpdateForm supplierUpdateForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.supplierValidator.validateUpdate(id,supplierUpdateForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        Supplier supplier;
        try {
            supplier = this.supplierService.edit(id,supplierUpdateForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }

        return ResponseEntity.ok(supplier);
    }
}