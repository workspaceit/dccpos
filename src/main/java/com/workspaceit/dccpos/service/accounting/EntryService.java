package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.ENTRY_TYPES;
import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
import com.workspaceit.dccpos.dao.accounting.EntryDao;
import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.entity.accounting.EntryType;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.AccountPaymentFormHelper;
import com.workspaceit.dccpos.util.SaleDetailsUtil;
import com.workspaceit.dccpos.util.validation.PaymentLedgerFormUtil;
import com.workspaceit.dccpos.util.validation.SaleFormUtil;
import com.workspaceit.dccpos.validation.form.accounting.InvestmentForm;
import com.workspaceit.dccpos.validation.form.accounting.PaymentLedgerForm;
import com.workspaceit.dccpos.validation.form.accounting.TransactionForm;
import com.workspaceit.dccpos.validation.form.purchase.PurchaseForm;;
import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EntryService {
    private EntryDao entryDao;
    private LedgerService ledgerService;
    private EntryTypeService entryTypeService;
    private EntryItemService entryItemService;
    private SaleDetailsUtil saleDetailsUtil;
    private SaleFormUtil saleFormUtil;
    private PaymentLedgerFormUtil paymentLedgerFormUtil;

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
    @Autowired
    public void setEntryItemService(EntryItemService entryItemService) {
        this.entryItemService = entryItemService;
    }
    @Autowired
    public void setSaleDetailsUtil(SaleDetailsUtil saleDetailsUtil) {
        this.saleDetailsUtil = saleDetailsUtil;
    }
    @Autowired
    public void setSaleFormUtil(SaleFormUtil saleFormUtil) {
        this.saleFormUtil = saleFormUtil;
    }
    @Autowired
    public void setPaymentLedgerFormUtil(PaymentLedgerFormUtil paymentLedgerFormUtil) {
        this.paymentLedgerFormUtil = paymentLedgerFormUtil;
    }

    @Transactional
    public List<Entry> getByDate(Date startDate, Date endDate){
        return this.entryDao.findByDate(null,null);
    }
    @Transactional
    public List<Entry> getAll(){
        return this.entryDao.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Entry decreaseInventory(Sale sale){
        Set<SaleDetails> saleDetails= sale.getSaleDetails();
        Date date = sale.getDate();
        String narration = "Inventory Sold";
        Employee createdBy = sale.getSoldBy();
        List<EntryItem> entryItems = new ArrayList<>();
        double amount = this.saleDetailsUtil.getTotalPurchasePrice(saleDetails);

        EntryType entryType = this.entryTypeService.getByLabel(ENTRY_TYPES.JOURNAL);
        EntryItem entryItemCogs = this.getEntryItem(amount,LEDGER_CODE.COGS,ACCOUNTING_ENTRY.DR);
        EntryItem entryItemInventory = this.getEntryItem(amount,LEDGER_CODE.INVENTORY,ACCOUNTING_ENTRY.CR);


        entryItems.add(entryItemCogs);
        entryItems.add(entryItemInventory);

        Entry entry = new Entry();

        entry.setDrTotal(amount);
        entry.setCrTotal(amount);
        entry.setEntryType(entryType);
        entry.setCreatedBy(createdBy);
        entry.setDate(date);
        entry.setNarration(narration);



        this.save(entry);

        this.entryItemService.saveAll(entry,entryItems);
        entry.setEntryItems(entryItems);

        this.ledgerService.resolveCurrentBalance(entryItems);
        return entry;
    }

    @Transactional(rollbackFor = Exception.class)
    public Entry createSaleEntry(Sale sale, SaleForm saleForm) throws EntityNotFound {
        Entry entry = new Entry();
        double totalAmount =  this.saleFormUtil.getTotalPayablePrice(saleForm);
        List<EntryItem> entryItems = new ArrayList<>();

        EntryType entryType = this.entryTypeService.getByLabel(ENTRY_TYPES.JOURNAL);
        EntryItem entryItemSale = this.getEntryItem(totalAmount,LEDGER_CODE.SALE,ACCOUNTING_ENTRY.CR);
        entryItems.add(entryItemSale);

        double totalReceive = this.paymentLedgerFormUtil.sumAmount(saleForm.getPaymentAccount());

        /**
         * Payment entry items
         * */
        if(totalReceive>0){
            PaymentLedgerForm[]  ledgerEntryForms =  saleForm.getPaymentAccount();

            for(PaymentLedgerForm ledgerEntryForm:ledgerEntryForms){

                Ledger ledger = this.ledgerService.getLedger(ledgerEntryForm.getLedgerId());
                EntryItem entryItemPayment = this.getEntryItem(ledgerEntryForm.getAmount(),ledger,ACCOUNTING_ENTRY.DR);

                entryItems.add(entryItemPayment);
            }
        }

        if(totalReceive!=totalAmount){
            double dueAmount = totalAmount - totalReceive;
            switch (saleForm.getType()){
                case CONSUMER_SALE:
                    EntryItem entryItemDueSale = this.getEntryItem(dueAmount,LEDGER_CODE.DUE_SALE,ACCOUNTING_ENTRY.DR);
                    entryItems.add(entryItemDueSale);
                    break;
                case WHOLESALE:
                    Ledger wholeSellerLedger = this.ledgerService.getByCompanyId(saleForm.getWholesalerId());
                    EntryItem entryItemWholeSeller = this.getEntryItem(dueAmount,wholeSellerLedger,ACCOUNTING_ENTRY.DR);
                    entryItems.add(entryItemWholeSeller);
                    break;
            }
        }

        entry.setDrTotal(totalAmount);
        entry.setCrTotal(totalAmount);
        entry.setEntryType(entryType);
        entry.setCreatedBy(sale.getSoldBy());
        entry.setDate(sale.getDate());
        entry.setNarration(sale.getDescription());

        this.save(entry);

        this.entryItemService.saveAll(entry,entryItems);
        entry.setEntryItems(entryItems);

        this.decreaseInventory(sale);

        return entry;
    }

    @Transactional(rollbackFor = Exception.class)
    public Entry createShipmentEntry(Shipment shipment, PurchaseForm purchaseForm) throws EntityNotFound {
        List<EntryItem> entryItems = new ArrayList<>();
        EntryType entryType = this.entryTypeService.getByLabel(ENTRY_TYPES.JOURNAL);
        PaymentLedgerForm[]  productPricePaymentAccounts =  purchaseForm.getProductPricePaymentAccount();
        PaymentLedgerForm shippingCostPaymentAccount =  purchaseForm.getShippingCostPaymentAccount();

        double totalShippingCost = shipment.getTotalCost();
        double totalInventoryPrice = shipment.getTotalProductPrice();

        double paidProductPrice = AccountPaymentFormHelper.getTotalPaidAmount(productPricePaymentAccounts);
        double paidShippingCostAmount =(shippingCostPaymentAccount!=null)? shippingCostPaymentAccount.getAmount():0;
        double totalEntryDcAmount = totalShippingCost+totalInventoryPrice;

        Supplier supplier = shipment.getSupplier();

        Ledger  supplierLedger = this.ledgerService.getByCompanyId(supplier.getCompany().getId());

        Entry entry = new Entry();
        entry.setDrTotal(totalEntryDcAmount);
        entry.setCrTotal(totalEntryDcAmount);
        entry.setEntryType(entryType);
        entry.setCreatedBy(shipment.getPurchasedBy());
        entry.setDate(shipment.getPurchasedDate());
        entry.setNarration("Purchased  product");
        this.save(entry);


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
        EntryItem entryItemShipmentCost = this.getEntryItem(totalShippingCost,LEDGER_CODE.SHIPMENT_COST,ACCOUNTING_ENTRY.DR);
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
            EntryItem entryItemDueShipmentCost = this.getEntryItem(paidShippingCostBalance,LEDGER_CODE.DUE_SHIPMENT_COST,ACCOUNTING_ENTRY.CR);
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


        EntryItem entryItemInventory = this.getEntryItem(totalInventoryPrice,LEDGER_CODE.INVENTORY,ACCOUNTING_ENTRY.DR);
        double priceBalance = 0;

        entryItems.add(entryItemInventory);

        if(productPricePaymentAccounts!=null){
            for(PaymentLedgerForm accountPaymentForm  :productPricePaymentAccounts){
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


        this.entryItemService.saveAll(entry,entryItems);
        entry.setEntryItems(entryItems);

        return entry;

    }
    @Transactional(rollbackFor = Exception.class)
    public Entry createPaymentEntry(Employee employee, TransactionForm transactionForm) throws EntityNotFound {
        return this.createPaymentOrReceiveEntry(employee,transactionForm,ENTRY_TYPES.PAYMENT);
    }
    @Transactional(rollbackFor = Exception.class)
    public Entry createReceiptEntry(Employee employee, TransactionForm transactionForm) throws EntityNotFound {
        return this.createPaymentOrReceiveEntry(employee,transactionForm,ENTRY_TYPES.RECEIPT);
    }
    @Transactional(rollbackFor = Exception.class)
    public Entry createInvestmentEntry(Employee employee, InvestmentForm investmentForm) throws EntityNotFound {
        List<EntryItem> entryItems = new ArrayList<>();
        double totalInvestment = 0;

        PaymentLedgerForm[]  cashOrBankList = investmentForm.getCashOrBank();
        Ledger ownersLedgerLedger = this.ledgerService.getByCode(LEDGER_CODE.INVESTMENT);
        EntryType entryType = this.entryTypeService.getByLabel(ENTRY_TYPES.JOURNAL);

        /**
         * Getting all amount of cash or bank ledger
         * */
        for(PaymentLedgerForm cashOrBank : cashOrBankList){

            Ledger entryCashOrBankLedger = this.ledgerService.getLedger(cashOrBank.getLedgerId());
            EntryItem entryCashOrBank = this.getEntryItem(cashOrBank.getAmount(),entryCashOrBankLedger,ACCOUNTING_ENTRY.DR);
            entryItems.add(entryCashOrBank);

            totalInvestment +=cashOrBank.getAmount();
        }


        Entry entry = new Entry();
        entry.setDrTotal(totalInvestment);
        entry.setCrTotal(totalInvestment);
        entry.setEntryType(entryType);
        entry.setCreatedBy(employee);
        entry.setDate(investmentForm.getDate());
        entry.setNarration(investmentForm.getNarration());
        this.save(entry);

        EntryItem entryOwnerInvestment = this.getEntryItem(totalInvestment,ownersLedgerLedger,ACCOUNTING_ENTRY.CR);
        entryItems.add(entryOwnerInvestment);

        this.entryItemService.saveAll(entry,entryItems);
        entry.setEntryItems(entryItems);

        return entry;
    }
    private Entry createPaymentOrReceiveEntry(Employee employee, TransactionForm transactionForm,ENTRY_TYPES _EntryType) throws EntityNotFound {

        ACCOUNTING_ENTRY accountTypeCashBank = null;
        ACCOUNTING_ENTRY accountTypeBeneficial = null;

        switch (_EntryType){
            case PAYMENT:
                accountTypeBeneficial = ACCOUNTING_ENTRY.DR;
                accountTypeCashBank=ACCOUNTING_ENTRY.CR;
                break;
            case RECEIPT:
                accountTypeCashBank=ACCOUNTING_ENTRY.DR;
                accountTypeBeneficial = ACCOUNTING_ENTRY.CR;
                break;
        }


        List<EntryItem> entryItems = new ArrayList<>();
        EntryType entryType = this.entryTypeService.getByLabel(_EntryType);
        PaymentLedgerForm beneficialForm =  transactionForm.getBeneficial();
        PaymentLedgerForm[] cashOrBankList = transactionForm.getCashOrBank();

        double totalEntryDcAmount = beneficialForm.getAmount();


        Entry entry = new Entry();
        entry.setDrTotal(totalEntryDcAmount);
        entry.setCrTotal(totalEntryDcAmount);
        entry.setEntryType(entryType);
        entry.setCreatedBy(employee);
        entry.setDate(transactionForm.getDate());
        entry.setNarration(transactionForm.getNarration());
        this.save(entry);

        Ledger beneficialLedger = this.ledgerService.getLedger(beneficialForm.getLedgerId());

        EntryItem entryBeneficial = this.getEntryItem(beneficialForm.getAmount(),beneficialLedger,accountTypeBeneficial);
        entryItems.add(entryBeneficial);


        /**
         * Getting all amount of cash or bank ledger
         * */
        for(PaymentLedgerForm cashOrBank : cashOrBankList){

            Ledger entryCashOrBankLedger = this.ledgerService.getLedger(cashOrBank.getLedgerId());
            EntryItem entryCashOrBank = this.getEntryItem(cashOrBank.getAmount(),entryCashOrBankLedger,accountTypeCashBank);

            entryItems.add(entryCashOrBank);
        }




        this.entryItemService.saveAll(entry,entryItems);
        entry.setEntryItems(entryItems);
        return entry;

    }
    private EntryItem getEntryItem(double amount , LEDGER_CODE ledgerCode, ACCOUNTING_ENTRY accountingEntry){
        Ledger ledger = this.ledgerService.getByCode(ledgerCode);
        return this.getEntryItem(amount,ledger,accountingEntry);
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
    public Entry save(Entry entry){
        this.entryDao.save(entry);

        return entry;
    }
    public double getTotalEntryItemsAmount(Entry entry,GROUP_CODE groupCode,ACCOUNTING_ENTRY accountingEntry){
      List<EntryItem> entryItems =   entry.getEntryItems();
      if(entryItems==null || entryItems.size()==0){
          entryItems = this.entryItemService.getByEntryId(entry.getId());
      }

        return entryItems.stream().filter(
                ei->( ei.getLedger().getGroupAccount().getCode().equals(groupCode)
                        && ei.getAccountingEntry().equals(accountingEntry)
                    ) ).mapToDouble(ei->ei.getAmount()).sum();

    }
}