package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.entity.Wholesaler;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.CompanyService;
import com.workspaceit.dccpos.service.WholesalerService;
import com.workspaceit.dccpos.validation.form.company.CompanyForm;
import com.workspaceit.dccpos.validation.form.wholesaler.WholesalerCreateForm;
import com.workspaceit.dccpos.validation.form.wholesaler.WholesalerUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class WholesalerValidator {
    private CompanyValidator companyValidator;
    private WholesalerService wholesalerService;
    private CompanyService companyService;

    @Autowired
    public void setCompanyValidator(CompanyValidator companyValidator) {
        this.companyValidator = companyValidator;
    }


    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void validate(WholesalerCreateForm wholesalerCreateForm, Errors errors){
        CompanyForm companyForm = wholesalerCreateForm.getCompany();
        String companyPrefix = "company.";

        if(wholesalerCreateForm.getWholesalerId()!=null && !wholesalerCreateForm.getWholesalerId().trim().equals("")) {
            this.validateUniqueWholesalerId(wholesalerCreateForm.getWholesalerId(), errors);
        }

        if(!errors.hasFieldErrors(companyPrefix+"title") && companyForm.getTitle()!=null && !companyForm.getTitle().trim().equals("")){
            this.validateUniqueTitle(companyPrefix,companyForm.getTitle(),errors);
        }
        if(!errors.hasFieldErrors(companyPrefix+"email") && companyForm.getEmail()!=null && !companyForm.getEmail().trim().equals("")){
            this.validateUniqueEmail(companyPrefix,companyForm.getEmail(),errors);
        }
        if(!errors.hasFieldErrors(companyPrefix+"phone") && companyForm.getPhone()!=null && !companyForm.getPhone().trim().equals("")){
            this.validateUniquePhone(companyPrefix,companyForm.getPhone(),errors);
        }


        if(wholesalerCreateForm.getCompany()!=null){
            this.companyValidator.validate("company",wholesalerCreateForm.getCompany(),errors);
        }




    }
    public void validateUpdate(int id, WholesalerUpdateForm wholesalerUpdateForm, Errors errors) throws EntityNotFound {
        Wholesaler wholesaler = this.wholesalerService.getWholesaler(id);
        Company company = wholesaler.getCompany();

        if(company==null)throw new EntityNotFound("Company not found by wholesaler id :"+id);

        String companyPrefix = "company.";
        CompanyForm companyForm = wholesalerUpdateForm.getCompany();

        if(wholesalerUpdateForm.getWholesalerId()!=null && !wholesalerUpdateForm.getWholesalerId().trim().equals("")) {

            this.validateWholesalerIdUsedByOthers(wholesalerUpdateForm.getWholesalerId(),id,errors);
        }
        if(wholesalerUpdateForm.getCompany()!=null){
            this.companyValidator.validateUpdate("company",company.getId(),wholesalerUpdateForm.getCompany(),errors);
        }

        if(!errors.hasFieldErrors(companyPrefix+"title") && companyForm.getTitle()!=null && !companyForm.getTitle().trim().equals("")){
            this.validateUniqueTitleAndNotById(companyPrefix,wholesaler.getId(),companyForm.getTitle(),errors);
        }
        if(!errors.hasFieldErrors(companyPrefix+"email") && companyForm.getEmail()!=null && !companyForm.getEmail().trim().equals("")){
            this.validateUniqueEmailAndNotById(companyPrefix,wholesaler.getId(),companyForm.getEmail(),errors);
        }
        if(!errors.hasFieldErrors(companyPrefix+"phone") && companyForm.getPhone()!=null && !companyForm.getPhone().trim().equals("")){
            this.validateUniquePhoneAndNotById(companyPrefix,wholesaler.getId(),companyForm.getPhone(),errors);
        }
    }
    public void validateUniqueWholesalerId(String employeeId, Errors error){
        Wholesaler Wholesaler =  this.wholesalerService.getByWholesalerId(employeeId);
        if(Wholesaler!=null){
            error.rejectValue("wholesalerId","Wholesaler Id already been used by : "+Wholesaler.getCompany().getTitle());
        }
    }
    public void validateWholesalerIdUsedByOthers(String employeeId, int id, Errors error){
        Wholesaler employee =  this.wholesalerService.getByWholesalerIdAndNotById(employeeId,id);
        if(employee!=null){
            error.rejectValue("wholesalerId","Wholesaler Id already been used by : "+employee.getCompany().getTitle());
        }
    }
    public void validateUniqueTitle(String prefix,String companyTitle, Errors error){
        if(companyTitle==null)return;
        Wholesaler Wholesaler =  (Wholesaler)this.companyService.getByFieldName(Wholesaler.class,"company","title",companyTitle);
        if(Wholesaler!=null){
            error.rejectValue(prefix+"title","Title already been used");
        }
    }
    public void validateUniqueEmail(String prefix,String companyEmail, Errors error){
        if(companyEmail==null)return;
        Wholesaler Wholesaler =  (Wholesaler)this.companyService.getByFieldName(Wholesaler.class,"company","email",companyEmail);
        if(Wholesaler!=null){
            error.rejectValue(prefix+"email","Email already been used");
        }
    }

    public void validateUniquePhone(String prefix,String companyPhone, Errors error){
        if(companyPhone==null)return;
        Wholesaler Wholesaler =  (Wholesaler)this.companyService.getByFieldName(Wholesaler.class,"company","phone",companyPhone);
        if(Wholesaler!=null){
            error.rejectValue(prefix+"phone","Phone already been used");
        }
    }

    public void validateUniqueTitleAndNotById(String prefix, int id, String companyTitle, Errors error){
        if(companyTitle==null)return;
        Wholesaler Wholesaler =  (Wholesaler)this.companyService.getByFieldNameAndNotById(Wholesaler.class,"company","title",companyTitle,id);
        if(Wholesaler!=null){
            error.rejectValue(prefix+"title","Title already been used");
        }
    }
    public void validateUniqueEmailAndNotById(String prefix, int id, String companyEmail, Errors error){
        if(companyEmail==null)return;
        Wholesaler Wholesaler =  (Wholesaler)this.companyService.getByFieldNameAndNotById(Wholesaler.class,"company","email",companyEmail,id);
        if(Wholesaler!=null){
            error.rejectValue(prefix+"email","Email already been used");
        }
    }

    public void validateUniquePhoneAndNotById(String prefix, int id, String companyPhone, Errors error){
        if(companyPhone==null)return;
        Wholesaler Wholesaler =  (Wholesaler)this.companyService.getByFieldNameAndNotById(Wholesaler.class,"company","phone",companyPhone,id);
        if(Wholesaler!=null){
            error.rejectValue(prefix+"phone","Phone already been used");
        }
    }

}