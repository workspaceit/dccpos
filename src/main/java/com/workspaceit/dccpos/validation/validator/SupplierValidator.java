package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.service.SupplierService;
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

    public void validate(SupplierCreateForm supplierCreateForm, Errors error){
        if(!error.hasFieldErrors("supplierId") &&
                supplierCreateForm.getSupplierId()!=null && !supplierCreateForm.getSupplierId().trim().equals("")){
            this.validateUniqueSupplierId(supplierCreateForm.getSupplierId(),error);
        }

        if(supplierCreateForm.getCompany()!=null){
            this.companyValidator.validate("company",supplierCreateForm.getCompany(),error);
        }

    }
    public void validateUpdate(int id, SupplierUpdateForm supplierUpdateForm, Errors error){
        if(!error.hasFieldErrors("supplierId") &&
                supplierUpdateForm.getSupplierId()!=null && !supplierUpdateForm.getSupplierId().trim().equals("")){
            this.validateSupplierIdUsedByOthers(supplierUpdateForm.getSupplierId(),id,error);
        }

        if(supplierUpdateForm.getCompany()!=null){
            this.companyValidator.validateUpdate("company",supplierUpdateForm.getCompany(),error);
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
}