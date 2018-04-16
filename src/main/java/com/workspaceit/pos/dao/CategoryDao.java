package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.Category;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao extends BaseDao{
    public Category findById(int id){
        Session session = this.getCurrentSession();
        return (Category)session.createQuery(" FROM Category c where c.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }

    public List<Category> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Category")
                .list();
    }

}
