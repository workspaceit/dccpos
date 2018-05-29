package com.workspaceit.dccpos.restendpoint;

/**
 * Created by Matin on 4/25/2018.
 */
import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.ShopInformation;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.ShopService;
import com.workspaceit.dccpos.service.TempFileService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import com.workspaceit.dccpos.validation.form.shop.ShopForm;
import com.workspaceit.dccpos.validation.validator.ShopValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointPublic+"/shop")

public class ShopEndPoint {

    private ShopService shopService;
    private ShopValidator shopValidator;

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    @Autowired
    public void setSupplierValidator(ShopValidator shopValidator) {
        this.shopValidator = shopValidator;
    }



    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResponseEntity<?> get(){
        ShopInformation shop  =  this.shopService.getShop();
        return ResponseEntity.ok(shop);
    }


    @RequestMapping(value = "/create-or-update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(Authentication authentication,
                                    @Valid @RequestBody ShopForm shopForm, BindingResult bindingResult){
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.shopValidator.validate(shopForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        ShopInformation shop  = null;
        try {
            shop = this.shopService.create(shopForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }


        return ResponseEntity.ok(shop);
    }
}
