package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.Company;
import com.workspaceit.pos.entity.PersonalInformation;
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
}