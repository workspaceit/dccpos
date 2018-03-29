package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.AuthCredential;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class AuthCredentialDao extends BaseDao {
    public AuthCredential getByEmail(String email){
        Session session = this.getCurrentSession();
        return (AuthCredential)session.createQuery("FROM AuthCredential  WHERE email=:email")
                .setParameter("email",email)
                .setMaxResults(1)
                .uniqueResult();
    }

}