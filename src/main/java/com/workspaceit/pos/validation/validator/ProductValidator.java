package com.workspaceit.pos.validation.validator;

import com.workspaceit.pos.constant.WEIGHT_UNIT;
import com.workspaceit.pos.entity.Category;
import com.workspaceit.pos.entity.Product;
import com.workspaceit.pos.service.CategoryService;
import com.workspaceit.pos.service.ProductService;
import com.workspaceit.pos.validation.form.product.ProductCreateForm;
import com.workspaceit.pos.validation.form.product.ProductUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ProductValidator {
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void validate(ProductCreateForm productCreateForm, Errors error){
        if(!error.hasFieldErrors("categoryId") && productCreateForm.getCategoryId()!=null){
            this.validateCategory(productCreateForm.getCategoryId(),error);
        }
        if(!error.hasFieldErrors("weight")){
            this.validateWeight(productCreateForm.getWeight(),productCreateForm.getWeightUnit(),error);
        }
        if(!error.hasFieldErrors("barcode") && productCreateForm.getCategoryId()!=null){
            this.validateUniqueBarcode(productCreateForm.getBarcode(),error);
        }
    }
    public void validateForUpdate(int id,ProductUpdateForm productCreateForm, Errors error){
        if(!error.hasFieldErrors("categoryId") && productCreateForm.getCategoryId()!=null){
            this.validateCategory(productCreateForm.getCategoryId(),error);
        }
        if(!error.hasFieldErrors("weight")){
            this.validateWeight(productCreateForm.getWeight(),productCreateForm.getWeightUnit(),error);
        }
        if(!error.hasFieldErrors("barcode") && productCreateForm.getCategoryId()!=null){
            this.validateUniqueBarcodeUsedByOthers(id,productCreateForm.getBarcode(),error);
        }
    }
    public void validateCategory(int categoryId,Errors error){
        Category category = this.categoryService.getById(categoryId);
        if(category==null){
            error.rejectValue("categoryId","Category not found by id :"+categoryId);
        }
    }
    public void validateWeight(Integer weight, WEIGHT_UNIT weightUnit, Errors error){
        if(weight==null || weight==0){
            return;
        }
        if(weightUnit==null){
            error.rejectValue("weightUnit","Weight unit required");
        }

    }
    public void validateUniqueBarcode(String barcode, Errors error){
        Product product = this.productService.getByBarcode(barcode);
        if(product!=null){
            error.rejectValue("barcode","Barcode already been used in product : "+product.getName());
        }

    }
    public void validateUniqueBarcodeUsedByOthers(int id,String barcode, Errors error){
        Product product = this.productService.getByBarcodeAndNotById(id,barcode);
        if(product!=null){
            error.rejectValue("barcode","Barcode already been used in product : "+product.getName());
        }

    }
}
