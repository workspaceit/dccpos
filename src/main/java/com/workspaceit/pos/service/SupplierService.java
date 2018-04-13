package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.SupplierDao;
import com.workspaceit.pos.entity.Company;
import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.entity.Supplier;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.util.DataFilterUtil;
import com.workspaceit.pos.validation.form.employee.EmployeeUpdateForm;
import com.workspaceit.pos.validation.form.supplier.SupplierCreateForm;
import com.workspaceit.pos.validation.form.supplier.SupplierUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {
    private SupplierDao supplierDao;
    private CompanyService companyService;

    @Autowired
    public void setSupplierDao(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
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
    public Supplier getSupplier(int id) throws EntityNotFound {
        Supplier supplier =  this.getById(id);

        if(supplier==null)throw new EntityNotFound("Supplier not found by id : "+id);

        return supplier;
    }
    @Transactional
    public Supplier getBySupplierId(String employeeId){
        return this.supplierDao.getBySupplierId(employeeId);
    }

    @Transactional
    public Supplier getBySupplierIdAndNotById(String employeeId,int id){
        return this.supplierDao.getBySupplierIdAndNotById(employeeId,id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Supplier create(SupplierCreateForm supplierCreateForm){
        Company company =  this.companyService.create(supplierCreateForm.getCompany());

        Supplier supplier = new Supplier();
        supplier.setCompany(company);
        supplier.setSupplierId(supplierCreateForm.getSupplierId());
        this.save(supplier);

        return supplier;
    }
    @Transactional(rollbackFor = Exception.class)
    public Supplier edit(int id, SupplierUpdateForm supplierUpdateForm) throws EntityNotFound {
        Supplier supplier = this.getSupplier(id);

        Company company =  this.companyService.edit(supplier.getCompany().getId(),supplierUpdateForm.getCompany());

        supplier.setCompany(company);
        supplier.setSupplierId(supplierUpdateForm.getSupplierId());

        this.update(supplier);

        return supplier;
    }
    private void save(Supplier supplier){
        this.supplierDao.save(supplier);
    }
    private void update(Supplier supplier){
        this.supplierDao.update(supplier);
    }
}