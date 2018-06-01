package com.workspaceit.dccpos.util.validation;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class CustomValidationUtil extends ValidationUtils{
    public static void rejectIfNull(Errors errors,String field,String errorCode){
        Assert.notNull(errors, "Errors object must not be null");
        Object value = errors.getFieldValue(field);
        if (value == null) {
            errors.rejectValue(field, errorCode);
        }
    }
}