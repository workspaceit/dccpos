package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.ENTRY_TYPES;
import com.workspaceit.dccpos.dao.accounting.EntryTypeDao;
import com.workspaceit.dccpos.entity.accounting.EntryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EntryTypeService {
    private EntryTypeDao entryTypeDao;

    @Autowired
    public void setEntryTypeDao(EntryTypeDao entryTypeDao) {
        this.entryTypeDao = entryTypeDao;
    }

    @Transactional
    public EntryType getByLabel(ENTRY_TYPES entryType){
        return this.entryTypeDao.findByLabel(entryType);
    }
}