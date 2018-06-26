package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.SupplierDao;
import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.validation.form.supplier.SupplierCreateForm;
import com.workspaceit.dccpos.validation.form.supplier.SupplierUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {
    private SupplierDao supplierDao;
    private CompanyService companyService;
    private LedgerService ledgerService;
    @Autowired
    public void setSupplierDao(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Transactional
    public List<Supplier> getAll(){
        return this.supplierDao.getAll();
    }

    @Transactional
    public Supplier getById(int id){
        return this.supplierDao.getById(id);
    }
    @Transactional
    public Supplier getByCompanyTitle(String companyTitle){
        return this.supplierDao.getByCompanyTitle(companyTitle);
    }
    @Transactional
    public Supplier getByCompanyEmail(String companyEmail){
        return this.supplierDao.getByCompanyEmail(companyEmail);
    }

    @Transactional
    public Supplier getByCompanyPhone(String companyPhone){
        return this.supplierDao.getByCompanyPhone(companyPhone);
    }
    @Transactional
    public Supplier getByCompanyTitleAndNotById(int id,String companyTitle){
        return this.supplierDao.getByCompanyTitleAndNotById(id,companyTitle);
    }
    @Transactional
    public Supplier getByCompanyEmailAndNotById(int id,String companyEmail){
        return this.supplierDao.getByCompanyEmailAndNotById(id,companyEmail);
    }
    @Transactional
    public Supplier getByCompanyPhoneAndNotById(int id,String companyEmail){
        return this.supplierDao.getByCompanyPhoneAndNotById(id,companyEmail);
    }
    @Transactional
    public Supplier getSupplier(int id) throws EntityNotFound {
        Supplier supplier =  this.getById(id);

        if(supplier==null)throw new EntityNotFound("Supplier not found by id : "+id);

        return supplier;
    }
    @Transactional
    public Supplier getBySupplierId(String supplierId){
        return this.supplierDao.getBySupplierId(supplierId);
    }

    @Transactional
    public Supplier getBySupplierIdAndNotById(String employeeId,int id){
        return this.supplierDao.getBySupplierIdAndNotById(employeeId,id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Supplier create(SupplierCreateForm supplierCreateForm){
        Company company =  this.companyService.create(supplierCreateForm.getCompany());
        String supplierId = null;
        if(supplierCreateForm.getSupplierId()!=null && !supplierCreateForm.getSupplierId().trim().equals("")){
            supplierId = supplierCreateForm.getSupplierId();
        }
        /**
         * Creating Supplier Ledger
         * */
        this.ledgerService.createSupplierLedger(company);

        Supplier supplier = new Supplier();
        supplier.setCompany(company);
        supplier.setSupplierId(supplierId);
        this.save(supplier);


        return supplier;
    }
    @Transactional(rollbackFor = Exception.class)
    public Supplier edit(int id, SupplierUpdateForm supplierUpdateForm) throws EntityNotFound {
        Supplier supplier = this.getSupplier(id);
        String supplierId = null;
        if(supplierUpdateForm.getSupplierId()!=null && !supplierUpdateForm.getSupplierId().trim().equals("")){
            supplierId = supplierUpdateForm.getSupplierId();
        }

        Company company =  this.companyService.edit(supplier.getCompany().getId(),supplierUpdateForm.getCompany());
        supplier.setCompany(company);
        supplier.setSupplierId(supplierId);

        this.update(supplier);

        /**
         * Creating Supplier Ledger
         * */
        this.ledgerService.editSupplierLedger(company);
        return supplier;
    }
    private void save(Supplier supplier){
        this.supplierDao.save(supplier);
    }
    private void update(Supplier supplier){
        this.supplierDao.update(supplier);
    }
}