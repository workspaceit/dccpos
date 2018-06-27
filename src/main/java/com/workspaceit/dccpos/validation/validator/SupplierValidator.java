package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.SupplierService;
import com.workspaceit.dccpos.validation.form.company.CompanyForm;
import com.workspaceit.dccpos.validation.form.company.CompanyUpdateForm;
import com.workspaceit.dccpos.validation.form.supplier.SupplierCreateForm;
import com.workspaceit.dccpos.validation.form.supplier.SupplierUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class SupplierValidator {
    private CompanyValidator companyValidator;
    private SupplierService supplierService;

    @Autowired
    public void setCompanyValidator(CompanyValidator companyValidator) {
        this.companyValidator = companyValidator;
    }


    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void validate(SupplierCreateForm supplierCreateForm, Errors errors){
        CompanyForm  companyForm = supplierCreateForm.getCompany();
        String companyPrefix = "company.";
        if(!errors.hasFieldErrors("supplierId") &&
                supplierCreateForm.getSupplierId()!=null && !supplierCreateForm.getSupplierId().trim().equals("")){
            this.validateUniqueSupplierId(supplierCreateForm.getSupplierId(),errors);
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

        if(supplierCreateForm.getCompany()!=null){
            this.companyValidator.validate("company",supplierCreateForm.getCompany(),errors);
        }



    }
    public void validateUpdate(int id, SupplierUpdateForm supplierUpdateForm, Errors errors) throws EntityNotFound {
        Supplier supplier = this.supplierService.getSupplier(id);
        Company company = supplier.getCompany();
        if(company==null)throw new EntityNotFound("Company not found in supplier id : "+supplier.getId());

        String prefix = "company.";
        CompanyUpdateForm  companyUpdateForm = supplierUpdateForm.getCompany();

        if(!errors.hasFieldErrors("supplierId") &&
                supplierUpdateForm.getSupplierId()!=null && !supplierUpdateForm.getSupplierId().trim().equals("")){
            this.validateSupplierIdUsedByOthers(supplierUpdateForm.getSupplierId(),id,errors);
        }

        if(!errors.hasFieldErrors(prefix+"title") && companyUpdateForm.getTitle()!=null && !companyUpdateForm.getTitle().trim().equals("")){
            this.validateUniqueTitleNotById(prefix,id,companyUpdateForm.getTitle(),errors);
        }
        if(!errors.hasFieldErrors(prefix+"email") && companyUpdateForm.getEmail()!=null && !companyUpdateForm.getEmail().trim().equals("")){
            this.validateUniqueEmailNotById(prefix,id,companyUpdateForm.getEmail(),errors);
        }
        if(!errors.hasFieldErrors(prefix+"phone") && companyUpdateForm.getPhone()!=null && !companyUpdateForm.getPhone().trim().equals("")){
            this.validateUniquePhoneNotById(prefix,id,companyUpdateForm.getPhone(),errors);
        }


        if(supplierUpdateForm.getCompany()!=null){
            this.companyValidator.validateUpdate("company",company.getId(),supplierUpdateForm.getCompany(),errors);
        }
    }
    public void validateUniqueSupplierId(String employeeId, Errors error){
        Supplier supplier =  this.supplierService.getBySupplierId(employeeId);
        if(supplier!=null){
            error.rejectValue("supplierId","Supplier Id already been used by : "+supplier.getCompany().getTitle());
        }
    }
    public void validateSupplierIdUsedByOthers(String employeeId, int id, Errors error){
        Supplier employee =  this.supplierService.getBySupplierIdAndNotById(employeeId,id);
        if(employee!=null){
            error.rejectValue("supplierId","Supplier Id already been used by : "+employee.getCompany().getTitle());
        }
    }

    public void validateUniqueTitle(String prefix,String title ,Errors errors){
        if(title==null)return;

        Supplier supplier = this.supplierService.getByCompanyTitle(title.trim());
        if(supplier!=null){
            errors.rejectValue(prefix+"title","Title already been used");
        }
    }

    public void validateUniqueEmail(String prefix,String email ,Errors errors){
        if(email==null)return;

        Supplier supplier = this.supplierService.getByCompanyEmail(email.trim());
        if(supplier!=null){
            errors.rejectValue(prefix+"email","Email already been used");
        }
    }
    public void validateUniquePhone(String prefix,String phone ,Errors errors){
        if(phone==null)return;

        Supplier supplier = this.supplierService.getByCompanyPhone(phone.trim());
        if(supplier!=null){
            errors.rejectValue(prefix+"phone","Phone already been used");
        }
    }
    public void validateUniqueTitleNotById(String prefix,int id,String title ,Errors errors){
        if(title==null)return;
        Supplier supplier = this.supplierService.getByCompanyTitleAndNotById(id,title.trim());
        if(supplier!=null){
            errors.rejectValue(prefix+"title","Title already been used");
        }
    }

    public void validateUniqueEmailNotById(String prefix,int id,String email ,Errors errors){
        if(email==null)return;
        Supplier supplier = this.supplierService.getByCompanyEmailAndNotById(id,email.trim());
        if(supplier!=null){
            errors.rejectValue(prefix+"email","Email already been used");
        }
    }
    public void validateUniquePhoneNotById(String prefix,int id,String phone ,Errors errors){
        if(phone==null)return;
        Supplier supplier = this.supplierService.getByCompanyPhoneAndNotById(id,phone.trim());
        if(supplier!=null){
            errors.rejectValue(prefix+"phone","Phone already been used");
        }
    }

    public static void main(String[] args) {
        System.out.println(Employee.class.getSimpleName());
    }
}