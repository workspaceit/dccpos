package com.workspaceit.dccpos.util.purchase;

import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentForm;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class ShipmentFormUtil {
    public double getTotalCost(ShipmentForm shipmentForm){
        double totalCost = 0;
        Map<SHIPMENT_COST,Double> costDoubleMap =  shipmentForm.getCost();
        if(costDoubleMap==null)return totalCost;
        Set<SHIPMENT_COST> keys = costDoubleMap.keySet();
        for(SHIPMENT_COST key :keys){
            Double amount =  costDoubleMap.get(key);
            totalCost += amount!=null?amount:0;
        }
        return totalCost;
    }
}
