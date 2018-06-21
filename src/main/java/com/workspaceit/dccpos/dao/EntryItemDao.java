package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Double> criteriaQuery = builder.createQuery(Double.class);
        Root<EntryItem> root = criteriaQuery.from(EntryItem.class);
        Expression<Double> sum = builder.sum(root.get("amount"));
        criteriaQuery.select(sum);
        List<Predicate> predicates = new ArrayList<>();
        if(start!=null && finish!=null){
            predicates.add(builder.between(root.get("entry").get("date"),start,finish));
        }else if(start!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("entry").get("date"),start));
        }else if(finish!=null){
            predicates.add(builder.lessThanOrEqualTo(root.get("entry").get("date"),finish));

        }
        predicates.add(builder.equal(root.get("ledger").get("id"),ledgerId));
        predicates.add(builder.equal(root.get("accountingEntry"),accountingEntry));

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        Query<Double> query = session.createQuery(criteriaQuery);


       /* Object sumAmount = session.createQuery("select sum(ei.amount) from EntryItem ei " +
                " where ei.ledger.id=:ledgerId " +
                " and ei.accountingEntry=:accountingEntry " +
                "and ei.entry.date >= :start and ei.entry.date <= :finish ")
                .setParameter("ledgerId",ledgerId)
                .setParameter("accountingEntry", accountingEntry)
                .setParameter("start", start, TemporalType.DATE)
                .setParameter("finish", finish,TemporalType.DATE)
                .uniqueResult();
        double sum =(Double) ((sumAmount==null)?0d:sumAmount);*/

        return (query.uniqueResult()==null)?0:query.uniqueResult();
    }
    public List<EntryItem> findByEntryId(int entryId){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM EntryItem ei where ei.entry.id =:entryId ")
                .setParameter("entryId",entryId)
                .list();
    }
}
