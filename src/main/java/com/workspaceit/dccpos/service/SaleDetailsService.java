package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.dao.SaleDetailsDao;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Sale;
import com.workspaceit.dccpos.entity.SaleDetails;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.sale.InventorySaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SaleDetailsService {
    private SaleDetailsDao saleDetailsDao;
    private InventoryService inventoryService;

    @Autowired
    public void setSaleDetailsDao( SaleDetailsDao saleDetailsDao) {
        this.saleDetailsDao = saleDetailsDao;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Set<SaleDetails> getSaleDetails(InventorySaleForm[] inventorySaleForms) throws EntityNotFound {
        Set<SaleDetails> saleDetailsList = new HashSet<>();
        for(InventorySaleForm inventorySaleForm:inventorySaleForms){
            SaleDetails saleDetails = new SaleDetails();
            Inventory inventory =  this.inventoryService.getById(inventorySaleForm.getInventoryId());
            PRODUCT_CONDITION productCondition = inventory.getCondition();
            double totalPrice = inventorySaleForm.getQuantity()*inventorySaleForm.getSellingPrice();

            saleDetails.setInventory(inventory);
            saleDetails.setProductCondition(productCondition);
            saleDetails.setQuantity(inventorySaleForm.getQuantity());
            saleDetails.setPerQuantityPrice(inventorySaleForm.getSellingPrice());
            saleDetails.setTotalPrice(totalPrice);

            saleDetailsList.add(saleDetails);
        }
        return saleDetailsList;
    }
    @Transactional(rollbackFor = Exception.class)
    public List<SaleDetails> saveAll( List<SaleDetails> saleDetailsList){
        this.saleDetailsDao.saveAll(saleDetailsList);
        return saleDetailsList;
    }
}
