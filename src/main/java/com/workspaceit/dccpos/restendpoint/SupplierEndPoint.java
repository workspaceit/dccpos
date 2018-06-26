package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.SupplierService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.supplier.SupplierCreateForm;
import com.workspaceit.dccpos.validation.form.supplier.SupplierUpdateForm;
import com.workspaceit.dccpos.validation.validator.SupplierValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/supplier")
@CrossOrigin
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
        Supplier supplier;
        try {
            this.supplierValidator.validateUpdate(id,supplierUpdateForm,bindingResult);

            if(bindingResult.hasErrors()){
                serviceResponse.bindValidationError(bindingResult);
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
            }




            supplier = this.supplierService.edit(id,supplierUpdateForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }

        return ResponseEntity.ok(supplier);
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        Supplier supplier = this.supplierService.getById(id);
        return ResponseEntity.ok(supplier);
    }
    @GetMapping("/get-by-supplier-id/{supplierId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable("supplierId") String employeeId){
        Supplier supplier = this.supplierService.getBySupplierId(employeeId);
        return ResponseEntity.ok(supplier);
    }
}