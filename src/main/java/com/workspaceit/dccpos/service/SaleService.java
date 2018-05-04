package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.SALE_TYPE;
import com.workspaceit.dccpos.dao.SaleDao;
import com.workspaceit.dccpos.entity.Sale;
import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SaleService {
    private SaleDao saleDao;
    private PersonalInformationService personalInformationService;
    private EmployeeService employeeService;


    @Autowired
    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void create(SaleForm saleForm){
        Sale sale = new Sale();

        sale.setWholesaler(null);
        sale.setConsumer(null);
        sale.setDate(new Date());
        sale.setDiscount(12d);
        sale.setVat(4d);
        sale.setSoldBy(this.employeeService.getById(1));
        sale.setTotalDue(41d);
        sale.setTotalPrice(13d);
        sale.setTotalQuantity(4);
        sale.setTotalReceive(1d);
        sale.setTotalRefundAmount(4d);
        sale.setTotalRefundAmountDue(1d);
        sale.setTotalRefundAmountPaid(12d);
        sale.setType(SALE_TYPE.WHOLESALE);
        sale.setNote("Illegal Weapon sold ");
        System.out.println(sale.getId());
        this.save(sale);
    }
    public Sale save(Sale sale){
        this.saleDao.save(sale);
        return sale;
    }
}