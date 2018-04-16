package com.workspaceit.pos.restendpoint;

import com.workspaceit.pos.constant.EndpointRequestUriPrefix;
import com.workspaceit.pos.entity.Product;
import com.workspaceit.pos.entity.Wholesaler;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.service.ProductService;
import com.workspaceit.pos.service.WholesalerService;
import com.workspaceit.pos.util.ServiceResponse;
import com.workspaceit.pos.validation.form.product.ProductCreateForm;
import com.workspaceit.pos.validation.form.product.ProductUpdateForm;
import com.workspaceit.pos.validation.form.wholesaler.WholesalerCreateForm;
import com.workspaceit.pos.validation.form.wholesaler.WholesalerUpdateForm;
import com.workspaceit.pos.validation.validator.ProductValidator;
import com.workspaceit.pos.validation.validator.WholesalerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointPublic+"/product")
public class ProductEndPoint {
    private ProductService productService;
    private ProductValidator productValidator;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductValidator(ProductValidator productValidator) {
        this.productValidator = productValidator;
    }

    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Product> productList = this.productService.getAll();
        return ResponseEntity.ok(productList);
    }
    @RequestMapping(value = "/get-by-barcode",method = RequestMethod.GET)
    public ResponseEntity<?> getByBarcode(@RequestParam("barcode") String barcode){
        Product product = this.productService.getByBarcode(barcode);
        return ResponseEntity.ok(product);
    }
    @RequestMapping("/get-by-name-fragment")
    public ResponseEntity<?> getAll(@RequestParam("name") String name){
        List<Product> productList = this.productService.getByNameLike(name);
        return ResponseEntity.ok(productList);
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