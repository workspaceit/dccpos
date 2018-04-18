package com.workspaceit.dccpos.dao.accounting;

import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.dao.BaseDao;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupAccountDao extends BaseDao {
    public List<GroupAccount> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("select distinct ga FROM GroupAccount ga left join fetch ga.child ")
                .list();
    }
    public GroupAccount findByCode(GROUP_CODE groupCode){
        Session session = this.getCurrentSession();
        return (GroupAccount)session.createQuery("FROM GroupAccount ga where ga.code=:groupCode ")
                .setParameter("groupCode",groupCode)
                .setMaxResults(1)
                .uniqueResult();
    }
}