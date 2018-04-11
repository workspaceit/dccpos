package com.workspaceit.pos.service.accounting;

import com.workspaceit.pos.dao.accounting.LedgerDao;
import com.workspaceit.pos.entity.accounting.Ledger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LedgerService {
    private LedgerDao ledgerDao;

    @Autowired
    public void setLedgerDao(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }

    @Transactional
    public List<Ledger> getAll(){
        return this.ledgerDao.getAll();
    }

}