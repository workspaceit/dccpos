package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.Product;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends BaseDao{
    public List<Product> findAll(int limit,int offset){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Product")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
    }


    public Product findById(int id){
        Session session = this.getCurrentSession();
        return (Product)session.createQuery(" FROM Product p where p.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public  List<Product> findByCategoryId(int categoryId,int limit,int offset){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Product p where p.category.id =:categoryId")
                .setParameter("categoryId",categoryId)
                .setMaxResults(offset)
                .setMaxResults(limit)
                .list();
    }
    public  long findByCategoryIdCount(int categoryId){
        Session session = this.getCurrentSession();
        return (Long)session.createQuery("select count(p.id)  FROM Product p where p.category.id =:categoryId")
                .setParameter("categoryId",categoryId)
                .uniqueResult();
    }
    public Product findByBarcode(String barcode){
        Session session = this.getCurrentSession();
        return (Product)session.createQuery(" FROM Product p where p.barcode =:barcode")
                .setParameter("barcode",barcode)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Product findByBarcodeAndNotById(int id,String barcode){
        Session session = this.getCurrentSession();
        return (Product)session.createQuery(" FROM Product p where p.id!=:id and p.barcode =:barcode")
                .setParameter("barcode",barcode)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public List<Product> findByNameLike(int limit,int offset,String nameLike){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Product p where p.name LIKE :nameLike")
                .setParameter("nameLike",nameLike+"%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
    }
    public long findByNameLikeCount(String nameLike){
        Session session = this.getCurrentSession();
        long count =(Long)session.createQuery("select count(p.id) from Product p where p.name LIKE :nameLike")
                                .setParameter("nameLike",nameLike+"%")
                                .uniqueResult();
        return count;
    }

    public List<Product> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Product")
                .list();
    }


}