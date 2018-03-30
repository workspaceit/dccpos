package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.accounting.GroupAccount;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupAccountDao extends BaseDao {
    public List<GroupAccount> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("select distinct ga FROM GroupAccount ga left join fetch ga.child ")
                .list();
    }
}