package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Supplier;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierDao extends BaseDao{
    public List<Supplier> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM Supplier")
                .list();
    }
    public Supplier getById(int id){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getByCompanyTitle(String companyTitle){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.company.title =:companyTitle")
                .setParameter("companyTitle",companyTitle)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getByCompanyEmail(String companyEmail){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.company.email =:companyEmail")
                .setParameter("companyEmail",companyEmail)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getByCompanyPhone(String companyPhone){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.company.phone =:companyPhone")
                .setParameter("companyPhone",companyPhone)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getBySupplierId(String supplierId){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.supplierId =:supplierId")
                .setParameter("supplierId",supplierId)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getByCompanyTitleAndNotById(int id,String companyTitle){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.company.title =:companyTitle and sp.id !=:id")
                .setParameter("companyTitle",companyTitle)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getByCompanyEmailAndNotById(int id,String companyEmail){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.company.email =:companyEmail and sp.id !=:id")
                .setParameter("companyEmail",companyEmail)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getByCompanyPhoneAndNotById(int id,String companyPhone){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where sp.company.phone =:companyPhone and sp.id !=:id")
                .setParameter("companyPhone",companyPhone)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Supplier getBySupplierIdAndNotById(String supplierId,int id){
        Session session = this.getCurrentSession();
        return (Supplier)session.createQuery(" FROM Supplier sp where id!=:id and sp.supplierId =:supplierId and sp.id !=:id")
                .setParameter("supplierId",supplierId)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }

}