package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.INVENTORY_ATTRS;
import com.workspaceit.dccpos.constant.INVENTORY_CYCLE;
import com.workspaceit.dccpos.constant.STOCK_STATUS;
import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.dao.InventoryDao;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.entity.Sale;
import com.workspaceit.dccpos.entity.SaleDetails;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.inventory.InventoryCreateFrom;
import com.workspaceit.dccpos.validation.form.inventory.InventoryFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Transactional
    public List<Inventory> getInStockByProductId(int productId) {
        return this.inventoryDao.findByProductIdAndStockStatus(productId,STOCK_STATUS.IN_STOCK);
    }
    @Transactional
    public List<Inventory> getAll() {
        return this.inventoryDao.findAll();
    }
    @Transactional
    public Inventory getById(long id){
        return this.inventoryDao.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Inventory> create(InventoryCreateFrom[] inventoryFroms) throws EntityNotFound {
        List<Inventory> inventories = new ArrayList<>();
        for(InventoryCreateFrom inventoryFrom:inventoryFroms){

            int totalQuantity = inventoryFrom.getPurchaseQuantity();

            Inventory inventory = this.getFromCreateFrom(inventoryFrom);
            inventories.add(inventory);
            inventory.setPurchaseQuantity(totalQuantity);
            inventory.setAvailableQuantity(totalQuantity);

        }


        this.save(inventories);

        return inventories;
    }
    public Map<INVENTORY_ATTRS,Double> getTotalProductPriceAndQuantity(List<Inventory> inventories){
        Map<INVENTORY_ATTRS,Double> map = new HashMap<>();
        map.put(INVENTORY_ATTRS.TOTAL_PRICE,0d);
        map.put(INVENTORY_ATTRS.TOTAL_QUANTITY,0d);

        double totalPrice = 0;
        double totalQuantity = 0;

        if(inventories==null || inventories.size()==0)return map;

        for(Inventory inventory:inventories){
            totalPrice += inventory.getPurchasePrice()*(double)inventory.getPurchaseQuantity();
            totalQuantity +=inventory.getPurchaseQuantity();
        }

        map.put(INVENTORY_ATTRS.TOTAL_PRICE,totalPrice);
        map.put(INVENTORY_ATTRS.TOTAL_QUANTITY,totalQuantity);

        return map;

    }
    private Inventory getFromCreateFrom(InventoryFrom inventoryFrom) throws EntityNotFound {
        Inventory inventory = new Inventory();

        Product product = this.productService.getProduct(inventoryFrom.getProductId());


        inventory.setProduct(product);
        inventory.setPurchasePrice(inventoryFrom.getPurchasePrice());
        inventory.setSoldQuantity(0);
        inventory.setCondition(inventoryFrom.getStatus());
        inventory.setSellingPrice(inventoryFrom.getSellingPrice());
        /**
         * Sum of Inventory details
         * */
        inventory.setPurchaseQuantity(0);
        inventory.setAvailableQuantity(0);

        inventory.setStatus(STOCK_STATUS.IN_STOCK);
        inventory.setInventoryCycle(INVENTORY_CYCLE.FROM_SUPPLIER);
        return inventory;
    }

    private Inventory getByProductId(int productId, PRODUCT_CONDITION productCondition, List<Inventory> inventories){
        Optional<Inventory> optionalInventory = inventories.stream().filter(inventory -> {
           Product product =  inventory.getProduct();
           if(product==null)return false;

           return ( product.getId() == productId);
        }).findFirst();
        return (optionalInventory.isPresent())?optionalInventory.get():null;
    }
    public double getMinSellingPrice(List<Inventory> inventories){


        if(inventories==null)return 0d;

        OptionalDouble min = inventories.stream()
                .mapToDouble(
                        inventoryDetails -> inventoryDetails.getSellingPrice()
                ).min();

        return min.isPresent()?min.getAsDouble():0d;
    }
    public double getMaxSellingPrice(List<Inventory> inventories){

        if(inventories==null)return 0d;


        OptionalDouble max = inventories.stream()
                .mapToDouble(
                        inventoryDetails -> inventoryDetails.getSellingPrice()
                ).max();

        return max.isPresent()?max.getAsDouble():0d;

    }
    public int getAvailableQuantity(List<Inventory> inventories, PRODUCT_CONDITION productCondition){

        if(inventories==null)return 0;

        return inventories.stream().filter(
                inventory ->inventory.getCondition().equals(productCondition)
        ).mapToInt(inventory -> inventory.getAvailableQuantity()
        ).sum();
    }
    @Transactional(rollbackFor = Exception.class)
    public void decreaseAfterSale(Sale sale){
        List<Inventory> inventories  = new ArrayList<>();
       Set<SaleDetails> saleDetailsList =   sale.getSaleDetails();

       for(SaleDetails saleDetails:saleDetailsList){
           Inventory inventory  = saleDetails.getInventory();
           inventory = this.getById(inventory.getId());
           int availableQuantity;
           int purchaseQuantity = inventory.getPurchaseQuantity();
           int soldQuantity = inventory.getSoldQuantity();
           /**
            * Sold quantity increase by Sale quantity
           * */
           soldQuantity += saleDetails.getQuantity();
           availableQuantity = purchaseQuantity - soldQuantity;

           inventory.setSoldQuantity(soldQuantity);
           inventory.setAvailableQuantity(availableQuantity);

           if(availableQuantity==0){
               inventory.setStatus(STOCK_STATUS.SOLD_OUT);
           }
           inventories.add(inventory);
       }

       this.update(inventories);
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