package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.helper.DateHelper;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoUpdateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Date;

@Component
public class PersonalInfoValidator {

    public void validate(String prefix, PersonalInfoCreateForm personalInfoForm, Errors errors){

        prefix  = ValidationHelper.preparePrefix(prefix);
        /**
         * Dob is optional
         * */
        if(!errors.hasFieldErrors(prefix+"dob") && personalInfoForm.getDob()!=null){
            validateDob(personalInfoForm.getDob(),errors,prefix);
        }
        if(!errors.hasFieldErrors(prefix+"email") && personalInfoForm.getEmail()!=null && !personalInfoForm.getEmail().trim().equals("")){
            this.validateEmail(personalInfoForm.getEmail(),errors,prefix);
        }


    }
    public void validateUpdate(String prefix, PersonalInfoUpdateForm personalInfoForm, Errors errors){

        prefix  = ValidationHelper.preparePrefix(prefix);
        /**
         * Dob is optional
         * */
        if(!errors.hasFieldErrors(prefix+"dob") && personalInfoForm.getDob()!=null){
            validateDob(personalInfoForm.getDob(),errors,prefix);
        }
        if(!errors.hasFieldErrors(prefix+"email") && personalInfoForm.getEmail()!=null && !personalInfoForm.getEmail().trim().equals("")){
            this.validateEmail(personalInfoForm.getEmail(),errors,prefix);
        }
    }
    public void validateDob(Date dob ,Errors errors,String prefix){
        Date now = DateHelper.getCurrentSystemDate();

        if( dob.getTime() >now.getTime() ){
            errors.rejectValue(prefix+"dob","You can't be from future !!");
        }
    }
    public void validateEmail(String email ,Errors errors,String prefix){
        boolean flag = ValidationHelper.isValidEmailAddress(email);
        if(!flag){
            errors.rejectValue(prefix+"email","Email is not valid "+email);

        }
    }
}
