package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import com.workspaceit.dccpos.dao.ShipmentCostDao;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.ShipmentCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShipmentCostService {
    private ShipmentCostDao shipmentCostDao;

    @Autowired
    public void setShipmentCostDao(ShipmentCostDao shipmentCostDao) {
        this.shipmentCostDao = shipmentCostDao;
    }

    public void create(Shipment shipment,  Map<SHIPMENT_COST,ShipmentCost> shipmentCostMap){

        List<ShipmentCost> shipmentCosts = new ArrayList<>(shipmentCostMap.values());

        shipmentCosts.stream().forEach(sc->sc.setShipment(shipment));
        this.shipmentCostDao.saveAll(shipmentCosts);
    }
    public void create(Map<SHIPMENT_COST,ShipmentCost> shipmentCostMap){

        this.shipmentCostDao.saveAll(shipmentCostMap.values());
    }
}