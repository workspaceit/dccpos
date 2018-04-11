package com.workspaceit.pos.validation.validator;

import com.workspaceit.pos.helper.DateHelper;
import com.workspaceit.pos.util.CustomValidationUtil;
import com.workspaceit.pos.validation.form.PersonalInfoForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Date;

@Component
public class PersonalInfoValidator {
    public void validate(String prefix,PersonalInfoForm personalInfoForm,Errors errors){
        if(prefix!=null && !prefix.trim().equals("")){
            prefix +=".";
        }else{
            prefix = "";
        }
        CustomValidationUtil.rejectIfEmptyOrWhitespace(errors,prefix+"fullName","Full name required");

        if(personalInfoForm.getDob()!=null){
            validateDob(personalInfoForm.getDob(),errors,prefix);
        }

    }
    public void validateDob(Date dob ,Errors errors,String prefix){
        Date now = DateHelper.getCurrentSystemDate();

        if( dob.getTime() >now.getTime() ){
            errors.rejectValue(prefix+"dob","You can't be from future !!");
        }
    }
}
