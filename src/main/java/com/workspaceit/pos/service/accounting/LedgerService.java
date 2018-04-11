package com.workspaceit.pos.service.accounting;

import com.workspaceit.pos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.pos.constant.accounting.GROUP_CODE;
import com.workspaceit.pos.constant.accounting.LEDGER_TYPE;
import com.workspaceit.pos.dao.accounting.LedgerDao;
import com.workspaceit.pos.entity.PersonalInformation;
import com.workspaceit.pos.entity.accounting.GroupAccount;
import com.workspaceit.pos.entity.accounting.Ledger;
import com.workspaceit.pos.validation.form.accounting.LedgerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LedgerService {
    private LedgerDao ledgerDao;
    private GroupAccountService groupAccountService;

    @Autowired
    public void setLedgerDao(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }

    @Autowired
    public void setGroupAccountService(GroupAccountService groupAccountService) {
        this.groupAccountService = groupAccountService;
    }

    @Transactional
    public List<Ledger> getAll(){
        return this.ledgerDao.getAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(LedgerForm ledgerForm, PersonalInformation personalInformation, GROUP_CODE groupCode){
        GroupAccount groupAccount = this.groupAccountService.getByCode(groupCode);
        LEDGER_TYPE ledgerType = ledgerForm.isBankOrCash()?LEDGER_TYPE.CASH_ACCOUNT:LEDGER_TYPE.OTHER;

        Ledger ledger = new Ledger();

        ledger.setName(ledgerForm.getLedgerName());
        ledger.setGroupAccount(groupAccount);
        ledger.setLedgerType(ledgerType);
        ledger.setOpeningBalance(0d);
        ledger.setOpeningEntryType(ledgerForm.getAccountingEntry());
        ledger.setNotes(ledgerForm.getNote());
        ledger.setPersonalInformation(personalInformation);
        ledger.setReconciliation(0);

        this.save(ledger);
    }
    @Transactional(rollbackFor = Exception.class)
    public void createWholesalerLedger(PersonalInformation personalInformation){
        GroupAccount groupAccount = this.groupAccountService.getByCode(GROUP_CODE.WHOLESALER);

        Ledger ledger = new Ledger();

        ledger.setName(personalInformation.getFullName());
        ledger.setGroupAccount(groupAccount);
        ledger.setLedgerType(LEDGER_TYPE.OTHER);
        ledger.setOpeningBalance(0d);
        ledger.setOpeningEntryType(ACCOUNTING_ENTRY.CR);
        ledger.setNotes("");
        ledger.setPersonalInformation(personalInformation);
        ledger.setReconciliation(0);

        this.save(ledger);
    }
    @Transactional(rollbackFor = Exception.class)
    public void createEmployeeSalaryLedger(PersonalInformation personalInformation){
        GroupAccount groupAccount = this.groupAccountService.getByCode(GROUP_CODE.SALARY);
        System.out.println(groupAccount.getId());
        Ledger ledger = new Ledger();

        ledger.setName(personalInformation.getFullName());
        ledger.setGroupAccount(groupAccount);
        ledger.setLedgerType(LEDGER_TYPE.OTHER);
        ledger.setOpeningBalance(0d);
        ledger.setOpeningEntryType(ACCOUNTING_ENTRY.DR);
        ledger.setNotes("");
        ledger.setPersonalInformation(personalInformation);
        ledger.setReconciliation(0);

        this.save(ledger);
        System.out.println(ledger.getId());
    }
    @Transactional(rollbackFor = Exception.class)
    public void createSupplierLedger(PersonalInformation personalInformation){
        GroupAccount groupAccount = this.groupAccountService.getByCode(GROUP_CODE.SUPPLIER);

        Ledger ledger = new Ledger();

        ledger.setName(personalInformation.getFullName());
        ledger.setGroupAccount(groupAccount);
        ledger.setLedgerType(LEDGER_TYPE.OTHER);
        ledger.setOpeningBalance(0d);
        ledger.setOpeningEntryType(ACCOUNTING_ENTRY.CR);
        ledger.setNotes("");
        ledger.setPersonalInformation(personalInformation);
        ledger.setReconciliation(0);

        this.save(ledger);
    }

    private void save(Ledger ledger){
        this.ledgerDao.save(ledger);
    }
}