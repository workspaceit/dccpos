package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.accounting.EntryDao;
import com.workspaceit.pos.entity.accounting.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EntryService {
    private EntryDao entryDao;

    @Autowired
    public void setEntryDao(EntryDao entryDao) {
        this.entryDao = entryDao;
    }

    public List<Entry> getByDate(Date startDate, Date endDate){
        return this.entryDao.findByDate(null,null);
    }
}