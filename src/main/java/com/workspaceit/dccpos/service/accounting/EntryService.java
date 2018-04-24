package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.ENTRY_TYPES;
import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
import com.workspaceit.dccpos.dao.accounting.EntryDao;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.entity.Supplier;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.entity.accounting.EntryType;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.AccountPaymentFormHelper;
import com.workspaceit.dccpos.validation.form.purchase.AccountPaymentForm;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntryService {
    private EntryDao entryDao;
    private LedgerService ledgerService;
    private EntryTypeService entryTypeService;


    @Autowired
    public void setEntryDao(EntryDao entryDao) {
        this.entryDao = entryDao;
    }
    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }
    @Autowired
    public void setEntryTypeService(EntryTypeService entryTypeService) {
        this.entryTypeService = entryTypeService;
    }

    @Transactional
    public List<Entry> getByDate(Date startDate, Date endDate){
        return this.entryDao.findByDate(null,null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Entry createShipmentEntry(Shipment shipment, PurchaseForm purchaseForm) throws EntityNotFound {
        List<EntryItem> entryItems = new ArrayList<>();
        EntryType entryType = this.entryTypeService.getByLabel(ENTRY_TYPES.JOURNAL);
        AccountPaymentForm[]  productPricePaymentAccounts =  purchaseForm.getProductPricePaymentAccount();
        AccountPaymentForm  shippingCostPaymentAccount =  purchaseForm.getShippingCostPaymentAccount();

        double totalShippingCost = shipment.getTotalCost();
        double totalInventoryPrice = shipment.getTotalProductPrice();

        double paidProductPrice = AccountPaymentFormHelper.getTotalPaidAmount(productPricePaymentAccounts);
        double paidShippingCostAmount =(shippingCostPaymentAccount!=null)? shippingCostPaymentAccount.getAmount():0;
        double totalEntryDcAmount = totalShippingCost+totalInventoryPrice;

        Supplier supplier = shipment.getSupplier();

        Ledger  supplierLedger = this.ledgerService.getByCompanyIdAndCode(supplier.getCompany().getId(), GROUP_CODE.SUPPLIER);

        Entry entry = new Entry();
        entry.setDrTotal(totalEntryDcAmount);
        entry.setCrTotal(totalEntryDcAmount);
        entry.setEntryType(entryType);
        entry.setCreatedBy(shipment.getPurchasedBy());
        entry.setEntryItems(entryItems);
        entry.setDate(shipment.getPurchasedDate());
        entry.setNarration("Purchased  product");



        /**
         * Shipping Cost
         *  - Full due
         *  - Full paid
         *  - Partially paid
         *
         *  Shipping Cost Dr
         *  CashOrBanck Dr
         * if Due
         *  Due Shipping Cost Cr
         * */
        EntryItem entryItemShipmentCost = this.getShipmentEntryItem(totalShippingCost,LEDGER_CODE.SHIPMENT_COST,ACCOUNTING_ENTRY.DR);
        double paidShippingCostBalance = 0;

        entryItems.add(entryItemShipmentCost);

        if(shippingCostPaymentAccount!=null){
            int ledgerId = shippingCostPaymentAccount.getLedgerId();

            Ledger cashOrBankLedger = this.ledgerService.getLedger(ledgerId);

            EntryItem entryShippingCostCashOrBank = this.getEntryItem(shippingCostPaymentAccount.getAmount(),cashOrBankLedger,ACCOUNTING_ENTRY.CR);
            entryItems.add(entryShippingCostCashOrBank);
        }


        if(paidShippingCostAmount==0){
            paidShippingCostBalance = totalShippingCost;
        }else if(totalShippingCost>paidShippingCostAmount){
            paidShippingCostBalance = totalShippingCost - paidShippingCostAmount;
        }


        if(paidShippingCostBalance>0){
            EntryItem entryItemDueShipmentCost = this.getShipmentEntryItem(paidShippingCostBalance,LEDGER_CODE.DUE_SHIPMENT_COST,ACCOUNTING_ENTRY.CR);
            entryItems.add(entryItemDueShipmentCost);
        }


        /**
         * Product price
         *  - Full due
         *  - Full paid
         *  - Partially paid
         * -----------------------
         *  Inventory ... Dr
         *  CashOrBanck 1 ... Cr
         *      .
         *      .
         *      .
         *  CashOrBanck n ... Cr
         *
         * if Due
         *  Supplier ... Cr
         * */


        EntryItem entryItemInventory = this.getShipmentEntryItem(totalInventoryPrice,LEDGER_CODE.INVENTORY,ACCOUNTING_ENTRY.DR);
        double priceBalance = 0;

        entryItems.add(entryItemInventory);

        if(productPricePaymentAccounts!=null){
            for(AccountPaymentForm accountPaymentForm  :productPricePaymentAccounts){
                int ledgerId = accountPaymentForm.getLedgerId();

                Ledger cashOrBankLedger = this.ledgerService.getLedger(ledgerId);
                EntryItem entryItemCashOrBank = this.getEntryItem(entryItems,accountPaymentForm.getAmount(),cashOrBankLedger,ACCOUNTING_ENTRY.CR);

                if(!entryItems.contains(entryItemCashOrBank))entryItems.add(entryItemCashOrBank);
            }
        }



        if(paidProductPrice == 0){
            priceBalance = totalInventoryPrice;
        }else if(totalInventoryPrice > paidProductPrice){
            priceBalance = totalInventoryPrice-paidProductPrice;
        }

        if(priceBalance>0){
            EntryItem entryItemSupplier = this.getEntryItem(priceBalance,supplierLedger,ACCOUNTING_ENTRY.CR);
            entryItems.add(entryItemSupplier);
        }


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
    private EntryItem getEntryItem(double amount , Ledger ledger, ACCOUNTING_ENTRY accountingEntry){


        EntryItem entryItem = new EntryItem();

        entryItem.setLedger(ledger);
        entryItem.setAccountingEntry(accountingEntry);
        entryItem.setAmount(amount);

        return entryItem;
    }
    private EntryItem getEntryItem(List<EntryItem> entryItems,double amount , Ledger ledger, ACCOUNTING_ENTRY accountingEntry){

       Optional<EntryItem> entryOptional =  entryItems.stream().filter(entryItem -> {
                return entryItem.getLedger().getId()==ledger.getId()
                        && entryItem.getAccountingEntry().equals(accountingEntry);
        }).findFirst();

        EntryItem entryItem =(entryOptional.isPresent())?entryOptional.get():new EntryItem();

        entryItem.setLedger(ledger);
        entryItem.setAccountingEntry(accountingEntry);

        if(entryOptional.isPresent()){
            amount +=  entryItem.getAmount();
        }

        entryItem.setAmount(amount);

        return entryItem;
    }
    private void save(Entry entry){
        this.entryDao.save(entry);
    }
}