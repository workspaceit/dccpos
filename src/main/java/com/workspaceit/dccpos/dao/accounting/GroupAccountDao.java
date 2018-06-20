package com.workspaceit.dccpos.dao.accounting;

import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.dao.BaseDao;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupAccountDao extends BaseDao {
    public GroupAccount findParentById(int id){
        Session session = this.getCurrentSession();
        return (GroupAccount)session.createQuery("select distinct ga FROM GroupAccount ga where ga.id=:id ")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public GroupAccount findByIdLazy(int id){
        Session session = this.getCurrentSession();
        return (GroupAccount)session.createQuery("select distinct ga FROM GroupAccount ga where ga.id=:id ")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public GroupAccount findById(int id){
        Session session = this.getCurrentSession();
        return (GroupAccount)session.createQuery("select distinct ga FROM GroupAccount ga left join fetch ga.child where ga.id=:id ")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
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