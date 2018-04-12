package com.workspaceit.pos.restendpoint;

import com.workspaceit.pos.entity.Supplier;
import com.workspaceit.pos.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oauth/api/supplier")
public class SupplierEndPoint {
    private SupplierService supplierService;


    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Supplier> supplierList = this.supplierService.getAll();
        return ResponseEntity.ok(supplierList);
    }
}