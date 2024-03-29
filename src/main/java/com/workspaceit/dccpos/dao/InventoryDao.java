package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.constant.STOCK_STATUS;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.validation.form.inventory.InventorySearchForm;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryDao extends BaseDao{
    public List<Inventory> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Inventory inv")
                .list();
    }
    public Inventory findById(long id){
        Session session = this.getCurrentSession();
        return (Inventory)session.createQuery(" FROM Inventory inv where inv.id =:id ")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public List<Inventory> findByProductIdAndStockStatus(int productId, STOCK_STATUS stockStatus){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Inventory inv where inv.product.id =:productId and inv.status =:stockStatus")
                .setParameter("productId",productId)
                .setParameter("stockStatus",stockStatus)
                .list();
    }
    public List<Inventory> findByProductIdAndStockStatus(int productId, STOCK_STATUS stockStatus, PRODUCT_CONDITION condition){

        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Inventory inv where inv.product.id =:productId " +
                " and inv.status =:stockStatus " +
                " and inv.condition=:condition")
                .setParameter("productId",productId)
                .setParameter("stockStatus",stockStatus)
                .setParameter("condition",condition)
                .list();
    }
}
