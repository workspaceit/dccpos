package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Wholesaler;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WholesalerDao extends BaseDao{
    public List<Wholesaler> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM Wholesaler")
                .list();
    }
    public Wholesaler getById(int id){
        Session session = this.getCurrentSession();
        return (Wholesaler)session.createQuery(" FROM Wholesaler sp where sp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Wholesaler getByWholesalerId(String wholesalerId){
        Session session = this.getCurrentSession();
        return (Wholesaler)session.createQuery(" FROM Wholesaler sp where sp.wholesalerId =:wholesalerId")
                .setParameter("wholesalerId",wholesalerId)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Wholesaler getByWholesalerAndNotById(String wholesalerId,int id){
        Session session = this.getCurrentSession();
        return (Wholesaler)session.createQuery(" FROM Wholesaler sp where id!=:id and sp.wholesalerId =:wholesalerId")
                .setParameter("wholesalerId",wholesalerId)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
}