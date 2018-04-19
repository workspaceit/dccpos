package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.ShipmentDao;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.TrackingIdGenerator;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShipmentService {
    private ShipmentDao shipmentDao;

    private SupplierService supplierService;

    @Autowired
    public void setShipmentDao(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }

    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }
    @Transactional(rollbackFor = Exception.class)
    public Shipment create(Employee employee, PurchaseForm purchaseForm)throws EntityNotFound{
        ShipmentCreateForm shipmentCreateForm = purchaseForm.getShipment();
        Supplier supplier =  this.supplierService.getSupplier(shipmentCreateForm.getSupplierId());

        double carryingCost = shipmentCreateForm.getCarryingCost()==null?0:shipmentCreateForm.getCarryingCost();
        double cfCost = shipmentCreateForm.getCfCost()==null?0:shipmentCreateForm.getCfCost();
        double laborCost = shipmentCreateForm.getLaborCost()==null?0:shipmentCreateForm.getLaborCost();

        Shipment shipment = new Shipment();

        shipment.setSupplier(supplier);
        shipment.setCarryingCost(carryingCost);
        shipment.setCfCost(cfCost);
        shipment.setLaborCost(laborCost);
        shipment.setPurchasedBy(employee);
        shipment.setPurchasedDate(shipmentCreateForm.getPurchaseDate());

        this.save(shipment);

        String trackingId = TrackingIdGenerator.getShipmentTrackingId(shipment.getId());

        shipment.setTrackingId(trackingId);

        this.update(shipment);

        return shipment;
    }
    private void save(Shipment shipment){
        this.shipmentDao.save(shipment);
    }
    private void update(Shipment shipment){
        this.shipmentDao.update(shipment);
    }
}