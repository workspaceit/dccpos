package com.workspaceit.pos.restendpoint;

import com.workspaceit.pos.constant.EndpointRequestUriPrefix;
import com.workspaceit.pos.entity.Product;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.service.ProductService;
import com.workspaceit.pos.util.ServiceResponse;
import com.workspaceit.pos.util.ValidationUtil;
import com.workspaceit.pos.validation.form.product.ProductCreateForm;
import com.workspaceit.pos.validation.form.product.ProductUpdateForm;
import com.workspaceit.pos.validation.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Service;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointPublic+"/product")
public class ProductEndPoint {
    private ProductService productService;
    private ProductValidator productValidator;
    private ValidationUtil validationUtil;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductValidator(ProductValidator productValidator) {
        this.productValidator = productValidator;
    }

    @Autowired
    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    @RequestMapping("/get-all/{limit}/{offset}")
    public ResponseEntity<?> getAll(@PathVariable int limit, @PathVariable int offset){
        ServiceResponse serviceResponse = this.validationUtil.limitOffsetValidation(limit,offset,10);
        if(serviceResponse.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        long totalRowCount = this.productService.getTotalRowCount();
        List<Product> productList = this.productService.getAll(limit,offset);

        return ResponseEntity.ok(serviceResponse.getResult(totalRowCount,productList));
    }
    @RequestMapping("/get-by-category-id/{categoryId}/{limit}/{offset}")
    public ResponseEntity<?> getAll(@PathVariable("categoryId")int categoryId,
                                    @PathVariable int limit, @PathVariable int offset){
        ServiceResponse serviceResponse = this.validationUtil.limitOffsetValidation(limit,offset,10);
        if(serviceResponse.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        long totalRowCount = this.productService.getByCategoryIdCount(categoryId);
        List<Product> productList = this.productService.getByCategoryId(categoryId,limit,offset);

        return ResponseEntity.ok(serviceResponse.getResult(totalRowCount,productList));
    }

    @RequestMapping(value = "/get-by-barcode",method = RequestMethod.GET)
    public ResponseEntity<?> getByBarcode(@RequestParam("barcode") String barcode){
        Product product = this.productService.getByBarcode(barcode);
        return ResponseEntity.ok(product);
    }
    @RequestMapping("/get-by-name-fragment/{limit}/{offset}")
    public ResponseEntity<?> getAll(@PathVariable int limit, @PathVariable int offset,@RequestParam("name") String name){
        ServiceResponse serviceResponse = this.validationUtil.limitOffsetValidation(limit,offset,10);
        if(serviceResponse.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }
        long totalRowCount = this.productService.getByNameLikeCount(name);
        List<Product> productList = this.productService.getByNameLike(name,limit,offset);
        return ResponseEntity.ok(serviceResponse.getResult(totalRowCount,productList));
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> create(Authentication authentication, @Valid ProductCreateForm productCreateForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.productValidator.validate(productCreateForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        Product product;
        try {
            product = this.productService.create(productCreateForm);
        } catch (EntityNotFound entityNotFound) {
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }

        return ResponseEntity.ok(product);
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> create(@PathVariable("id") int id,
                                    @Valid ProductUpdateForm productUpdateForm, BindingResult bindingResult){

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.productValidator.validateForUpdate(id,productUpdateForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }
        Product product;
        try {
            product = this.productService.update(id,productUpdateForm);
        } catch (EntityNotFound entityNotFound) {
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResponse.getMsgInMap(entityNotFound.getMessage()));
        }

        return ResponseEntity.ok(product);
    }
}