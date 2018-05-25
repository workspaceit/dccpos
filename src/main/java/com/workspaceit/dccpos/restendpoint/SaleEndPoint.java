package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
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


    @Autowired
    public void setSaleValidator(SaleValidator saleValidator) {
        this.saleValidator = saleValidator;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(Authentication authentication,
                                    @Valid @RequestBody SaleForm saleForm, BindingResult bindingResult){
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.saleValidator.validate(saleForm,bindingResult);
        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());

        }
        return ResponseEntity.status(HttpStatus.OK).body("");

    }

}