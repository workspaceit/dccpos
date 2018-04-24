package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.INVENTORY_ATTRS;
import com.workspaceit.dccpos.dao.ShipmentDao;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.TrackingIdGenerator;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import com.workspaceit.dccpos.validation.form.purchase.PurchasePaymentForm;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShipmentService {
    private ShipmentDao shipmentDao;

    private SupplierService supplierService;
    private InventoryService inventoryService;

    @Autowired
    public void setShipmentDao(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Transactional
    public Shipment getByShipment(int id) throws EntityNotFound {
        Shipment shipment =  this.shipmentDao.getById(id);

        if(shipment==null)throw new EntityNotFound("Entity not found by id: "+id);
        return shipment;
    }
    @Transactional
    public Shipment getById(int id){
        return this.shipmentDao.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Shipment create(Employee employee, PurchaseForm purchaseForm)throws EntityNotFound{
        ShipmentCreateForm shipmentCreateForm = purchaseForm.getShipment();
        Supplier supplier =  this.supplierService.getSupplier(shipmentCreateForm.getSupplierId());
        PurchasePaymentForm paymentForm = purchaseForm.getPayment();


        double carryingCost = shipmentCreateForm.getCarryingCost()==null?0:shipmentCreateForm.getCarryingCost();
        double cfCost = shipmentCreateForm.getCfCost()==null?0:shipmentCreateForm.getCfCost();
        double laborCost = shipmentCreateForm.getLaborCost()==null?0:shipmentCreateForm.getLaborCost();
        double otherCost = shipmentCreateForm.getOtherCost()==null?0:shipmentCreateForm.getOtherCost();
        double totalCost = this.getTotalCost(shipmentCreateForm);
        double totalPaid = paymentForm.getTotalPaidAmount();

        /**
         * Create Inventory
         * */
        List<Inventory> inventories = this.inventoryService.create(purchaseForm.getInventories());
        Map<INVENTORY_ATTRS,Double> priceCostMap = this.inventoryService.getTotalProductPriceAndQuantity(inventories);

        Shipment shipment = new Shipment();

        shipment.setSupplier(supplier);

        shipment.setCarryingCost(carryingCost);
        shipment.setCfCost(cfCost);
        shipment.setLaborCost(laborCost);
        shipment.setOtherCost(otherCost);

        shipment.setInventories(inventories);

        shipment.setTotalProductPrice(priceCostMap.get(INVENTORY_ATTRS.TOTAL_PRICE));
        shipment.setTotalQuantity(priceCostMap.get(INVENTORY_ATTRS.TOTAL_QUANTITY).intValue());
        shipment.setTotalCost(totalCost);
        shipment.setTotalPaid(totalPaid);

        shipment.setPurchasedDate(shipmentCreateForm.getPurchaseDate());
        shipment.setPurchasedBy(employee);

        this.save(shipment);

        String trackingId = TrackingIdGenerator.getShipmentTrackingId(shipment.getId());

        shipment.setTrackingId(trackingId);

        this.update(shipment);




        return shipment;
    }

    public double getTotalCost(Shipment shipment){
        double totalCost = 0;
        totalCost += shipment.getCarryingCost();
        totalCost += shipment.getCfCost();
        totalCost += shipment.getLaborCost();
        totalCost += shipment.getTotalProductPrice();

        return totalCost;
    }
    public void update(Shipment shipment){
        this.shipmentDao.update(shipment);
    }
    public double getTotalCost(ShipmentCreateForm shipmentCreateForm){
        double totalCost = 0;
        double carryingCost = shipmentCreateForm.getCarryingCost()==null?0:shipmentCreateForm.getCarryingCost();
        double cfCost = shipmentCreateForm.getCfCost()==null?0:shipmentCreateForm.getCfCost();
        double laborCost = shipmentCreateForm.getLaborCost()==null?0:shipmentCreateForm.getLaborCost();
        double otherCost = shipmentCreateForm.getOtherCost()==null?0:shipmentCreateForm.getOtherCost();


        totalCost += carryingCost;
        totalCost +=cfCost;
        totalCost += laborCost;
        totalCost +=otherCost;

        return totalCost;
    }
    private void save(Shipment shipment){
        this.shipmentDao.save(shipment);
    }
}