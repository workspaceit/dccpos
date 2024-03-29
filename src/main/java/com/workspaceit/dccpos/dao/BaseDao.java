package com.workspaceit.dccpos.dao;


import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.helper .NumberHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Created by mi on 03/28/17.
 */

public class BaseDao {


    private SessionFactory sessionFactory;
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    protected void setSessionFactory(SessionFactory sessionFactory) {
        System.out.println(" ***** **** *** ** * SessionFactory Autowired For '"+this.getClass().getSimpleName()+"' * ** *** **** *****");
        this.sessionFactory = sessionFactory;
    }
    public void save(Object obj){
        Session session = this.getCurrentSession();
        session.save(obj);

    }
    public void saveOrUpdate(Object obj){
        Session session = this.getCurrentSession();
        session.saveOrUpdate(obj);
    }
    public void saveAll(Collection<? extends Object> entities){

        Session session = this.getCurrentSession();
        for (Object entity:entities) {
            session.save(entity);
        }
    }
    public void update(Object obj){
        Session session = this.getCurrentSession();
        session.update(obj);
    }

    public void updateAll(Collection<? extends Object> entities){
        Session session = this.getCurrentSession();
        for (Object entity:entities) {
            session.update(entity);
        }
    }
    public void delete(Object obj){
        Session session = this.getCurrentSession();
        session.delete(obj);
    }
    public void commit(){
        this.getCurrentSession().getTransaction().commit();
    }
    public void deleteAll(Collection<? extends Object> entities){
        Session session = this.getCurrentSession();
        for (Object entity:entities) {
            session.delete(entity);
        }
    }

    protected Session getCurrentSession() {
    	return this.sessionFactory.getCurrentSession();
    }
    protected Session openSession() {
        return this.sessionFactory.openSession();
    }
    public void clearCurrentSessionFirstLevelCache(){
        Session session =  this.getCurrentSession();
        if(session!=null){
            session.clear();
        }
    }
    public void clearCurrentSessionFirstLevelCache(Object obj){
        Session session =  this.getCurrentSession();
        if(session!=null){
            session.evict(obj);
        }
    }
    public long findAllRowCount(Class<?> cls){
        String entityName = cls.getSimpleName();
        long count =(Long)this.getCurrentSession().createQuery("select count(id) from "+entityName).uniqueResult();
        return count;
    }
    public long findMaxId(Class<?> cls){
        String entityName = cls.getSimpleName();
        Object maxId = this.getCurrentSession().createQuery("select max(id) as maxId from "+entityName).uniqueResult();
        return NumberHelper.getLong(maxId);
    }
    public long findMinId(Class<?> cls){
        String entityName = cls.getSimpleName();
        Object minId =this.getCurrentSession().createQuery("select min(id) from "+entityName).uniqueResult();
        return NumberHelper.getLong(minId);
    }


}