package com.workspaceit.pos.service.accounting;

import com.workspaceit.pos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.pos.constant.accounting.GROUP_CODE;
import com.workspaceit.pos.constant.accounting.LEDGER_TYPE;
import com.workspaceit.pos.dao.accounting.LedgerDao;
import com.workspaceit.pos.entity.Company;
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
        return this.ledgerDao.findAll();
    }
    @Transactional
    public Ledger getPersonalInfoIdAndCode(int id, GROUP_CODE groupCode){
        return this.ledgerDao.findByPersonalInfoIdAndCode(id,groupCode);
    }

    @Transactional
    public Ledger getCompanyIdAndCode(int id, GROUP_CODE groupCode){
        return this.ledgerDao.findByCompanyAndCode(id,groupCode);
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
        ledger.setOpeningEntryType(ledgerForm.getLedgerAccountingEntry());
        ledger.setNotes(ledgerForm.getLedgerNote());
        ledger.setPersonalInformation(personalInformation);
        ledger.setReconciliation(0);

        this.save(ledger);
    }

    @Transactional(rollbackFor = Exception.class)
    public Ledger createEmployeeSalaryLedger(PersonalInformation personalInformation){
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

        return ledger;
    }
    @Transactional(rollbackFor = Exception.class)
    public void createSupplierLedger(Company company){
        GroupAccount groupAccount = this.groupAccountService.getByCode(GROUP_CODE.SUPPLIER);

        Ledger ledger = new Ledger();

        ledger.setName(company.getTitle());
        ledger.setGroupAccount(groupAccount);
        ledger.setLedgerType(LEDGER_TYPE.OTHER);
        ledger.setOpeningBalance(0d);
        ledger.setOpeningEntryType(ACCOUNTING_ENTRY.CR);
        ledger.setNotes("");
        ledger.setCompany(company);
        ledger.setReconciliation(0);

        this.save(ledger);
    }
    @Transactional(rollbackFor = Exception.class)
    public void createWholesalerLedger(Company company){
        GroupAccount groupAccount = this.groupAccountService.getByCode(GROUP_CODE.WHOLESALER);

        Ledger ledger = new Ledger();

        ledger.setName(company.getTitle());
        ledger.setGroupAccount(groupAccount);
        ledger.setLedgerType(LEDGER_TYPE.OTHER);
        ledger.setOpeningBalance(0d);
        ledger.setOpeningEntryType(ACCOUNTING_ENTRY.CR);
        ledger.setNotes("");
        ledger.setCompany(company);
        ledger.setReconciliation(0);

        this.save(ledger);
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editSupplierLedger(Company company){

        Ledger ledger = this.getCompanyIdAndCode(company.getId(),GROUP_CODE.SUPPLIER);
        this.updateLedgeName(ledger,company.getTitle());

        return ledger;
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editWholesalerLedger(Company company){

        Ledger ledger = this.getCompanyIdAndCode(company.getId(),GROUP_CODE.WHOLESALER);
        this.updateLedgeName(ledger,company.getTitle());

        return ledger;
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editEmployeeSalaryLedger(PersonalInformation personalInformation){

        Ledger ledger = this.getPersonalInfoIdAndCode(personalInformation.getId(),GROUP_CODE.SALARY);
        this.updateLedgeName(ledger,personalInformation.getFullName());

        return ledger;
    }
    private void updateLedgeName(Ledger ledger,String name){
        ledger.setName(name);
        this.update(ledger);
    }
    private void save(Ledger ledger){
        this.ledgerDao.save(ledger);
    }
    private void update(Ledger ledger){
        this.ledgerDao.update(ledger);
    }
}