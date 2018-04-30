package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Shipment;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipmentDao extends BaseDao {

    public Shipment findById(int id){
        Session session = this.getCurrentSession();
        return (Shipment)session.createQuery("select distinct sp FROM Shipment sp " +
                " left join fetch sp.entry " +
                " left join fetch sp.inventories " +
                " where sp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public List<Shipment> findAll(int limit, int offset){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Shipment")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
    }
}
