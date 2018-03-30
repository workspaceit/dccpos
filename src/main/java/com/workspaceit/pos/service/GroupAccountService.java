package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.GroupAccountDao;
import com.workspaceit.pos.entity.accounting.GroupAccount;
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

    public List<GroupAccount> getAll(){
        return this.groupAccountDao.getAll();
    }
}