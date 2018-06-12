package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.service.InventoryService;
import com.workspaceit.dccpos.validation.form.inventory.InventorySearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/inventory")
@CrossOrigin
public class InventoryEndPoint {
    private InventoryService inventoryService;

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RequestMapping(value = "/get-by-product-id/{productId}",method = RequestMethod.GET)
    public ResponseEntity<Collection<Inventory>> getByProductId(@PathVariable("productId") Integer productId,
                                                                @Valid InventorySearchForm inventorySearchForm,
                                                                BindingResult bindingResult){

         List<Inventory> inventoryList =    this.inventoryService.getInStockByProductId(productId,inventorySearchForm);
         return ResponseEntity.ok(inventoryList);
    }
}
