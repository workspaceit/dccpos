package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Wholesaler;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.WholesalerService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.wholesaler.WholesalerCreateForm;
import com.workspaceit.dccpos.validation.form.wholesaler.WholesalerUpdateForm;
import com.workspaceit.dccpos.validation.validator.WholesalerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/wholesaler")
public class WholesalerEndPoint {
    private WholesalerService wholesalerService;
    private WholesalerValidator wholesalerValidator;

    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }

    @Autowired
    public void setWholesalerValidator(WholesalerValidator wholesalerValidator) {
        this.wholesalerValidator = wholesalerValidator;
    }

    //@Secured(SecurityRole.ADMIN)
    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Wholesaler> wholesalerList = this.wholesalerService.getAll();
        return ResponseEntity.ok(wholesalerList);
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> create(Authentication authentication,@Valid WholesalerCreateForm wholesalerCreateForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.wholesalerValidator.validate(wholesalerCreateForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        Wholesaler wholesaler =  this.wholesalerService.create(wholesalerCreateForm);

        return ResponseEntity.ok(wholesaler);
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> create(@PathVariable("id") int id,
                                    @Valid WholesalerUpdateForm wholesalerUpdateForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.wholesalerValidator.validateUpdate(id,wholesalerUpdateForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        Wholesaler wholesaler;
        try {
            wholesaler = this.wholesalerService.edit(id,wholesalerUpdateForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }

        return ResponseEntity.ok(wholesaler);
    }
}