package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.AuthCredential;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class AuthCredentialDao extends BaseDao {
    public AuthCredential getById(int id){
        Session session = this.getCurrentSession();
        return (AuthCredential)session.createQuery("FROM AuthCredential  WHERE id=:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public AuthCredential getByEmail(String email){
        Session session = this.getCurrentSession();
        return (AuthCredential)session.createQuery("FROM AuthCredential  WHERE email=:email")
                .setParameter("email",email)
                .setMaxResults(1)
                .uniqueResult();
    }

}