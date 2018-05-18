package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.ProductService;
import com.workspaceit.dccpos.validation.form.inventory.InventoryFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;

@Component
public class InventoryValidator {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void validate(String prefix, InventoryFrom[] inventoryFroms, Errors errors){

        if(inventoryFroms==null || inventoryFroms.length==0)return;

        for(int i=0;i<inventoryFroms.length;i++){
            InventoryFrom inventoryFrom = inventoryFroms[i];
            String tmpPrefix = ValidationHelper.preparePrefix(prefix+"["+i+"]");

            if(!errors.hasFieldErrors(tmpPrefix+"productId")){
                this.validateProduct(tmpPrefix,inventoryFrom.getProductId(),errors);
            }
        }
       // this.validateDuplicateProductId(prefix,inventoryFroms,errors);
    }

    private void validateProduct(String prefix,Integer productId,Errors errors){
        Product product =  this.productService.getById(productId);
        if(product==null){
            errors.rejectValue(prefix+"productId","Product not found by id :"+productId);
        }
    }

    /**
     * Find duplicate in same array
     *
     * */
    public void validateDuplicateProductId(String prefix,InventoryFrom[] inventoryFroms,Errors errors){
      List<InventoryFrom> inventoryFromList =  Arrays.asList(inventoryFroms);
        for(int i=0;i<inventoryFroms.length;i++){
            InventoryFrom inventoryForm = inventoryFroms[i];
            if(inventoryForm==null || inventoryForm.getProductId()==null || inventoryForm.getProductId()==0)
                continue;
            boolean found  =   inventoryFromList.stream().anyMatch(
                                      invFrom ->{
                                          if(invFrom==null || invFrom.getProductId()==null || invFrom.getProductId()==0)
                                              return false;
                                          return (invFrom!=inventoryForm) && invFrom.getProductId().equals(inventoryForm.getProductId());

                                      });
            if(found){
                String tmpPrefix = ValidationHelper.preparePrefix(prefix+"["+i+"]");
                errors.rejectValue(tmpPrefix+"productId","Duplicate product id found");
                return;
            }
        }
    }
}
