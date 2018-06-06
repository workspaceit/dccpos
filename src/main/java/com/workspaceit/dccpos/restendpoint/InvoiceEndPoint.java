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

    @RequestMapping(value = "/{invoiceType}/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getSaleInvoice(@PathVariable("invoiceType") final String invoiceType,@PathVariable("id") Long id){
        Invoice invoice=null;
        try {
            switch (invoiceType){
                case "sale":
                    invoice = this.invoiceGenerateService.generateSaleInvoice(id);
                    break;
                case "shipment":
                    invoice = this.invoiceGenerateService.generateShipmentInvoice(id);
                    break;
            }

        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }
        return ResponseEntity.ok(invoice);
    }


    @RequestMapping(value = "/get-by-tracking-id/{trackingId}",method = RequestMethod.GET)
    public ResponseEntity<?> getInvoice(@PathVariable("trackingId") String trackingId){
        Invoice invoice;
        try {
            invoice = this.invoiceGenerateService.getInvoiceByTrackingId(trackingId);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }
        return ResponseEntity.ok(invoice);
    }
}