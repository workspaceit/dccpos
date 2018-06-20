package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public class EntryItemDao extends BaseDao{
    public double findSum(int ledgerId, ACCOUNTING_ENTRY accountingEntry ){
        Session session = this.getCurrentSession();
        Object sumAmount = session.createQuery("select sum(ei.amount) from EntryItem ei " +
                " where ei.ledger.id=:ledgerId " +
                " and ei.accountingEntry=:accountingEntry")
                .setParameter("ledgerId",ledgerId)
                .setParameter("accountingEntry", accountingEntry)
                .uniqueResult();
        double sum =(Double) ((sumAmount==null)?0d:sumAmount);
        return sum;
    }
    public double findSum(int ledgerId, ACCOUNTING_ENTRY accountingEntry,Date start, Date finish){


        Session session = this.getCurrentSession();
        Object sumAmount = session.createQuery("select sum(ei.amount) from EntryItem ei " +
                " where ei.ledger.id=:ledgerId " +
                " and ei.accountingEntry=:accountingEntry " +
                "and ei.entry.date >= :start and ei.entry.date <= :finish ")
                .setParameter("ledgerId",ledgerId)
                .setParameter("accountingEntry", accountingEntry)
                .setParameter("start", start, TemporalType.DATE)
                .setParameter("finish", finish,TemporalType.DATE)
                .uniqueResult();
        double sum =(Double) ((sumAmount==null)?0d:sumAmount);
        return sum;
    }
    public List<EntryItem> findByEntryId(int entryId){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM EntryItem ei where ei.entry.id =:entryId ")
                .setParameter("entryId",entryId)
                .list();
    }
}
