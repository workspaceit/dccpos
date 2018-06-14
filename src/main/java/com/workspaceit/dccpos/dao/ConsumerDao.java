package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Consumer;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConsumerDao extends BaseDao{
    public List<Consumer> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Consumer c ")
                .list();
    }
    public Consumer findById(int id){
        Session session = this.getCurrentSession();
        return (Consumer)session.createQuery(" FROM Consumer c where c.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Consumer findByConsumerId(String consumerId){
        Session session = this.getCurrentSession();
        return (Consumer)session.createQuery(" FROM Consumer c where c.consumerId =:consumerId")
                .setParameter("consumerId",consumerId)
                .setMaxResults(1)
                .uniqueResult();
    }

}
