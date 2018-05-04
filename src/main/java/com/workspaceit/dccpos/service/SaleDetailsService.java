package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.SaleDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleDetailsService {
    private SaleDetailsDao saleDetailsDao;


    @Autowired
    public void setSaleDetailsDao(SaleDetailsDao saleDetailsDao) {
        this.saleDetailsDao = saleDetailsDao;
    }


}
