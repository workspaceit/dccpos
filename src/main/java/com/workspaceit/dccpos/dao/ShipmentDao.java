package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Shipment;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ShipmentDao extends BaseDao {

    public Shipment getById(int id){
        Session session = this.getCurrentSession();
        return (Shipment)session.createQuery(" FROM Shipment sp where sp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
}
