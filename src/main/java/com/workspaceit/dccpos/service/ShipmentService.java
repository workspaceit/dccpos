package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.INVENTORY_ATTRS;
import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import com.workspaceit.dccpos.dao.ShipmentDao;
import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.IdGenerator;
import com.workspaceit.dccpos.util.purchase.ShipmentFormUtil;
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

    private ShipmentFormUtil shipmentFormUtil;

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

    @Autowired
    public void setShipmentFormUtil(ShipmentFormUtil shipmentFormUtil) {
        this.shipmentFormUtil = shipmentFormUtil;
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
    public Shipment getShipment(long id) throws EntityNotFound{
        Shipment shipment = this.getById(id);
        if(shipment==null){
            throw new EntityNotFound("Shipment not found by id :"+id);
        }
        return shipment;
    }
    @Transactional
    public Shipment getByTrackingId(String trackingId,boolean fetchLazy){
        Shipment shipment;
        if(fetchLazy)
            shipment = this.shipmentDao.findByTrackingIdFetchLazy(trackingId);
        else
            shipment = this.shipmentDao.findByTrackingId(trackingId);
        return shipment;
    }

    @Transactional(rollbackFor = Exception.class)
    public Shipment create(Employee employee, PurchaseForm purchaseForm)throws EntityNotFound{
        ShipmentCreateForm shipmentCreateForm = purchaseForm.getShipment();
        Supplier supplier =  this.supplierService.getSupplier(shipmentCreateForm.getSupplierId());

        double totalCost = this.shipmentFormUtil.getTotalCost(shipmentCreateForm);
        double totalPaid = 0;

        Map<SHIPMENT_COST,ShipmentCost> shipmentCosts = new HashMap<>();

        Map<SHIPMENT_COST,Double> shipmentCreateFormCosts = shipmentCreateForm.getCost();
        if(shipmentCreateFormCosts!=null){
            for (SHIPMENT_COST key :shipmentCreateFormCosts.keySet()){
                double cost = shipmentCreateFormCosts.get(key)!=null?shipmentCreateFormCosts.get(key):0;
                shipmentCosts.put(key,new ShipmentCost(key,cost));
            }
        }


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

        String trackingId = IdGenerator.getShipmentTrackingId(shipment.getId());

        shipment.setTrackingId(trackingId);

        this.update(shipment);


        shipment = this.getById(shipment.getId());

        return shipment;
    }

    public double getTotalCost( Map<SHIPMENT_COST,ShipmentCost> costs){
        double totalCost = 0;

        Set<SHIPMENT_COST> keySet =  costs.keySet();
        for(SHIPMENT_COST key :keySet){
            totalCost += costs.get(key).getAmount();
        }


        return totalCost;
    }
    public void update(Shipment shipment){
        this.shipmentDao.update(shipment);
    }

    private void save(Shipment shipment){
        this.shipmentDao.save(shipment);
    }
}