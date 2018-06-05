package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/get-by-product-id/{productId}")
    public ResponseEntity<Collection<Inventory>> getByProductId(@PathVariable("productId") Integer productId){

         List<Inventory> inventoryList =    this.inventoryService.getInStockByProductId(productId);
         return ResponseEntity.ok(inventoryList);
    }
}
