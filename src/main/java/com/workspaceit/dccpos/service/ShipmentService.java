package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.INVENTORY_ATTRS;
import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import com.workspaceit.dccpos.dao.ShipmentDao;
import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.TrackingIdGenerator;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentCreateForm;
import com.workspaceit.dccpos.validation.form.shipment.ShipmentSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ShipmentService {
    private ShipmentDao shipmentDao;

    private SupplierService supplierService;
    private InventoryService inventoryService;
    private ShipmentCostService shipmentCostService;

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

    @Autowired
    public void setShipmentCostService(ShipmentCostService shipmentCostService) {
        this.shipmentCostService = shipmentCostService;
    }

    @Transactional
    public Shipment getByShipment(int id) throws EntityNotFound {
        Shipment shipment =  this.shipmentDao.findById(id);

        if(shipment==null)throw new EntityNotFound("Entity not found by id: "+id);
        return shipment;
    }

    @Transactional
    public long getMaxId(){
        return this.shipmentDao.findMaxId(Shipment.class);
    }
    @Transactional
    public long getMinId(){
        return this.shipmentDao.findMinId(Shipment.class);
    }
    @Transactional
    public List<Shipment> getAll(int limit, int offset){
        offset = (offset-1)*limit;
        return this.shipmentDao.findAll(limit,offset);
    }
    @Transactional
    public long getCountOfAll(){
        return this.shipmentDao.findAllRowCount(Shipment.class);
    }

    @Transactional
    public List<Shipment> getAll(int limit, int offset, ShipmentSearchForm shipmentSearchForm){
        offset = (offset-1)*limit;
        return this.shipmentDao.findAll(limit,offset,shipmentSearchForm);
    }
    @Transactional
    public long getCountOfAll( ShipmentSearchForm shipmentSearchForm){
        return this.shipmentDao.findCountOfAll(shipmentSearchForm);
    }

    @Transactional
    public List<Shipment> getAllByDate( int limit, int offset,Date date){
        offset = (offset-1)*limit;
        return this.shipmentDao.findAllByDate(limit,offset,date);
    }

    @Transactional
    public List<Shipment> getAllByDate( int limit, int offset,Date fromDate,Date toDate){
        offset = (offset-1)*limit;
        return this.shipmentDao.findAllByDate(limit,offset,fromDate,toDate);
    }
    @Transactional
    public long getCountOfAllByDate(Date date){
        return this.shipmentDao.findCountOfAllByDate(date);
    }
    @Transactional
    public long getCountOfAllByDate(Date fromDate,Date toDate){
        return this.shipmentDao.findCountOfAllByDate(fromDate,toDate);
    }
    @Transactional
    public Shipment getById(long id){
        return this.shipmentDao.findById(id);
    }
    @Transactional
    public Shipment getByTrackingId(String trackingId){
        return this.shipmentDao.findByTrackingId(trackingId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Shipment create(Employee employee, PurchaseForm purchaseForm)throws EntityNotFound{
        ShipmentCreateForm shipmentCreateForm = purchaseForm.getShipment();
        Supplier supplier =  this.supplierService.getSupplier(shipmentCreateForm.getSupplierId());

        double carryingCost = shipmentCreateForm.getCarryingCost()==null?0:shipmentCreateForm.getCarryingCost();
        double cfCost = shipmentCreateForm.getCfCost()==null?0:shipmentCreateForm.getCfCost();
        double laborCost = shipmentCreateForm.getLaborCost()==null?0:shipmentCreateForm.getLaborCost();
        double otherCost = shipmentCreateForm.getOtherCost()==null?0:shipmentCreateForm.getOtherCost();
        double totalCost = this.getTotalCost(shipmentCreateForm);
        double totalPaid = 0;

        Map<SHIPMENT_COST,ShipmentCost> shipmentCosts = new HashMap<>();


        shipmentCosts.put(SHIPMENT_COST.CARRYING,new ShipmentCost(SHIPMENT_COST.CARRYING,carryingCost));
        shipmentCosts.put(SHIPMENT_COST.CF,new ShipmentCost(SHIPMENT_COST.CF,cfCost));
        shipmentCosts.put(SHIPMENT_COST.LABOR,new ShipmentCost(SHIPMENT_COST.LABOR,laborCost));
        shipmentCosts.put(SHIPMENT_COST.OTHERS,new ShipmentCost(SHIPMENT_COST.OTHERS,otherCost));

        /**
         * Create Inventory
         * */
        List<Inventory> inventories = this.inventoryService.create(purchaseForm.getInventories());
        this.shipmentCostService.create(null,shipmentCosts);
        Map<INVENTORY_ATTRS,Double> priceCostMap = this.inventoryService.getTotalProductPriceAndQuantity(inventories);


        Shipment shipment = new Shipment();

        shipment.setSupplier(supplier);

        shipment.setInventories(inventories);

        shipment.setTotalProductPrice(priceCostMap.get(INVENTORY_ATTRS.TOTAL_PRICE));
        shipment.setTotalQuantity(priceCostMap.get(INVENTORY_ATTRS.TOTAL_QUANTITY).intValue());
        shipment.setTotalCost(totalCost);
        shipment.setTotalPaid(totalPaid);

        shipment.setPurchasedDate(shipmentCreateForm.getPurchaseDate());
        shipment.setPurchasedBy(employee);

        this.save(shipment);




        shipment.setCosts(shipmentCosts);

        String trackingId = TrackingIdGenerator.getShipmentTrackingId(shipment.getId());

        shipment.setTrackingId(trackingId);

        this.update(shipment);


        shipment = this.getById(shipment.getId());

        return shipment;
    }

    public double getTotalCost( Map<SHIPMENT_COST,ShipmentCost> costs){
        double totalCost = 0;

        Set<SHIPMENT_COST> keySet =  costs.keySet();
        for(SHIPMENT_COST key :keySet){
            System.out.println(costs.get(key).getId()+" "+  totalCost+" " +costs.get(key).getAmount());
            totalCost += costs.get(key).getAmount();
        }


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