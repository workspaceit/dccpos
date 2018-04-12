package com.workspaceit.pos.validation.validator;

import com.workspaceit.pos.helper.DateHelper;
import com.workspaceit.pos.helper.ValidationHelper;
import com.workspaceit.pos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.pos.validation.form.personalIformation.PersonalInfoUpdateForm;
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

    }
    public void validateUpdate(String prefix, PersonalInfoUpdateForm personalInfoForm, Errors errors){

        prefix  = ValidationHelper.preparePrefix(prefix);
        /**
         * Dob is optional
         * */
        if(!errors.hasFieldErrors(prefix+"dob") && personalInfoForm.getDob()!=null){
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
