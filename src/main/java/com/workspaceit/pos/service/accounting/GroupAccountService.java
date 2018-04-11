package com.workspaceit.pos.service.accounting;

import com.workspaceit.pos.constant.accounting.GROUP_CODE;
import com.workspaceit.pos.dao.accounting.GroupAccountDao;
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

    @Transactional
    public List<GroupAccount> getAll(){
        return this.groupAccountDao.findAll();
    }

    @Transactional
    public GroupAccount getByCode(GROUP_CODE groupCode){
        return this.groupAccountDao.findByCode(groupCode);
    }
}