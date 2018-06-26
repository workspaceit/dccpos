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

        if(!error.hasFieldErrors("name") && productCreateForm.getName()!=null){
            this.validateUniqueName(productCreateForm.getName(),error);
        }
        if(!error.hasFieldErrors("categoryId") && productCreateForm.getCategoryId()!=null){
            this.validateCategory(productCreateForm.getCategoryId(),error);
        }
        if(!error.hasFieldErrors("weight")){
            this.validateWeight(productCreateForm.getWeight(),productCreateForm.getWeightUnit(),error);
        }
        if(!error.hasFieldErrors("barcode")
                && productCreateForm.getBarcode()!=null
                && !productCreateForm.getBarcode().trim().equals("")){
            this.validateUniqueBarcode(productCreateForm.getBarcode(),error);
        }
        if(!error.hasFieldErrors("imageToken")
                && productCreateForm.getImageToken()!=null
                && productCreateForm.getImageToken()>0){
            this.validateImageToken(productCreateForm.getImageToken(),error);
        }
    }
    public void validateForUpdate(int id,ProductUpdateForm productUpdateForm, Errors error){
        if(!error.hasFieldErrors("categoryId") && productUpdateForm.getCategoryId()!=null){
            this.validateCategory(productUpdateForm.getCategoryId(),error);
        }
        if(!error.hasFieldErrors("weight")){
            this.validateWeight(productUpdateForm.getWeight(),productUpdateForm.getWeightUnit(),error);
        }
        if(!error.hasFieldErrors("barcode")
                && ( productUpdateForm.getBarcode()!=null &&  !productUpdateForm.getBarcode().trim().equals("") ) ){
            this.validateUniqueBarcodeUsedByOthers(id,productUpdateForm.getBarcode(),error);
        }
        if(!error.hasFieldErrors("name") && productUpdateForm.getName()!=null){
            this.validateUniqueNameUsedByOthers(id,productUpdateForm.getName(),error);
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
        if(barcode==null)return;

        Product product = this.productService.getByBarcode(barcode.trim());
        if(product!=null){
            error.rejectValue("barcode","Barcode already been used in product : "+product.getName());
        }

    }
    public void validateUniqueBarcodeUsedByOthers(int id,String barcode, Errors error){
        if(barcode==null)return;

        Product product = this.productService.getByBarcodeAndNotById(id,barcode.trim());
        if(product!=null){
            error.rejectValue("barcode","Barcode already been used in product : "+product.getName());
        }
    }
    public void validateUniqueName(String productName,Errors errors){
        if(productName==null)return;
        Product product = this.productService.getByName(productName.trim());
        if(product!=null){
            errors.rejectValue("name","Product name already been used");
        }
    }
    public void validateUniqueNameUsedByOthers(int id,String name, Errors error){
        if(name==null)return;
        Product product = this.productService.getByNameAndNotById(id,name.trim());
        if(product!=null){
            error.rejectValue("name","Product name already been used ");
        }
    }


}
