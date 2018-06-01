package com.workspaceit.dccpos.restendpoint.accounting;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.AuthCredential;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.EmployeeService;
import com.workspaceit.dccpos.service.accounting.EntryService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.accounting.InvestmentForm;
import com.workspaceit.dccpos.validation.form.accounting.TransactionForm;
import com.workspaceit.dccpos.validation.form.authcredential.PasswordResetForm;
import com.workspaceit.dccpos.validation.validator.accounting.EntryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth +"/entry")
@CrossOrigin
public class EntryEndPoint {
    private EntryService entryService;
    private EntryValidator entryValidator;
    private EmployeeService employeeService;

    @Autowired
    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }

    @Autowired
    public void setEntryValidator(EntryValidator entryValidator) {
        this.entryValidator = entryValidator;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/get-by-date/{startDate}/{endDate}")
    public ResponseEntity<?> getByDate(@PathVariable("startDate") String startDate,
                                       @PathVariable("endDate") String endDate,
                                       @Valid PasswordResetForm passwordResetForm,
                                       BindingResult bindingResult){

        PropertyEditor propertyEditor = bindingResult.findEditor("newPassword",String.class);
        System.out.println(bindingResult.getRawFieldValue("newPassword"));
        System.out.println(propertyEditor.getValue());


        List<Entry> entries = this.entryService.getByDate(null,null);
        return ResponseEntity.ok(entries);
    }

    @RequestMapping("/make/payment")
    public ResponseEntity<?> makePayment(Authentication authentication,@Valid @RequestBody TransactionForm transactionForm, BindingResult bindingResult){
        AuthCredential authCredential = (AuthCredential) authentication.getPrincipal();
        Employee currentUser =  this.employeeService.getByAuthCredential(authCredential);
        Entry entry = null;

        /**
         * Basic and business validation
         * */
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.entryValidator.validatePayment(transactionForm,bindingResult);


        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        /**
         * DR  Cr amount balance check
         * */
        this.entryValidator.validateDcAmountBalance(transactionForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());

        }

        try {
            entry =   this.entryService.createPaymentEntry(currentUser,transactionForm);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }
        return ResponseEntity.ok(entry);
    }
    @RequestMapping("/make/receipt")
    public ResponseEntity<?> makeReceipt(Authentication authentication,@Valid @RequestBody TransactionForm transactionForm, BindingResult bindingResult){
        AuthCredential authCredential = (AuthCredential) authentication.getPrincipal();
        Employee currentUser =  this.employeeService.getByAuthCredential(authCredential);
        Entry entry = null;

        /**
         * Basic and business validation
         * */
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.entryValidator.validateReceipt(transactionForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        /**
         * DR  Cr amount balance check
         * */
        this.entryValidator.validateDcAmountBalance(transactionForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());

        }

        try {
            entry =   this.entryService.createReceiptEntry(currentUser,transactionForm);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }
        return ResponseEntity.ok(entry);
    }
    @RequestMapping("/make/investment/cash")
    public ResponseEntity<?> makeReceipt(Authentication authentication, @Valid @RequestBody InvestmentForm investmentForm, BindingResult bindingResult){
        AuthCredential authCredential = (AuthCredential) authentication.getPrincipal();
        Employee currentUser =  this.employeeService.getByAuthCredential(authCredential);
        Entry entry = null;

        /**
         * Basic and business validation
         * */
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.entryValidator.validateInvestment(investmentForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }


        try {
            entry =   this.entryService.createInvestmentEntry(currentUser,investmentForm);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }
        return ResponseEntity.ok(entry);
    }

}