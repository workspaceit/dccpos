package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_TYPE;
import com.workspaceit.dccpos.dao.accounting.LedgerDao;
import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.NumberHelper;
import com.workspaceit.dccpos.validation.form.accounting.LedgerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class LedgerService {
    private LedgerDao ledgerDao;
    private GroupAccountService groupAccountService;
    private EntryItemService entryItemService;

    @Autowired
    public void setLedgerDao(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }

    @Autowired
    public void setGroupAccountService(GroupAccountService groupAccountService) {
        this.groupAccountService = groupAccountService;
    }

    @Autowired
    public void setEntryItemService(EntryItemService entryItemService) {
        this.entryItemService = entryItemService;
    }

    private void save(Ledger ledger){
        this.ledgerDao.save(ledger);
    }
    @Transactional(rollbackFor = Exception.class)
    public void update(Ledger ledger){
        this.ledgerDao.update(ledger);
    }

    private void update(Collection<Ledger> ledger){
        this.ledgerDao.updateAll(ledger);
    }

    @Transactional
    public List<Ledger> getAll(){
        return this.ledgerDao.findAll();
    }
    @Transactional
    public Ledger getByPersonalInfoIdAndCode(int id, GROUP_CODE groupCode){
        return this.ledgerDao.findByPersonalInfoIdAndGroupCode(id,groupCode);
    }
    @Transactional
    public Ledger getByPersonalInfoId(int personalInformationId){
        return this.ledgerDao.findByPersonalInfoId(personalInformationId);
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
    public List<Ledger> getByIds(Collection<Integer> id){
        return this.ledgerDao.findByIds(id);
    }


    @Transactional
    public Ledger getLedger(int id) throws EntityNotFound {
         Ledger ledger = this.ledgerDao.findById(id);

         if(ledger==null)throw new EntityNotFound("Ledger not found by id:"+id);
        return ledger;
    }

    @Transactional
    public Ledger getByCompanyId(int id){
        return this.ledgerDao.findByCompanyId(id);
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
        ledger.setNotes("");
        ledger.setCompany(company);
        ledger.setReconciliation(0);

        this.save(ledger);
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editSupplierLedger(Company company){

        Ledger ledger = this.getByCompanyId(company.getId());
        this.updateLedgeName(ledger,company.getTitle());

        return ledger;
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editWholesalerLedger(Company company){

        Ledger ledger = this.getByCompanyId(company.getId());
        this.updateLedgeName(ledger,company.getTitle());

        return ledger;
    }
    @Transactional(rollbackFor = Exception.class)
    public Ledger editEmployeeSalaryLedger(PersonalInformation personalInformation)throws EntityNotFound{

        Ledger ledger = this.getByPersonalInfoIdAndCode(personalInformation.getId(),GROUP_CODE.SALARY);
        if(ledger==null)throw new EntityNotFound("Salary Ledger not found ");
        this.updateLedgeName(ledger,personalInformation.getFullName());

        return ledger;
    }
    public boolean isCashOrBankAccountWillOverDrawn(Ledger ledger,double amount,boolean calculateFromEntireRecords){
        double currentBalance = ledger.getCurrentBalance();

        if(calculateFromEntireRecords){
            try {
                currentBalance = this.getCurrentBalance(ledger.getId());
            } catch (EntityNotFound entityNotFound) {
               System.out.println(entityNotFound.getMessage());
            }
        }

        if(currentBalance<amount){
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
    public boolean isAssetAccount( Ledger ledger){
        return ledger.getGroupAccount().getCode().equals(GROUP_CODE.ASSET);
    }
    private void updateLedgeName(Ledger ledger,String name){
        ledger.setName(name);
        this.update(ledger);
    }


    @Transactional
    public List<Ledger> getByGroupCode(GROUP_CODE groupCode){
        return this.ledgerDao.findByGroupCode(groupCode);
    }
    @Transactional
    public List<Ledger> getAllWholesaler(){
        return this.getByGroupCode(GROUP_CODE.WHOLESALER);
    }
    @Transactional
    public List<Ledger> getAllSupplier(){
        return this.getByGroupCode(GROUP_CODE.SUPPLIER);
    }
    @Transactional
    public List<Ledger> getAllEmployeeSalary(){
        return this.getByGroupCode(GROUP_CODE.SALARY);
    }
    @Transactional
    public List<Ledger> getAllBankOrCash(){
        return this.ledgerDao.findAllBakOrCash();
    }
    @Transactional
    public List<Ledger> getAssetAccount(){
        return this.ledgerDao.findByGroupCode(GROUP_CODE.ASSET);
    }
    @Transactional
    public List<Ledger> getLiabilityAccount(){
        return this.ledgerDao.findByGroupCode(GROUP_CODE.LIABILITY);
    }
    @Transactional
    public List<Ledger> getExpenseAccount(){
        return this.ledgerDao.findByGroupCode(GROUP_CODE.EXPENSE);
    }
    @Transactional
    public List<Ledger> getIncomeAccount(){
        return this.ledgerDao.findByGroupCode(GROUP_CODE.INCOME);

    }
    @Transactional
    public double getCurrentBalance(int ledgerId) throws EntityNotFound {
        Ledger ledger = this.getLedger(ledgerId);
        double drAmount = this.entryItemService.getBalance(ledger.getId(), ACCOUNTING_ENTRY.DR);
        double crAmount = this.entryItemService.getBalance(ledger.getId(), ACCOUNTING_ENTRY.CR);
        double balance = 0;

        switch (ledger.getOpeningBalanceEntryType()){
            case DR:
                balance = drAmount-crAmount;
                break;
            case CR:
                balance = crAmount-drAmount;
                break;
        }

        return  NumberHelper.round(balance,2);
    }
    @Transactional
    public void resolveCurrentBalance(Collection<EntryItem> entryItems){

        this.resolveCurrentBalanceByCalculateFromEntireEntry(entryItems);
    }
    private void resolveCurrentBalanceByCalculateFromEntireEntry(Collection<EntryItem> entryItems){
        List<Ledger> ledgerList = new ArrayList<>();

        for(EntryItem entryItem :entryItems){
            Ledger ledger =   entryItem.getLedger();

            if(ledger==null)continue;

            ledgerList.add(ledger);
        }

        for(Ledger ledger:ledgerList){
            double balance  = 0;
            try {
                balance = this.getCurrentBalance(ledger.getId());
            } catch (EntityNotFound entityNotFound) {
                System.out.println("From Ledger Service : "+entityNotFound.getMessage());
            }
            ledger.setCurrentBalance(balance);
        }
        this.update(ledgerList);
    }
}