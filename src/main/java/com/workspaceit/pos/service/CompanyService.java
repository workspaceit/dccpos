package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.CompanyDao;
import com.workspaceit.pos.entity.Address;
import com.workspaceit.pos.entity.Company;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.validation.form.company.CompanyCreateForm;
import com.workspaceit.pos.validation.form.company.CompanyUpdateForm;
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
    public Company getById(int id){
        return this.companyDao.getById(id);
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