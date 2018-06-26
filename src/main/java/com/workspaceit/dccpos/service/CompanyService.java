package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.CompanyDao;
import com.workspaceit.dccpos.entity.Address;
import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.company.CompanyCreateForm;
import com.workspaceit.dccpos.validation.form.company.CompanyUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyService {

    private CompanyDao companyDao;
    private AddressService addressService;
    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }
    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public Company getCompany(int id) throws EntityNotFound {
        Company company = this.getById(id);

        if(company==null)throw new EntityNotFound("Company's Information not found by id:"+id);

        return company;

    }
    @Transactional
    public Company getById(int id){
        return this.companyDao.getById(id);
    }

    @Transactional
    public Company getByTitle(String title){
        return this.companyDao.getByTitle(title);
    }

    @Transactional
    public Company getByEmail(String email){
        return this.companyDao.getByEmail(email);
    }

    @Transactional
    public Company getByPhone(String name){
        return this.companyDao.getByPhone(name);
    }


    @Transactional
    public Company getByTitleAndNotByID(int id, String name){
        return this.companyDao.getByTitleAndNotById(id,name);
    }

    @Transactional
    public Company getByEmailAndNotByID(int id,String email){
        return this.companyDao.getByEmailAndNotById(id,email);
    }

    @Transactional
    public Company getByPhoneAndNotByID(int id,String phone){
        return this.companyDao.getByPhoneAndNotById(id,phone);
    }

    @Transactional
    public Object getByFieldName(Class<?> aClass, String objectName, String fieldName, String value){
        return this.companyDao.getByFieldName(aClass,objectName,fieldName,value);
    }
    @Transactional
    public Object getByFieldNameAndNotById(Class<?> aClass, String objectName, String fieldName, String value,int id){
        return this.companyDao.getByFieldNameAndNotById(aClass,objectName,fieldName,value,id);
    }


    public Company create(CompanyCreateForm companyCreateForm){
        Address address = new Address();
        address.setFormattedAddress(companyCreateForm.getAddress());


        Company  company = new Company();

        company.setTitle(companyCreateForm.getTitle());
        company.setPhone(companyCreateForm.getPhone());
        company.setEmail(companyCreateForm.getEmail());

        company.setAddress(address);

        this.save(company);

        return company;
    }

    public Company edit(int id, CompanyUpdateForm companyUpdateForm) throws EntityNotFound {

        Company  company = this.getCompany(id);

        Address address = company.getAddress();
        address.setFormattedAddress(companyUpdateForm.getAddress());

        company.setTitle(companyUpdateForm.getTitle());
        company.setPhone(companyUpdateForm.getPhone());
        company.setEmail(companyUpdateForm.getEmail());
        company.setAddress(address);

        this.update(company);

        return company;
    }
    private void save(Company company){
        this.companyDao.save(company);
    }
    private void update(Company company){
        this.companyDao.update(company);
    }
}