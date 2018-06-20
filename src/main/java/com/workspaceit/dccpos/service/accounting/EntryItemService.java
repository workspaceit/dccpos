package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.dao.EntryItemDao;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EntryItemService {
    private LedgerService ledgerService;
    private EntryItemDao entryItemDao;

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Autowired
    public void setEntryItemDao(EntryItemDao entryItemDao) {
        this.entryItemDao = entryItemDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(List<EntryItem> entries){
        this.save(entries);
    }
    private void save( List<EntryItem> entries){
        this.entryItemDao.saveAll(entries);
    }

    @Transactional
    public double getSum(int ledgerId, ACCOUNTING_ENTRY accountingEntry){
        return entryItemDao.findSum(ledgerId, accountingEntry);
    }
    @Transactional
    public double getSum(int ledgerId,ACCOUNTING_ENTRY accountingEntry, Date start, Date finish ){
        return entryItemDao.findSum(ledgerId, accountingEntry,start,finish);
    }

    @Transactional
    public List<EntryItem> getByEntryId(int entryId){
        return entryItemDao.findByEntryId(entryId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(EntryItem  entryItem){
        this.entryItemDao.save(entryItem);
    }
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(Entry entry,List<EntryItem>  entryItems){
        entryItems.stream().forEach(entryItem -> {
            entryItem.setEntry(entry);
        });

        this.entryItemDao.saveAll(entryItems);
    }
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<EntryItem>  entryItems){
        this.entryItemDao.saveAll(entryItems);
    }
}
