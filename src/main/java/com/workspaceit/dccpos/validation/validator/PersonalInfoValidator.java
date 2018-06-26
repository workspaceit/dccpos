package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.helper.DateHelper;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.PersonalInformationService;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Date;

@Component
public class PersonalInfoValidator {

    private PersonalInformationService personalInformationService;


    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

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
        if(!errors.hasFieldErrors(prefix+"email") && personalInfoForm.getEmail()!=null && !personalInfoForm.getEmail().trim().equals("")){
            this.validateUniqueEmail(personalInfoForm.getEmail(),errors,prefix);
        }
        if(!errors.hasFieldErrors(prefix+"phone") && personalInfoForm.getPhone()!=null && !personalInfoForm.getPhone().trim().equals("")){
            this.validateUniquePhone(personalInfoForm.getPhone(),errors,prefix);
        }

    }
    public void validateUpdate(String prefix,int id, PersonalInfoUpdateForm personalInfoForm, Errors errors){

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
        if(!errors.hasFieldErrors(prefix+"email") && personalInfoForm.getEmail()!=null && !personalInfoForm.getEmail().trim().equals("")){
            this.validateUniqueEmailNotById(id,personalInfoForm.getEmail(),errors,prefix);
        }
        if(!errors.hasFieldErrors(prefix+"phone") && personalInfoForm.getPhone()!=null && !personalInfoForm.getPhone().trim().equals("")){
            this.validateUniquePhoneNotById(id,personalInfoForm.getPhone(),errors,prefix);
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
    public void validateUniqueEmail(String email ,Errors errors,String prefix){
        if(email==null)return;
        PersonalInformation personalInformation = this.personalInformationService.getByEmail(email.trim());
        if(personalInformation!=null){
            errors.rejectValue(prefix+"email","Email is already taken");

        }
    }
    public void validateUniquePhone(String phone ,Errors errors,String prefix){
        if(phone==null)return;
        PersonalInformation personalInformation = this.personalInformationService.getByPhone(phone.trim());
        if(personalInformation!=null){
            errors.rejectValue(prefix+"phone","Phone is already taken");

        }
    }
    public void validateUniqueEmailNotById(int id,String email ,Errors errors,String prefix){
        if(email==null)return;
        PersonalInformation personalInformation = this.personalInformationService.getByEmailNotById(id,email.trim());
        if(personalInformation!=null){
            errors.rejectValue(prefix+"email","Email is already taken");

        }
    }
    public void validateUniquePhoneNotById(int id,String phone ,Errors errors,String prefix){
        if(phone==null)return;
        PersonalInformation personalInformation = this.personalInformationService.getByPhoneNotById(id,phone.trim());
        if(personalInformation!=null){
            errors.rejectValue(prefix+"phone","Phone is already taken");

        }
    }
}
