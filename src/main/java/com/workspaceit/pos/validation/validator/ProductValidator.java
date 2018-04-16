package com.workspaceit.pos.validation.validator;

import com.workspaceit.pos.constant.WEIGHT_UNIT;
import com.workspaceit.pos.entity.Category;
import com.workspaceit.pos.service.CategoryService;
import com.workspaceit.pos.validation.form.product.ProductCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ProductValidator {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void validate(ProductCreateForm productCreateForm, Errors error){
        if(!error.hasFieldErrors("categoryId") && productCreateForm.getCategoryId()!=null){
            this.validateCategory(productCreateForm.getCategoryId(),error);
        }
        if(!error.hasFieldErrors("weight")){
            this.validateWeight(productCreateForm.getWeight(),productCreateForm.getWeightUnit(),error);
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
}
