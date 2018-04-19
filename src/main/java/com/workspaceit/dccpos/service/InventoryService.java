package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.validation.form.inventory.InventoryFrom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventoryService {
    public List<Inventory> create(InventoryFrom[] inventoryFroms){
        List<Inventory> inventories = new ArrayList<>();




        return inventories;
    }
    public Inventory create(InventoryFrom inventoryFrom){
        Inventory inventory = new Inventory();



        return inventory;
    }
    public Inventory merge(Inventory inventory,InventoryFrom inventoryFrom){
        double purchasedPrice = inventory.getPurchasePrice();
        int purchasedQuantity = inventory.getPurchaseQuantity();

        double formPurchasedPrice = inventoryFrom.getPurchasePrice()==null?0:inventoryFrom.getPurchasePrice();
        int formPurchasedQuantity = inventoryFrom.getPurchaseQuantity()==null?0:inventoryFrom.getPurchaseQuantity();


        inventory.setPurchasePrice(purchasedPrice+formPurchasedPrice);
        inventory.setPurchaseQuantity(purchasedQuantity+formPurchasedQuantity);

        return inventory;
    }
    private Inventory fetchByProductId(int productId, PRODUCT_CONDITION productCondition, List<Inventory> inventories){
        Optional<Inventory> optionalInventory = inventories.stream().filter(inventory -> {
           Product product =  inventory.getProduct();
           if(product==null)return false;

           return ( product.getId() == productId);
        }).findFirst();

        return (optionalInventory.isPresent())?optionalInventory.get():null;
    }
}