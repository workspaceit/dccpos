package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.AuthCredentialDao;
import com.workspaceit.pos.entity.AuthCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthCredentialService {
    private AuthCredentialDao authCredentialDao;

    @Autowired
    public void setAuthCredentialDao(AuthCredentialDao authCredentialDao) {
        this.authCredentialDao = authCredentialDao;
    }

    @Transactional
    public AuthCredential getByEmail(String email){
        return this.authCredentialDao.getByEmail(email);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(AuthCredential authCredential){
         this.authCredentialDao.update(authCredential);
    }

}