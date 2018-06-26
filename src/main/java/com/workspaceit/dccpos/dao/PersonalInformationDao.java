package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.PersonalInformation;
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
    public PersonalInformation getByEmail(String email){
        Session session = this.getCurrentSession();
        return (PersonalInformation)session.createQuery(" FROM PersonalInformation pi where pi.email =:email")
                .setParameter("email",email)
                .setMaxResults(1)
                .uniqueResult();
    }
    public PersonalInformation getByPhone(String phone){
        Session session = this.getCurrentSession();
        return (PersonalInformation)session.createQuery(" FROM PersonalInformation pi where pi.phone =:phone")
                .setParameter("phone",phone)
                .setMaxResults(1)
                .uniqueResult();
    }
    public PersonalInformation getByEmailNotById(int id,String email){
        Session session = this.getCurrentSession();
        return (PersonalInformation)session.createQuery(" FROM PersonalInformation pi where pi.email =:email and pi.id !=:id")
                .setParameter("email",email)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public PersonalInformation getByPhoneNotById(int id,String phone){
        Session session = this.getCurrentSession();
        return (PersonalInformation)session.createQuery(" FROM PersonalInformation pi where pi.phone =:phone and pi.id !=:id")
                .setParameter("phone",phone)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
}