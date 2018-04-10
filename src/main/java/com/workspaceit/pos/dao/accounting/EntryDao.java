package com.workspaceit.pos.dao.accounting;

import com.workspaceit.pos.dao.BaseDao;
import com.workspaceit.pos.entity.accounting.Entry;
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