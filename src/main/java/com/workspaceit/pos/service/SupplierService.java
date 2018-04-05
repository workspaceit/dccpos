package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.SupplierDao;
import com.workspaceit.pos.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {
    private SupplierDao supplierDao;

    @Autowired
    public void setSupplierDao(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @Transactional
    public List<Supplier> getAll(){
        return this.supplierDao.getAll();
    }
}