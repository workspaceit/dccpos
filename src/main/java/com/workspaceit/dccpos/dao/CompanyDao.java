package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Company;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDao extends BaseDao{
    public List<Company> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Company")
                .list();
    }
    public Company getById(int id){
        Session session = this.getCurrentSession();
        return (Company)session.createQuery(" FROM Company com where com.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Company getByTitle(String title){
        Session session = this.getCurrentSession();
        return (Company)session.createQuery(" FROM Company com where com.title =:title")
                .setParameter("title",title)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Company getByEmail(String email){
        Session session = this.getCurrentSession();
        return (Company)session.createQuery(" FROM Company com where com.email =:email")
                .setParameter("email",email)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Company getByPhone(String phone){
        Session session = this.getCurrentSession();
        return (Company)session.createQuery(" FROM Company com where com.phone =:phone")
                .setParameter("phone",phone)
                .setMaxResults(1)
                .uniqueResult();
    }

    public Company getByTitleAndNotById(int id,String title){
        Session session = this.getCurrentSession();
        return (Company)session.createQuery(" FROM Company com where com.id!=:id and com.title =:title")
                .setParameter("title",title)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Company getByEmailAndNotById(int id,String email){
        Session session = this.getCurrentSession();
        return (Company)session.createQuery(" FROM Company com where com.id!=:id and com.email =:email")
                .setParameter("email",email)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Company getByPhoneAndNotById(int id,String phone){
        Session session = this.getCurrentSession();
        return (Company)session.createQuery(" FROM Company com where com.id!=:id and com.phone =:phone")
                .setParameter("phone",phone)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }


    public Object getByFieldName(Class<?> cls, String objectName, String fieldName, String value){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM "+cls.getSimpleName()+" cls where cls."+objectName+"."+fieldName+" =:val")
                .setParameter("val",value)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Object getByFieldNameAndNotById(Class<?> cls, String objectName, String fieldName, String value,int id){

        Session session = this.getCurrentSession();
        return session.createQuery(" FROM "+cls.getSimpleName()+" cls where " +
                " cls."+objectName+"."+fieldName+" =:val "+
                " and cls.id!=:id")
                .setParameter("val",value)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
}