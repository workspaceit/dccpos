package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.WholesalerDao;
import com.workspaceit.pos.entity.Wholesaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WholesalerService {
    private WholesalerDao wholesalerDao;

    @Autowired
    public void setWholesalerDao(WholesalerDao wholesalerDao) {
        this.wholesalerDao = wholesalerDao;
    }



    @Transactional
    public List<Wholesaler> getAll(){
        return this.wholesalerDao.getAll();
    }
}