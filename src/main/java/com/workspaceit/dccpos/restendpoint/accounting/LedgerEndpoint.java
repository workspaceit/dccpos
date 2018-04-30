package com.workspaceit.dccpos.restendpoint.accounting;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/ledger")
@CrossOrigin
public class LedgerEndpoint {
    private LedgerService ledgerService;

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @RequestMapping(value = "/get-all",method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        List<Ledger> ledgerList = this.ledgerService.getAll();
       return ResponseEntity.ok(ledgerList);
    }

    @RequestMapping(value = "/get/{type}",method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable("type") String type){
        List<Ledger> ledgerList = null;

        switch (type){
            case "wholesaler":
                ledgerList= this.ledgerService.getAllWholesaler();
                break;
            case "supplier":
                ledgerList= this.ledgerService.getAllSupplier();
                break;
            case "employeeSalary":
                ledgerList= this.ledgerService.getAllEmployeeSalary();
                break;
            case "bankOrCash":
                ledgerList= this.ledgerService.getAllBankOrCash();
                break;
        }


        return ResponseEntity.ok(ledgerList);
    }
}