package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.ResetPasswordToken;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ResetPasswordTokenDao extends BaseDao {

    public ResetPasswordToken findByToken(String token){
        Session session = this.getCurrentSession();
        ResetPasswordToken passwordResetToken = (ResetPasswordToken) session.createQuery("FROM ResetPasswordToken where token=:token")
                .setParameter("token",token)
                .setMaxResults(1)
                .uniqueResult();
        return passwordResetToken;
    }
    public ResetPasswordToken findByAuthCredentialId(int authCredentialId){
        Session session = this.getCurrentSession();
        ResetPasswordToken passwordResetToken = (ResetPasswordToken) session.createQuery("FROM ResetPasswordToken where authCredential.id=:authCredentialId")
                .setParameter("authCredentialId",authCredentialId)
                .setMaxResults(1)
                .uniqueResult();
        return passwordResetToken;
    }
}
