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
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;
import com.workspaceit.dccpos.validation.form.purchase.PurchasePaymentForm;
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
    public Entry createShipmentEntry(Shipment shipment, PurchaseForm purchaseForm){
        List<EntryItem> entryItems = new ArrayList<>();
        PurchasePaymentForm paymentForm = purchaseForm.getPayment();

        double totalShippingCost = shipment.getTotalCost();
        double totalInventoryPrice = shipment.getTotalProductPrice();


        double paidShippingCostAmount = paymentForm.getPaidCostAmount();
        double paidProductPrice = paymentForm.getPaidProductPriceAmount();

        double totalEntryDcAmount = totalShippingCost+totalInventoryPrice;

        Supplier supplier = shipment.getSupplier();

        Ledger  supplierLedger = this.ledgerService.getByCompanyIdAndCode(supplier.getCompany().getId(), GROUP_CODE.SUPPLIER);

        Entry entry = new Entry();
        entry.setDrTotal(totalEntryDcAmount);
        entry.setCrTotal(totalEntryDcAmount);
        entry.setEntryType(null);
        entry.setCreatedBy(shipment.getPurchasedBy());
        entry.setEntryItems(entryItems);
        entry.setDate(shipment.getPurchasedDate());
        entry.setNarration("Purchased  product");

        /**
         * Shipping Cost
         *  - Full due
         *  - Full paid
         *  - Partially paid
         * */
        EntryItem entryItemShipmentCost = this.getShipmentEntryItem(totalShippingCost,LEDGER_CODE.SHIPMENT_COST,ACCOUNTING_ENTRY.DR);
        EntryItem entryItemCash = this.getShipmentEntryItem(0d,LEDGER_CODE.CASH,ACCOUNTING_ENTRY.CR);

        entryItems.add(entryItemShipmentCost);

        if(paidShippingCostAmount==0){
            EntryItem entryItemDueShipmentCost = this.getShipmentEntryItem(totalShippingCost,LEDGER_CODE.DUE_SHIPMENT_COST,ACCOUNTING_ENTRY.CR);
            entryItems.add(entryItemDueShipmentCost);
        }else if(totalShippingCost==paidShippingCostAmount){
            entryItemCash.setAmount(totalShippingCost);
            entryItems.add(entryItemCash);
        }else if(totalShippingCost>paidShippingCostAmount){
            EntryItem entryItemDueShipmentCost = this.getShipmentEntryItem(totalShippingCost-paidShippingCostAmount,LEDGER_CODE.DUE_SHIPMENT_COST,ACCOUNTING_ENTRY.CR);
            entryItemCash.setAmount(paidShippingCostAmount);

            entryItems.add(entryItemDueShipmentCost);
            entryItems.add(entryItemCash);
        }



        EntryItem entryItemInventory = this.getShipmentEntryItem(totalInventoryPrice,LEDGER_CODE.INVENTORY,ACCOUNTING_ENTRY.DR);
        entryItems.add(entryItemInventory);


        double cashAmount = entryItemCash.getAmount();

        /**
         * Product price
         *  - Full due
         *  - Full paid
         *  - Partially paid
         * */
        if(paidProductPrice == 0){
            EntryItem entryItemSupplier = this.getSupplierEntryItem(totalInventoryPrice,supplierLedger,ACCOUNTING_ENTRY.CR);

            entryItems.add(entryItemSupplier);
        }else if(totalInventoryPrice == paidProductPrice){
            entryItemCash.setAmount(cashAmount+totalInventoryPrice);

            if(!entryItems.contains(entryItemCash))entryItems.add(entryItemCash);

        }else if(totalInventoryPrice > paidProductPrice){
            double priceBalance = totalInventoryPrice-paidProductPrice;

            EntryItem entryItemSupplier = this.getSupplierEntryItem(priceBalance,supplierLedger,ACCOUNTING_ENTRY.CR);
            entryItemCash.setAmount(cashAmount+paidProductPrice);

            entryItems.add(entryItemSupplier);
            if(!entryItems.contains(entryItemCash))entryItems.add(entryItemCash);
        }

        this.entryDao.save(entry);


        return entry;

    }
    @Transactional(rollbackFor = Exception.class)
    public Entry createShipmentEntryOnCash(Shipment shipment){
        List<EntryItem> entryItems = new ArrayList<>();

        double totalCost = shipment.getTotalCost();
        double totalInventoryPrice = shipment.getTotalProductPrice();

        double totalEntryDcAmount = totalCost+totalInventoryPrice;


        Entry entry = new Entry();
        entry.setDrTotal(totalEntryDcAmount);
        entry.setCrTotal(totalEntryDcAmount);
        entry.setEntryType(null);
        entry.setCreatedBy(shipment.getPurchasedBy());
        entry.setEntryItems(entryItems);
        entry.setDate(shipment.getPurchasedDate());
        entry.setNarration("Purchased  product");


        EntryItem entryItemInventory = this.getShipmentEntryItem(totalInventoryPrice,LEDGER_CODE.INVENTORY,ACCOUNTING_ENTRY.DR);
        EntryItem entryItemShipmentCost = this.getShipmentEntryItem(totalCost,LEDGER_CODE.SHIPMENT_COST,ACCOUNTING_ENTRY.DR);
        EntryItem entryItemCash = this.getShipmentEntryItem(totalInventoryPrice+totalInventoryPrice,LEDGER_CODE.CASH,ACCOUNTING_ENTRY.CR);



        entryItems.add(entryItemInventory);
        entryItems.add(entryItemShipmentCost);

        entryItems.add(entryItemCash);
        this.entryDao.save(entry);


        return entry;

    }
    private EntryItem getShipmentEntryItem(double amount ,LEDGER_CODE ledgerCode,ACCOUNTING_ENTRY accountingEntry){
        Ledger ledger = this.ledgerService.getByCode(ledgerCode);

        EntryItem entryItem = new EntryItem();

        entryItem.setLedger(ledger);
        entryItem.setAccountingEntry(accountingEntry);
        entryItem.setAmount(amount);

        return entryItem;
    }
    private EntryItem getSupplierEntryItem(double amount ,Ledger ledger,ACCOUNTING_ENTRY accountingEntry){


        EntryItem entryItem = new EntryItem();

        entryItem.setLedger(ledger);
        entryItem.setAccountingEntry(accountingEntry);
        entryItem.setAmount(amount);

        return entryItem;
    }
    private void save(Entry entry){
        this.entryDao.save(entry);
    }
}