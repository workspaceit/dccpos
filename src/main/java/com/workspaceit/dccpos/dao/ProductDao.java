package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.validation.form.product.ProductSearchForm;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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

    private List<Predicate> buildPredicatesQueryFromProductSearchForm(Root<Product> root, CriteriaBuilder builder, ProductSearchForm productSearchForm){
        List<Predicate> predicates = new ArrayList<>();


        if(productSearchForm.getName()!=null && !productSearchForm.getName().equals("")){
            predicates.add(builder.like(root.get("name"),productSearchForm.getName()+"%"));
        }

        if(productSearchForm.getCategoryId()!=null && productSearchForm.getCategoryId()>0){
            predicates.add(builder.equal(root.get("category").get("id"),productSearchForm.getCategoryId()));
        }



        return predicates;
    }
    public List<Product> findAll(int limit, int offset, ProductSearchForm productSearchForm){
        Session session = this.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        List<Predicate> predicates = this.buildPredicatesQueryFromProductSearchForm(root,builder,productSearchForm);

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));


        Query<Product> query = session.createQuery(criteriaQuery);

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.list();
    }

    public long findCountOfAll( ProductSearchForm productSearchForm){
        Session session = this.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        Expression<Long>  count = builder.count(root.get("id"));
        criteriaQuery.select(count);

        List<Predicate> predicates = this.buildPredicatesQueryFromProductSearchForm(root,builder,productSearchForm);

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));


        Query<Long> query = session.createQuery(criteriaQuery);

        return query.uniqueResult();
    }

    public Product findById(int id){
        Session session = this.getCurrentSession();
        return (Product)session.createQuery("select distinct p FROM Product p  where p.id =:id")
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