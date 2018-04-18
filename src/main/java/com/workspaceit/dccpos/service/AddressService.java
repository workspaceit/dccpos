package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.AddressDao;
import com.workspaceit.dccpos.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressService {
    private AddressDao addressDao;

    @Autowired
    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public Address create(Address address){
        this.addressDao.save(address);
        return address;
    }
}
