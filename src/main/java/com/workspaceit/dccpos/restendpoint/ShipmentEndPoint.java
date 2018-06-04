package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.AuthCredential;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.EmployeeService;
import com.workspaceit.dccpos.service.ShipmentService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.util.ValidationUtil;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentSearchForm;
import com.workspaceit.dccpos.validation.validator.PurchaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/shipment")
@CrossOrigin
public class ShipmentEndPoint {

    private PurchaseValidator purchaseValidator;
    private ShipmentService shipmentService;
    private ValidationUtil validationUtil;
    private EmployeeService employeeService;

    @Autowired
    public void setPurchaseValidator(PurchaseValidator purchaseValidator) {
        this.purchaseValidator = purchaseValidator;
    }

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Autowired
    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }





    @RequestMapping(value = "/create",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(Authentication authentication,
                                    @Valid @RequestBody PurchaseForm purchaseForm, BindingResult bindingResult){
        AuthCredential currentUser = (AuthCredential)authentication.getPrincipal();
        Employee employee = this.employeeService.getByAuthCredential(currentUser);

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.purchaseValidator.validate(purchaseForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        Shipment shipment  = null;
        try {
            shipment = this.shipmentService.create(employee,purchaseForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }


        return ResponseEntity.ok(shipment);
    }
    @GetMapping(value = "/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        Shipment shipment  =  this.shipmentService.getById(id);
        return ResponseEntity.ok(shipment);
    }
    @GetMapping(value = "/get-by-tracking-id/{trackingId}")
    public ResponseEntity<?> getById(@PathVariable("trackingId") String trackingId){
        Shipment shipment  =  this.shipmentService.getByTrackingId(trackingId);
        return ResponseEntity.ok(shipment);
    }
    /**
     * Change it to Create Criteria
     * and Limit offset
     * */
    @GetMapping("/get-all/{limit}/{offset}")
    public ResponseEntity<?> getAll(@PathVariable int limit, @PathVariable int offset,
                                    @Valid ShipmentSearchForm shipmentSearchForm,BindingResult bindingResult){
        ServiceResponse serviceResponse = this.validationUtil.limitOffsetValidation(limit,offset,10);
        if(serviceResponse.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        long totalCount;
        List<Shipment> shipments;

        totalCount = this.shipmentService.getCountOfAll(shipmentSearchForm);
        shipments = this.shipmentService.getAll(limit,offset,shipmentSearchForm);


        return ResponseEntity.ok(serviceResponse.getListResult(totalCount,shipments));
    }

}