package com.workspaceit.dccpos.dao.accounting;

import com.workspaceit.dccpos.dao.BaseDao;
import com.workspaceit.dccpos.entity.accounting.Entry;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class EntryDao extends BaseDao {
    public List<Entry> findByDate(Date startDate, Date endDate){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM Entry")
                .list();
    }


}