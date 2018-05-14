package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryItemDao extends BaseDao{
    public double findBalance(int ledgerId, ACCOUNTING_ENTRY accountingEntry ){
        Session session = this.getCurrentSession();
        Object sumAmount = session.createQuery("select sum(ei.amount) from EntryItem ei " +
                " where ei.ledger.id=:ledgerId " +
                " and ei.accountingEntry=:accountingEntry")
                .setParameter("ledgerId",ledgerId)
                .setParameter("accountingEntry", accountingEntry)
                .uniqueResult();
        double count =(Double) ((sumAmount==null)?0d:sumAmount);
        return count;
    }

    public List<EntryItem> findByEntryId(int entryId){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM EntryItem ei where ei.entry.id =:entryId ")
                .setParameter("entryId",entryId)
                .list();
    }
}
