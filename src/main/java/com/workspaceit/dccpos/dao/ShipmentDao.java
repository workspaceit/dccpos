package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentSearchForm;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class ShipmentDao extends BaseDao {

    public Shipment findById(int id){
        Session session = this.getCurrentSession();
        return (Shipment)session.createQuery("select distinct sp FROM Shipment sp " +
                " left join fetch sp.entry " +
                " left join fetch sp.inventories " +
                " where sp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Shipment findByTrackingId(String trackingId){
        Session session = this.getCurrentSession();
        return (Shipment)session.createQuery("select distinct sp FROM Shipment sp " +
                " left join fetch sp.entry " +
                " left join fetch sp.inventories " +
                " where sp.trackingId =:trackingId")
                .setParameter("trackingId",trackingId)
                .setMaxResults(1)
                .uniqueResult();
    }

    public List<Shipment> findAll(int limit, int offset){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Shipment")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
    }
    private List<Predicate> buildPredicatesQueryFromShipmentSearchForm(Root<Shipment> root, CriteriaBuilder builder, ShipmentSearchForm shipmentSearchForm){
        List<Predicate> predicates = new ArrayList<>();

        Date fromDate = shipmentSearchForm.getFromDate();
        Date toDate = shipmentSearchForm.getToDate();

        if(fromDate!=null && toDate!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("purchasedDate"),fromDate));
            predicates.add(builder.lessThanOrEqualTo(root.get("purchasedDate"),toDate));
        }else if(fromDate!=null){
            predicates.add(builder.equal(root.get("purchasedDate"),fromDate));

        }

        return predicates;
    }

    public List<Shipment> findAll(int limit, int offset, ShipmentSearchForm shipmentSearchForm){
        Session session = this.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Shipment> criteriaQuery = builder.createQuery(Shipment.class);
        Root<Shipment> root = criteriaQuery.from(Shipment.class);

        criteriaQuery.select(root);

        List<Predicate> predicates = this.buildPredicatesQueryFromShipmentSearchForm(root,builder,shipmentSearchForm);

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));


        Query<Shipment> query = session.createQuery(criteriaQuery);

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.list();
    }
    public long findCountOfAll(ShipmentSearchForm shipmentSearchForm){
        Session session = this.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Shipment> root = criteriaQuery.from(Shipment.class);

        Expression<Long> count = builder.count(root.get("id"));
        criteriaQuery.select(count);


        List<Predicate> predicates = this.buildPredicatesQueryFromShipmentSearchForm(root,builder,shipmentSearchForm);
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));


        Query<Long> query = session.createQuery(criteriaQuery);


        return query.uniqueResult();
    }
    public List<Shipment> findAllByDate(int limit, int offset,Date date){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Shipment shp where shp.purchasedDate =:date")
                .setParameter("date",date)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
    }
    public List<Shipment> findAllByDate(int limit, int offset,Date fromDate,Date toDate){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Shipment shp where shp.purchasedDate between :fromDate and :toDate")
                .setParameter("fromDate",fromDate)
                .setParameter("toDate",toDate)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
    }
    public long findCountOfAllByDate(Date date){
        Session session = this.getCurrentSession();
        return (long)session.createQuery("select count(shp.id) FROM Shipment shp where shp.purchasedDate =:date")
                .setParameter("date",date)
                .uniqueResult();
    }
    public long findCountOfAllByDate(Date fromDate,Date toDate){
        Session session = this.getCurrentSession();
        return (long)session.createQuery("select count(shp.id) FROM Shipment shp where shp.purchasedDate between :fromDate and :toDate")
                .setParameter("fromDate",fromDate)
                .setParameter("toDate",toDate)
                .uniqueResult();
    }
}
