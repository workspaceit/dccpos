package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.ResetPasswordToken;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ResetPasswordTokenDao extends BaseDao {

    public ResetPasswordToken findByToken(String token){
        Session session = this.sessionFactory.getCurrentSession();
        ResetPasswordToken passwordResetToken = (ResetPasswordToken) session.createQuery("FROM ResetPasswordToken where token=:token")
                .setParameter("token",token)
                .setMaxResults(1)
                .uniqueResult();
        return passwordResetToken;
    }
    public ResetPasswordToken findByAuthCredentialId(int authCredentialId){
        Session session = this.sessionFactory.getCurrentSession();
        ResetPasswordToken passwordResetToken = (ResetPasswordToken) session.createQuery("FROM ResetPasswordToken where authCredential.id=:authCredentialId")
                .setParameter("authCredentialId",authCredentialId)
                .setMaxResults(1)
                .uniqueResult();
        return passwordResetToken;
    }
}
