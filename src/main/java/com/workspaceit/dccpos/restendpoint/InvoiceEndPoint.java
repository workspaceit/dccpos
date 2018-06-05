package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.dataModel.Invoice;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.InvoiceGenerateService;
import com.workspaceit.dccpos.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/invoice")
@CrossOrigin
public class InvoiceEndPoint {
    private InvoiceGenerateService invoiceGenerateService;

    @Autowired
    public void setInvoiceGenerateService(InvoiceGenerateService invoiceGenerateService) {
        this.invoiceGenerateService = invoiceGenerateService;
    }

    @RequestMapping(value = "/sale/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getSaleInvoice(@PathVariable("id") Long id){
        Invoice invoice;
        try {
            invoice = this.invoiceGenerateService.generateSaleInvoice(id);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }
        return ResponseEntity.ok(invoice);
    }
}