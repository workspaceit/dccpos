package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.AddressDao;
import com.workspaceit.pos.entity.Address;
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
