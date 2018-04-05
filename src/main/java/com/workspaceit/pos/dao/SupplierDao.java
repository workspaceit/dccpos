package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.Supplier;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierDao extends BaseDao{
    public List<Supplier> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM Supplier")
                .list();
    }
}