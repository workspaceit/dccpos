package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.Product;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends BaseDao{
    public Product findById(int id){
        Session session = this.getCurrentSession();
        return (Product)session.createQuery(" FROM Product p where p.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Product findByBarcode(String barcode){
        Session session = this.getCurrentSession();
        return (Product)session.createQuery(" FROM Product p where p.barcode =:barcode")
                .setParameter("barcode",barcode)
                .setMaxResults(1)
                .uniqueResult();
    }
    public List<Product> findByNameLike(String nameLike){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Product p where p.name LIKE :nameLike")
                .setParameter("nameLike",nameLike+"%")
                .list();
    }
    public List<Product> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Product")
                .list();
    }

}
