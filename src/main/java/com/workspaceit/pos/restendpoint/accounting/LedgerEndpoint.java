package com.workspaceit.pos.restendpoint.accounting;

import com.workspaceit.pos.constant.EndpointRequestUriPrefix;
import com.workspaceit.pos.entity.accounting.Ledger;
import com.workspaceit.pos.service.accounting.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/ledger")
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
}