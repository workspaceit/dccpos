package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.dao.EntryItemDao;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EntryItemService {
    private EntryItemDao entryItemDao;


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
}
