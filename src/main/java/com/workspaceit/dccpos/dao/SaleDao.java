package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Sale;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleDao extends BaseDao{
    public Sale getById(long id){
        Session session = this.getCurrentSession();
        return (Sale)session.createQuery("select distinct s FROM Sale s " +
                " left join fetch s.soldBy " +
                " left join fetch s.wholesaler " +
                " left join fetch s.consumer " +
                " left join fetch s.saleDetails " +
                "  WHERE s.id=:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public List<Sale> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM Sale s ")
                .list();
    }
}
