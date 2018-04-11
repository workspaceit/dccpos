package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.PersonalInformationDao;
import com.workspaceit.pos.entity.Address;
import com.workspaceit.pos.entity.PersonalInformation;
import com.workspaceit.pos.validation.form.PersonalInfoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PersonalInformation create(PersonalInfoForm personalInfoForm){
        Address address = new Address();
        address.setFormattedAddress(personalInfoForm.getAddress());

        PersonalInformation  personalInformation = new PersonalInformation();

        personalInformation.setFullName(personalInfoForm.getFullName());
        personalInformation.setDob(personalInfoForm.getDob());
        personalInformation.setPhone(personalInfoForm.getPhone());
        personalInformation.setEmail(personalInfoForm.getEmail());

        personalInformation.setAddress(address);
        //this.addressService.create(address);

        this.save(personalInformation);

        return personalInformation;
    }
    private void save(PersonalInformation personalInformation){
        this.personalInformationDao.save(personalInformation);
    }
}