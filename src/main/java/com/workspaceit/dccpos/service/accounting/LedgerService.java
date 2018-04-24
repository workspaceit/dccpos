package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_TYPE;
import com.workspaceit.dccpos.dao.accounting.LedgerDao;
import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.accounting.LedgerForm;
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
        return this.ledgerDao.findByPersonalInfoIdAndGroupCode(id,groupCode);
    }
    @Transactional
    public Ledger getByCode(LEDGER_CODE ledgerCode){
        return this.ledgerDao.findByCode(ledgerCode);
    }
    @Transactional
    public Ledger getById(int id){
        return this.ledgerDao.findById(id);
    }


    @Transactional
    public Ledger getLedger(int id) throws EntityNotFound {
         Ledger ledger = this.ledgerDao.findById(id);

         if(ledger==null)throw new EntityNotFound("Ledger not found by id:"+id);
        return ledger;
    }

    @Transactional
    public Ledger getByCompanyIdAndCode(int id, GROUP_CODE groupCode){
        return this.ledgerDao.findByCompanyAndGroupCode(id,groupCode);
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
        ledger.setOpeningBalanceEntryType(ledgerForm.getLedgerAccountingEntry());
        ledger.setCurrentBalance(0d);
        ledger.setCurrentBalanceEntryType(ledgerForm.getLedgerAccountingEntry());
        ledger.setNotes(ledgerForm.getLedgerNote());
        ledger.setPersonalInformation(personalInformation);
        ledger.setReconciliation(0);

        this.save(ledger);
    }

    @Transactional(rollbackFor = Exception.class)
    public Ledger createEmployeeSalaryLedger(PersonalInformation personalInformation){
        GroupAccount groupAccount = this.groupAccountService.getByCode(GROUP_CODE.SALARY);

        Ledger ledger = new Ledger();

        ledger.setName(personalInformation.getFullName());
        ledger.setGroupAccount(groupAccount);
        ledger.setLedgerType(LEDGER_TYPE.OTHER);
        ledger.setOpeningBalance(0d);
        ledger.setOpeningBalanceEntryType(ACCOUNTING_ENTRY.DR);
        ledger.setCurrentBalance(0d);
        ledger.setCurrentBalanceEntryType(ACCOUNTING_ENTRY.DR);
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
        ledger.setOpeningBalanceEntryType(ACCOUNTING_ENTRY.CR);
        ledger.setCurrentBalance(0d);
        ledger.setCurrentBalanceEntryType(ACCOUNTING_ENTRY.CR);
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
        ledger.setOpeningBalanceEntryType(ACCOUNTING_ENTRY.CR);
        ledger.setCurrentBalance(0d);
        ledger.setCurrentBalanceEntryType(ACCOUNTING_ENTRY.CR);
        ledger.setNotes("");
        ledger.setCompany(company);
        ledger.setReconciliation(0);

        this.save(ledger);
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editSupplierLedger(Company company){

        Ledger ledger = this.getByCompanyIdAndCode(company.getId(),GROUP_CODE.SUPPLIER);
        this.updateLedgeName(ledger,company.getTitle());

        return ledger;
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editWholesalerLedger(Company company){

        Ledger ledger = this.getByCompanyIdAndCode(company.getId(),GROUP_CODE.WHOLESALER);
        this.updateLedgeName(ledger,company.getTitle());

        return ledger;
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editEmployeeSalaryLedger(PersonalInformation personalInformation){

        Ledger ledger = this.getPersonalInfoIdAndCode(personalInformation.getId(),GROUP_CODE.SALARY);
        this.updateLedgeName(ledger,personalInformation.getFullName());

        return ledger;
    }
    public boolean isCashOrBankAccountWillOverDrawn(Ledger ledger,double amount,boolean calculateFromEntierRecords){
        ACCOUNTING_ENTRY openingBalanceEntryType = ledger.getOpeningBalanceEntryType();
        ACCOUNTING_ENTRY currentBalanceEntryType = ledger.getCurrentBalanceEntryType();
        double currentBalance = ledger.getCurrentBalance();
        if(openingBalanceEntryType.equals(currentBalanceEntryType)){
            if(currentBalance<amount){
                return true;
            }
        }else{
            return true;
        }
        return false;

    }
    public boolean isCashOrBankAccountWillOverDrawn(int ledgerId,double amount,boolean calculateFromEntireRecords) throws EntityNotFound {
       Ledger ledger =  this.getLedger(ledgerId);
       return this.isCashOrBankAccountWillOverDrawn(ledger,amount,calculateFromEntireRecords);
    }
    public boolean isCashOrBankAccount(int ledgerId) throws EntityNotFound {
        Ledger ledger =  this.getLedger(ledgerId);

        return this.isCashOrBankAccount(ledger);

    }
    public boolean isCashOrBankAccount( Ledger ledger){
        return ledger.getLedgerType().equals(LEDGER_TYPE.CASH_ACCOUNT);
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