package com.workspaceit.pos.dao.accounting;

import com.workspaceit.pos.dao.BaseDao;
import com.workspaceit.pos.entity.accounting.Ledger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class LedgerDao extends BaseDao {
    public List<Ledger> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM Ledger")
                .list();
    }
}