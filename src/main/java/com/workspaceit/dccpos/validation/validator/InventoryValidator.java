package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.constant.STOCK_STATUS;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.InventoryService;
import com.workspaceit.dccpos.service.ProductService;
import com.workspaceit.dccpos.validation.form.inventory.InventoryFrom;
import com.workspaceit.dccpos.validation.form.sale.InventorySaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;

@Component
public class InventoryValidator {
    private ProductService productService;
    private InventoryService inventoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
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

    public void validate(String prefix, InventorySaleForm[] inventorySaleFroms, Errors errors){

        if(inventorySaleFroms==null || inventorySaleFroms.length==0)return;

        for(int i=0;i<inventorySaleFroms.length;i++){
            InventorySaleForm inventorySaleFrom = inventorySaleFroms[i];
            this.validateInventory(prefix+"["+i+"].",inventorySaleFrom,errors);
        }

    }
    public void validateInventory(String prefix,InventorySaleForm inventorySaleFrom, Errors errors){
        if(inventorySaleFrom.getInventoryId()==null)return;

        Inventory inventory = this.inventoryService.getById(inventorySaleFrom.getInventoryId());
        Product product =  this.productService.getByInventoryId(inventory.getId());
        if(product==null){
            errors.rejectValue(prefix+"inventoryId","Product not found by Inventory id :"+inventorySaleFrom.getInventoryId());
            return;
        }
        if(inventory==null){
            errors.rejectValue(prefix+"inventoryId","Inventory not found by id :"+inventorySaleFrom.getInventoryId());
            return;
        }
        if(inventory.getStatus().equals(STOCK_STATUS.SOLD_OUT)){
            errors.rejectValue(prefix+"inventoryId",product.getName()+" sold out");
            return;
        }
        if(!errors.hasFieldErrors(prefix+"quantity") && inventory.getAvailableQuantity() < inventorySaleFrom.getQuantity()){
            errors.rejectValue(prefix+"quantity","Quantity of "+product.getName()+"  not available");
        }
        if(!errors.hasFieldErrors(prefix+"sellingPrice") && inventory.getSellingPrice() < inventorySaleFrom.getSellingPrice()){
            errors.rejectValue(prefix+"sellingPrice","Selling price is higher then actual selling price");
        }

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
