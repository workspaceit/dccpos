package com.workspaceit.dccpos.util;

import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.ShipmentCost;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class ShipmentUtil {

    public double getDue(Shipment shipment){
        double totalPrice =  this.getTotalPrice(shipment);
        double totalPaid = shipment.getTotalPaid();
        double due = totalPrice - totalPaid;
        return due;
    }
    public double getTotalPrice(Shipment shipment){
        double totalProductPrice =  shipment.getTotalProductPrice();
        double totalCost =  this.getTotalCost(shipment);
        return totalProductPrice+totalCost;
    }
    public double getTotalCost(Shipment shipment){
        double totalCost = 0;
        Map<SHIPMENT_COST,ShipmentCost> shipmentCosts = shipment.getCosts();

        if(shipmentCosts==null)return totalCost;

        Set<SHIPMENT_COST> keySet = shipmentCosts.keySet();


        for(SHIPMENT_COST key :keySet){
            ShipmentCost shipmentCost = shipmentCosts.get(key);
            totalCost += shipmentCost.getAmount();
        }

        return totalCost;
    }
}
