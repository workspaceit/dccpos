package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.WholesalerDao;
import com.workspaceit.pos.entity.Company;
import com.workspaceit.pos.entity.Wholesaler;
import com.workspaceit.pos.entity.Wholesaler;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.service.accounting.LedgerService;
import com.workspaceit.pos.validation.form.wholesaler.WholesalerCreateForm;
import com.workspaceit.pos.validation.form.wholesaler.WholesalerUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WholesalerService {
    private WholesalerDao wholesalerDao;
    private CompanyService companyService;
    private LedgerService ledgerService;

    @Autowired
    public void setWholesalerDao(WholesalerDao wholesalerDao) {
        this.wholesalerDao = wholesalerDao;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Transactional
    public List<Wholesaler> getAll(){
        return this.wholesalerDao.getAll();
    }

    @Transactional
    public Wholesaler getById(int id){
        return this.wholesalerDao.getById(id);
    }
    @Transactional
    public Wholesaler getWholesaler(int id) throws EntityNotFound {
        Wholesaler wholesaler =  this.getById(id);

        if(wholesaler==null)throw new EntityNotFound("Wholesaler not found by id : "+id);

        return wholesaler;
    }
    @Transactional
    public Wholesaler getByWholesalerId(String employeeId){
        return this.wholesalerDao.getByWholesalerId(employeeId);
    }

    @Transactional
    public Wholesaler getByWholesalerIdAndNotById(String employeeId,int id){
        return this.wholesalerDao.getByWholesalerAndNotById(employeeId,id);
    }
    @Transactional(rollbackFor = Exception.class)
    public Wholesaler create(WholesalerCreateForm wholesalerCreateForm){
        Company company =  this.companyService.create(wholesalerCreateForm.getCompany());

        /**
         * Creating Wholesaler Ledger
         * */
        this.ledgerService.createWholesalerLedger(company);

        Wholesaler wholesaler = new Wholesaler();
        wholesaler.setCompany(company);
        wholesaler.setWholesalerId(wholesalerCreateForm.getWholesalerId());
        this.save(wholesaler);


        return wholesaler;
    }
    @Transactional(rollbackFor = Exception.class)
    public Wholesaler edit(int id, WholesalerUpdateForm wholesalerUpdateForm) throws EntityNotFound {
        Wholesaler wholesaler = this.getWholesaler(id);

        Company company =  this.companyService.edit(wholesaler.getCompany().getId(),wholesalerUpdateForm.getCompany());

        wholesaler.setCompany(company);
        wholesaler.setWholesalerId(wholesalerUpdateForm.getWholesalerId());

        this.update(wholesaler);

        /**
         * Creating Wholesaler Ledger
         * */
        this.ledgerService.editWholesalerLedger(company);
        return wholesaler;
    }
    private void save(Wholesaler wholesaler){
        this.wholesalerDao.save(wholesaler);
    }
    private void update(Wholesaler wholesaler){
        this.wholesalerDao.update(wholesaler);
    }
}