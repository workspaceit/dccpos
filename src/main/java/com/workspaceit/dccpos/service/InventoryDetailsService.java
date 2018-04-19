package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.InventoryDetailsDao;
import com.workspaceit.dccpos.entity.InventoryDetails;
import com.workspaceit.dccpos.validation.form.inventory.InventoryCreateFrom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryDetailsService {
    private InventoryDetailsDao inventoryDetailsDao;

    public List<InventoryDetails> create(List<InventoryCreateFrom> inventoryCreateFroms){
        List<InventoryDetails> inventoryDetailsList = new ArrayList<>();
        InventoryDetails  inventoryDetails = new InventoryDetails();



        return inventoryDetailsList;

    }

    public InventoryDetails create(InventoryCreateFrom inventoryCreateFrom){
        InventoryDetails  inventoryDetails = new InventoryDetails();



        return inventoryDetails;

    }
}