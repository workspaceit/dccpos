package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.INVENTORY_STATUS;
import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.dao.InventoryDao;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.InventoryDetails;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.inventory.InventoryCreateFrom;
import com.workspaceit.dccpos.validation.form.inventory.InventoryFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventoryService {
    private InventoryDao inventoryDao;
    private ProductService productService;
    private InventoryDetailsService inventoryDetailsService;


    @Autowired
    public void setInventoryDao(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setInventoryDetailsService(InventoryDetailsService inventoryDetailsService) {
        this.inventoryDetailsService = inventoryDetailsService;
    }

    public List<Inventory> create(InventoryCreateFrom[] inventoryFroms) throws EntityNotFound {
        List<Inventory> inventories = new ArrayList<>();
        for(InventoryCreateFrom inventoryFrom:inventoryFroms){
            List<InventoryDetails>  detailsList =  this.inventoryDetailsService.create(inventoryFrom.getDetails());

            int totalQuantity = this.inventoryDetailsService.getTotalPurchasedQuantity(detailsList);

            Inventory inventory = this.getFromCreateFrom(inventoryFrom);
            inventories.add(inventory);
            inventory.setInventoryDetails(detailsList);
            inventory.setPurchaseQuantity(totalQuantity);
            inventory.setAvailableQuantity(totalQuantity);

        }


        this.save(inventories);

        return inventories;
    }
    private Inventory getFromCreateFrom(InventoryFrom inventoryFrom) throws EntityNotFound {
        Inventory inventory = new Inventory();

        Product product = this.productService.getProduct(inventoryFrom.getProductId());


        inventory.setProduct(product);
        inventory.setPurchasePrice(inventoryFrom.getPurchasePrice());
        inventory.setSoldQuantity(0);

        /**
         * Sum of Inventory details
         * */
        inventory.setPurchaseQuantity(0);
        inventory.setAvailableQuantity(0);

        inventory.setStatus(INVENTORY_STATUS.IN_STOCK);

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

    private void save(Inventory inventoryDetails){
        this.inventoryDao.save(inventoryDetails);
    }
    private void update(Inventory inventoryDetails){
        this.inventoryDao.update(inventoryDetails);
    }
    private void save(List<Inventory> inventoryDetailsList){
        this.inventoryDao.saveAll(inventoryDetailsList);
    }
    private void update(List<Inventory> inventoryDetailsList){
        this.inventoryDao.updateAll(inventoryDetailsList);
    }
}