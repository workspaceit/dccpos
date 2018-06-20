package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.dao.accounting.GroupAccountDao;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupAccountService {

    private GroupAccountDao groupAccountDao;

    @Autowired
    public void setGroupAccountDao(GroupAccountDao groupAccountDao) {
        this.groupAccountDao = groupAccountDao;
    }

    @Transactional
    public GroupAccount getById(int id){
        return this.groupAccountDao.findById(id);
    }
    @Transactional
    public GroupAccount getByIdLazy(int id){
        return this.groupAccountDao.findByIdLazy(id);
    }
    @Transactional
    public List<GroupAccount> getAll(){
        return this.groupAccountDao.findAll();
    }



    @Transactional
    public GroupAccount getByCode(GROUP_CODE groupCode){
        return this.groupAccountDao.findByCode(groupCode);
    }
}