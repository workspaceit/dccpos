package com.workspaceit.dccpos.dao.accounting;

import com.workspaceit.dccpos.constant.accounting.ENTRY_TYPES;
import com.workspaceit.dccpos.dao.BaseDao;
import com.workspaceit.dccpos.entity.accounting.EntryType;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class EntryTypeDao extends BaseDao {
    public EntryType findByLabel(ENTRY_TYPES entryType){
        Session session = this.getCurrentSession();
        return (EntryType)session.createQuery("FROM EntryType where label=:entryType")
                .setParameter("entryType",entryType)
                .setMaxResults(1)
                .uniqueResult();
    }
}
