package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.dao.InventoryDetailsDao;
import com.workspaceit.dccpos.entity.InventoryDetails;
import com.workspaceit.dccpos.validation.form.inventoryDetails.InventoryDetailsCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryDetailsService {
    private InventoryDetailsDao inventoryDetailsDao;

    @Autowired
    public void setInventoryDetailsDao(InventoryDetailsDao inventoryDetailsDao) {
        this.inventoryDetailsDao = inventoryDetailsDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<InventoryDetails> create(InventoryDetailsCreateForm[] inventoryCreateFroms){
        List<InventoryDetails> inventoryDetailsList = new ArrayList<>();

        for(InventoryDetailsCreateForm  inventoryDetailsCreateForm :inventoryCreateFroms){
            InventoryDetails inventoryDetails =  this.create(inventoryDetailsCreateForm);
            inventoryDetailsList.add(inventoryDetails);
        }

        return inventoryDetailsList;
    }

    private InventoryDetails create(InventoryDetailsCreateForm inventoryCreateFrom){
        InventoryDetails  inventoryDetails = new InventoryDetails();

        inventoryDetails.setCondition(inventoryCreateFrom.getStatus());
        inventoryDetails.setPurchasedQuantity( inventoryCreateFrom.getPurchaseQuantity());
        inventoryDetails.setSellingPrice(inventoryCreateFrom.getSellingPrice());
        /**
         * Initially all quantity are Available Quantity
         * */
        inventoryDetails.setAvailableQuantity(inventoryCreateFrom.getPurchaseQuantity());
        return inventoryDetails;
    }

    public void merge(InventoryDetails inventory, InventoryDetailsCreateForm inventoryFrom){
        double sellingPrice = inventory.getSellingPrice();
        int purchasedQuantity = inventory.getPurchasedQuantity();

        double formSellingPrice = inventoryFrom.getSellingPrice()==null?0:inventoryFrom.getSellingPrice();
        int formPurchasedQuantity = inventoryFrom.getPurchaseQuantity()==null?0:inventoryFrom.getPurchaseQuantity();


        inventory.setSellingPrice(sellingPrice+formSellingPrice);
        inventory.setPurchasedQuantity(purchasedQuantity+formPurchasedQuantity);

    }
    public int getTotalPurchasedQuantity(List<InventoryDetails> inventoryDetails ){
       int totalQuantity = 0;

       if(inventoryDetails==null || inventoryDetails.size()==0)return totalQuantity;

       for(InventoryDetails inventoryDetail : inventoryDetails){
           totalQuantity += inventoryDetail.getPurchasedQuantity();
       }
       return totalQuantity;
    }

    private void save(InventoryDetails inventoryDetails){
        this.inventoryDetailsDao.save(inventoryDetails);
    }
    private void update(InventoryDetails inventoryDetails){
        this.inventoryDetailsDao.update(inventoryDetails);
    }
    private void save(List<InventoryDetails> inventoryDetailsList){
        this.inventoryDetailsDao.saveAll(inventoryDetailsList);
    }
    private void update(List<InventoryDetails> inventoryDetailsList){
        this.inventoryDetailsDao.updateAll(inventoryDetailsList);
    }
}