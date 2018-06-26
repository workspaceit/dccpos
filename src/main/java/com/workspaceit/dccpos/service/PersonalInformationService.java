package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.COMPANY_ROLE;
import com.workspaceit.dccpos.dao.PersonalInformationDao;
import com.workspaceit.dccpos.entity.Address;
import com.workspaceit.dccpos.entity.CompanyRole;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PersonalInformationService {
    private AddressService addressService;

    private PersonalInformationDao personalInformationDao;

    @Autowired
    public void setPersonalInformationDao(PersonalInformationDao personalInformationDao) {
        this.personalInformationDao = personalInformationDao;
    }
    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }


    public PersonalInformation getPersonalInformation(int id) throws EntityNotFound {
        PersonalInformation personalInformation = this.getById(id);

        if(personalInformation==null)throw new EntityNotFound("Personal Information not found by id:"+id);

        return personalInformation;

    }
    public PersonalInformation getById(int id){
        return this.personalInformationDao.getById(id);
    }
    public PersonalInformation getByEmail(String email){
        return this.personalInformationDao.getByEmail(email);
    }
    public PersonalInformation getByPhone(String phone){
        return this.personalInformationDao.getByPhone(phone);
    }
    public PersonalInformation getByEmailNotById(int id,String email){
        return this.personalInformationDao.getByEmailNotById(id,email);
    }
    public PersonalInformation getByPhoneNotById(int id,String phone){
        return this.personalInformationDao.getByPhoneNotById(id,phone);
    }

    public PersonalInformation create(PersonalInfoCreateForm personalInfoForm,COMPANY_ROLE role){
        Address address = new Address();
        address.setFormattedAddress(personalInfoForm.getAddress());

        CompanyRole companyRole = new CompanyRole();
        companyRole.setCompanyRole(role);

        Set<CompanyRole> companyRoles = new HashSet<>();
        companyRoles.add(companyRole);

        PersonalInformation  personalInformation = new PersonalInformation();

        personalInformation.setFullName(personalInfoForm.getFullName());
        personalInformation.setDob(personalInfoForm.getDob());
        personalInformation.setPhone(personalInfoForm.getPhone());
        personalInformation.setEmail(personalInfoForm.getEmail());

        personalInformation.setCompanyRoles(companyRoles);
        personalInformation.setAddress(address);

        this.save(personalInformation);

        return personalInformation;
    }

    public PersonalInformation edit(int id, PersonalInfoUpdateForm personalInfoForm) throws EntityNotFound {

        PersonalInformation  personalInformation = this.getPersonalInformation(id);
        Address address = personalInformation.getAddress();
        address.setFormattedAddress(personalInfoForm.getAddress());

        personalInformation.setFullName(personalInfoForm.getFullName());
        personalInformation.setDob(personalInfoForm.getDob());
        personalInformation.setPhone(personalInfoForm.getPhone());
        personalInformation.setEmail(personalInfoForm.getEmail());

        personalInformation.setAddress(address);

        this.update(personalInformation);

        return personalInformation;
    }
    private void save(PersonalInformation personalInformation){
        this.personalInformationDao.save(personalInformation);
    }
    private void update(PersonalInformation personalInformation){
        this.personalInformationDao.update(personalInformation);
    }
}