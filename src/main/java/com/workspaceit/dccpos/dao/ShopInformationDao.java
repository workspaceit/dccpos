package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.ShopInformation;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Matin on 4/25/2018.
 */
@Repository
public class ShopInformationDao extends BaseDao{

    public List<ShopInformation> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM ShopInformation")
                .list();
    }
    public ShopInformation getById(int id){
        Session session = this.getCurrentSession();
        return (ShopInformation)session.createQuery(" FROM ShopInformation sp where sp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }

    public ShopInformation getOne(){
        Session session = this.getCurrentSession();
        return (ShopInformation)session.createQuery(" FROM ShopInformation sp ")
                .setMaxResults(1)
                .uniqueResult();
    }
}
