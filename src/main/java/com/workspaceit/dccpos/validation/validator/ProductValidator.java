package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.constant.WEIGHT_UNIT;
import com.workspaceit.dccpos.entity.Category;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.entity.TempFile;
import com.workspaceit.dccpos.service.CategoryService;
import com.workspaceit.dccpos.service.ProductService;
import com.workspaceit.dccpos.service.TempFileService;
import com.workspaceit.dccpos.validation.form.product.ProductCreateForm;
import com.workspaceit.dccpos.validation.form.product.ProductUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ProductValidator {
    private CategoryService categoryService;
    private ProductService productService;
    private TempFileService tempFileService;


    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setTempFileService(TempFileService tempFileService) {
        this.tempFileService = tempFileService;
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
        if(!error.hasFieldErrors("imageToken") && productCreateForm.getImageToken()!=null && productCreateForm.getImageToken()>0){
            this.validateImageToken(productCreateForm.getImageToken(),error);
        }
    }
    public void validateForUpdate(int id,ProductUpdateForm productCreateForm, Errors error){
        if(!error.hasFieldErrors("categoryId") && productCreateForm.getCategoryId()!=null){
            this.validateCategory(productCreateForm.getCategoryId(),error);
        }
        if(!error.hasFieldErrors("weight")){
            this.validateWeight(productCreateForm.getWeight(),productCreateForm.getWeightUnit(),error);
        }
        if(!error.hasFieldErrors("barcode")
                && ( productCreateForm.getBarcode()!=null &&  !productCreateForm.getBarcode().trim().equals("") ) ){
            this.validateUniqueBarcodeUsedByOthers(id,productCreateForm.getBarcode(),error);
        }
    }
    public void validateImageToken(Integer imageToken,Errors error){
        TempFile tempFile =  this.tempFileService.getByToken(imageToken);

        if(tempFile==null){
            error.rejectValue("imageToken","Image token not found ");
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
