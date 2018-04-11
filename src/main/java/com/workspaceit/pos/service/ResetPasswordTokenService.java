package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.ResetPasswordTokenDao;
import com.workspaceit.pos.entity.AuthCredential;
import com.workspaceit.pos.entity.ResetPasswordToken;
import com.workspaceit.pos.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ResetPasswordTokenService {
    private ResetPasswordTokenDao resetPasswordTokenDao;
    private AuthCredentialService authCredentialService;

    @Autowired
    public void setResetPasswordTokenDao(ResetPasswordTokenDao resetPasswordTokenDao) {
        this.resetPasswordTokenDao = resetPasswordTokenDao;
    }

    @Autowired
    public void setAuthCredentialService(AuthCredentialService authCredentialService) {
        this.authCredentialService = authCredentialService;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResetPasswordToken create(String email) throws EntityNotFound{
        AuthCredential authCredential = this.authCredentialService.getByEmail(email);

        if(authCredential==null){
            throw new EntityNotFound("No information found by email : "+email);
        }

        String token =  UUID.randomUUID().toString()+String.valueOf(authCredential.getEmail().hashCode());

        ResetPasswordToken resetPasswordToken = this.resetPasswordTokenDao.findByAuthCredentialId(authCredential.getId());

        if(resetPasswordToken==null){
            resetPasswordToken = new ResetPasswordToken();
        }

        resetPasswordToken.setAuthCredential(authCredential);
        resetPasswordToken.setToken(token);
        resetPasswordToken.setCreatedAt(new Date());

        this.resetPasswordTokenDao.saveOrUpdate(resetPasswordToken);

        return resetPasswordToken;
    }
    @Transactional
    public ResetPasswordToken getByToken(String token) throws EntityNotFound{

        ResetPasswordToken resetPasswordToken = this.resetPasswordTokenDao.findByToken(token);

        if(resetPasswordToken==null){
            throw  new EntityNotFound("No information found by token "+token);
        }

        return resetPasswordToken;
    }
    @Transactional(rollbackFor = Exception.class)
    public void remove(ResetPasswordToken resetPasswordToken){
        this.resetPasswordTokenDao.delete(resetPasswordToken);
    }

    public boolean isResetPasswordTokenExpired(ResetPasswordToken resetPasswordToken) {

        boolean isExpired = false;
        Date createdDate = resetPasswordToken.getCreatedAt();

        Date now = new Date();

        long diff = now.getTime()-createdDate.getTime();
        long days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        System.out.println(now);
        System.out.println(createdDate);

        System.out.println(days);
        isExpired = (days>0)?true:false;
        return isExpired;
    }

    public static void main(String[] args) {

        Date now = new Date("2017-01-01");
        Date now1 = new Date();
        long diff = now.getTime()-now1.getTime();
        long days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        System.out.println(now);
        System.out.println(now1);
        System.out.println(days);

    }
}