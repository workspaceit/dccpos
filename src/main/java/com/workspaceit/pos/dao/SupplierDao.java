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
    public Supplier getById(int id){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getBySupplierId(String supplierId){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.supplierId =:supplierId")
                .setParameter("supplierId",supplierId)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getBySupplierIdAndNotById(String supplierId,int id){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where id!=:id and sp.supplierId =:supplierId")
                .setParameter("supplierId",supplierId)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }

}