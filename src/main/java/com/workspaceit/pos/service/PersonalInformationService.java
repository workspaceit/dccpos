package com.workspaceit.pos.service;

import com.workspaceit.pos.constant.COMPANY_ROLE;
import com.workspaceit.pos.dao.PersonalInformationDao;
import com.workspaceit.pos.entity.Address;
import com.workspaceit.pos.entity.CompanyRole;
import com.workspaceit.pos.entity.PersonalInformation;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.validation.form.personalIformation.PersonalInfoCreateForm;
import com.workspaceit.pos.validation.form.personalIformation.PersonalInfoUpdateForm;
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


    public PersonalInformation getPersonalInformation(int id) throws EntityNotFound {
        PersonalInformation personalInformation = this.getById(id);

        if(personalInformation==null)throw new EntityNotFound("Personal Information not found by id:"+id);

        return personalInformation;

    }
    public PersonalInformation getById(int id){
        return this.personalInformationDao.getById(id);
    }
    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public PersonalInformation create(PersonalInfoCreateForm personalInfoForm){
        Address address = new Address();
        address.setFormattedAddress(personalInfoForm.getAddress());

        CompanyRole companyRole = new CompanyRole();
        companyRole.setCompanyRole(COMPANY_ROLE.EMPLOYEE);

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

        CompanyRole companyRole = new CompanyRole();
        companyRole.setCompanyRole(COMPANY_ROLE.EMPLOYEE);

        Set<CompanyRole> companyRoles = new HashSet<>();
        companyRoles.add(companyRole);

        PersonalInformation  personalInformation = this.getPersonalInformation(id);
        Address address = personalInformation.getAddress();
        address.setFormattedAddress(personalInfoForm.getAddress());

        personalInformation.setFullName(personalInfoForm.getFullName());
        personalInformation.setDob(personalInfoForm.getDob());
        personalInformation.setPhone(personalInfoForm.getPhone());
        personalInformation.setEmail(personalInfoForm.getEmail());

        personalInformation.setCompanyRoles(companyRoles);
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