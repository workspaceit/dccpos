package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.Wholesaler;
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
}