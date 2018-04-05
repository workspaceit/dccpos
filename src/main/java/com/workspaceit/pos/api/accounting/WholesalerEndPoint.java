package com.workspaceit.pos.api.accounting;

import com.workspaceit.pos.entity.Supplier;
import com.workspaceit.pos.entity.Wholesaler;
import com.workspaceit.pos.service.SupplierService;
import com.workspaceit.pos.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oauth/api/wholesaler")
public class WholesalerEndPoint {
    private WholesalerService wholesalerService;

    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }



    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Wholesaler> supplierList = this.wholesalerService.getAll();
        return ResponseEntity.ok(supplierList);
    }
}