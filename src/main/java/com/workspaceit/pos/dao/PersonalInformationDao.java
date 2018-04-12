package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.PersonalInformation;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonalInformationDao extends BaseDao{
    public List<PersonalInformation> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM PersonalInformation")
                .list();
    }
    public PersonalInformation getById(int id){
        Session session = this.getCurrentSession();
        return (PersonalInformation)session.createQuery(" FROM PersonalInformation pi where pi.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
}