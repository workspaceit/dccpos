package com.workspaceit.pos.service;

import com.workspaceit.pos.constant.ACCESS_ACCOUNT_STATUS;
import com.workspaceit.pos.dao.AuthCredentialDao;
import com.workspaceit.pos.entity.AccessRole;
import com.workspaceit.pos.entity.AuthCredential;
import com.workspaceit.pos.entity.PersonalInformation;
import com.workspaceit.pos.entity.ResetPasswordToken;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.validation.form.authcredential.AuthCredentialCreateForm;
import com.workspaceit.pos.validation.form.authcredential.ChangePasswordForm;
import com.workspaceit.pos.validation.form.authcredential.PasswordResetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthCredentialService {
    private AuthCredentialDao authCredentialDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAuthCredentialDao(AuthCredentialDao authCredentialDao) {
        this.authCredentialDao = authCredentialDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthCredential getAuthCredential(int id) throws EntityNotFound {
        AuthCredential authCredential =  this.getById(id);

        if(authCredential == null)throw new EntityNotFound("AuthCredential not found by id :"+id);
        return authCredential;
    }
    @Transactional
    public AuthCredential getById(int id){
        return this.authCredentialDao.getById(id);
    }
    @Transactional
    public AuthCredential getByEmail(String email){
        return this.authCredentialDao.getByEmail(email);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(AuthCredential authCredential){
         this.authCredentialDao.update(authCredential);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(ResetPasswordToken resetPasswordToken,PasswordResetForm passwordResetForm)throws EntityNotFound{
        AuthCredential authCredential = resetPasswordToken.getAuthCredential();
        if(authCredential==null){
            throw new EntityNotFound("Entity not found");
        }
        authCredential.setPassword(this.passwordEncoder.encode(passwordResetForm.getPassword()));
        this.authCredentialDao.update(authCredential);
    }


    @Transactional(rollbackFor = Exception.class)
    public void changePassword(int id, ChangePasswordForm changePasswordForm)throws EntityNotFound{
        AuthCredential authCredential = this.getAuthCredential(id);
        if(authCredential==null){
            throw new EntityNotFound("Entity not found");
        }
        authCredential.setPassword(this.passwordEncoder.encode(changePasswordForm.getPassword()));
        this.authCredentialDao.update(authCredential);
    }
    @Transactional(rollbackFor = Exception.class)
    public void create(AuthCredentialCreateForm authCredentialForm, PersonalInformation personalInfo){
        String encodedPassword = this.passwordEncoder.encode(authCredentialForm.getPassword());

        AccessRole accessRole = new AccessRole();
        accessRole.setAccessRole(authCredentialForm.getAccessRole());

        Set<AccessRole> accessRoles = new HashSet<>();
        accessRoles.add(accessRole);

        AuthCredential authCredential = new AuthCredential();
        authCredential.setEmail(authCredentialForm.getEmail());
        authCredential.setPassword(encodedPassword);
        authCredential.setAccessRole(accessRoles);
        authCredential.setStatus(ACCESS_ACCOUNT_STATUS.ACTIVE);
        authCredential.setPersonalInformation(personalInfo);

        this.save(authCredential);
    }
    private void save(AuthCredential authCredential){
        this.authCredentialDao.save(authCredential);
    }

}