package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.TempFile;
import com.workspaceit.dccpos.service.ShopService;
import com.workspaceit.dccpos.service.SupplierService;
import com.workspaceit.dccpos.service.TempFileService;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentCreateForm;
import com.workspaceit.dccpos.validation.form.shop.ShopForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Matin on 5/14/2018.
 */
@Component
public class ShopValidator {

    private TempFileService tempFileService;

    @Autowired
    public void setTempFileService(TempFileService tempFileService) {
        this.tempFileService = tempFileService;
    }

    public void validate(ShopForm shopForm, Errors error){
        if(!error.hasFieldErrors("imageToken") && shopForm.getImageToken()!=null && shopForm.getImageToken()>0){
            this.validateImageToken(shopForm.getImageToken(),error);
        }
    }


    public void validateImageToken(Integer imageToken,Errors error){
        TempFile tempFile =  this.tempFileService.getByToken(imageToken);

        if(tempFile==null){
            error.rejectValue("imageToken","Image token not found ");
        }
    }
}
