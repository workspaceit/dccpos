package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
import com.workspaceit.dccpos.dao.accounting.EntryDao;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.service.ShipmentService;
import com.workspaceit.dccpos.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EntryService {
    private EntryDao entryDao;
    private EntryItemService entryItemService;
    private ShipmentService shipmentService;
    private LedgerService ledgerService;
    private SupplierService supplierService;

    @Autowired
    public void setEntryDao(EntryDao entryDao) {
        this.entryDao = entryDao;
    }

    @Autowired
    public void setEntryItemService(EntryItemService entryItemService) {
        this.entryItemService = entryItemService;
    }

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }
    @Transactional
    public List<Entry> getByDate(Date startDate, Date endDate){
        return this.entryDao.findByDate(null,null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Entry createShipmentEntryOnDue(Shipment shipment){
        List<EntryItem> entryItems = new ArrayList<>();

        double totalCost = shipment.getTotalCost();
        double totalInventoryPrice = shipment.getTotalProductPrice();

        double totalEntryDcAmount = totalCost+totalInventoryPrice;

        Supplier supplier = shipment.getSupplier();
        Ledger  supplierLedger = this.ledgerService.getByCompanyIdAndCode(supplier.getCompany().getId(), GROUP_CODE.SUPPLIER);
        Ledger inventoryLedger = this.ledgerService.getByCode(LEDGER_CODE.INVENTORY);
        Ledger cashLedger = this.ledgerService.getByCode(LEDGER_CODE.CASH);
        Ledger shipmentCostLedger = this.ledgerService.getByCode(LEDGER_CODE.SHIPMENT_COST);

        Entry entry = new Entry();
        entry.setDrTotal(totalEntryDcAmount);
        entry.setCrTotal(totalEntryDcAmount);
        entry.setEntryType(null);
        entry.setCreatedBy(shipment.getPurchasedBy());
        entry.setEntryItems(entryItems);
        entry.setDate(shipment.getPurchasedDate());
        entry.setNarration("Purchased  product");


        EntryItem entryItemInventory = new EntryItem();

        entryItemInventory.setEntry(entry);
        entryItemInventory.setLedger(inventoryLedger);
        entryItemInventory.setAccountingEntry(ACCOUNTING_ENTRY.DR);
        entryItemInventory.setAmount(totalInventoryPrice);

        EntryItem entryItemSupplier = new EntryItem();

        entryItemInventory.setEntry(entry);
        entryItemSupplier.setLedger(supplierLedger);
        entryItemSupplier.setAccountingEntry(ACCOUNTING_ENTRY.CR);
        entryItemSupplier.setAmount(totalInventoryPrice);


        EntryItem entryItemShipmentCost = new EntryItem();

        entryItemInventory.setEntry(entry);
        entryItemShipmentCost.setLedger(shipmentCostLedger);
        entryItemShipmentCost.setAccountingEntry(ACCOUNTING_ENTRY.DR);
        entryItemShipmentCost.setAmount(totalInventoryPrice);

        EntryItem entryItemCash = new EntryItem();

        entryItemInventory.setEntry(entry);
        entryItemCash.setLedger(cashLedger);
        entryItemCash.setAccountingEntry(ACCOUNTING_ENTRY.CR);
        entryItemCash.setAmount(totalInventoryPrice);


        entryItems.add(entryItemInventory);
        entryItems.add(entryItemSupplier);

        entryItems.add(entryItemShipmentCost);
        entryItems.add(entryItemCash);
        this.entryDao.save(entry);
        //this.entryItemService.create(entryItems);

        return entry;

    }
    private void save(Entry entry){
        this.entryDao.save(entry);
    }
}