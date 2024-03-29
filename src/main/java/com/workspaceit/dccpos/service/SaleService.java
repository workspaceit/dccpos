package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.SaleDao;
import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.IdGenerator;
import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SaleService {
    private SaleDao saleDao;
    private PersonalInformationService personalInformationService;
    private SaleDetailsService saleDetailsService;
    private WholesalerService wholesalerService;
    private InventoryService inventoryService;
    private ConsumerService consumerService;

    @Autowired
    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    @Autowired
    public void setSaleDetailsService(SaleDetailsService saleDetailsService) {
        this.saleDetailsService = saleDetailsService;
    }

    @Autowired
    public void setWholesalerService(WholesalerService wholesalerService) {
        this.wholesalerService = wholesalerService;
    }

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @Transactional
    public List<Sale> getAll(){
        return this.saleDao.getAll();
    }
    @Transactional
    public Sale getSale(long id)throws EntityNotFound{
        Sale sale = this.getById(id);
        if(sale==null)throw  new EntityNotFound("Sale information not found by id :"+id);
        return sale;
    }
    @Transactional
    public Sale getById(long id){
        return this.saleDao.getById(id);
    }
    @Transactional
    public Sale getByTrackingId(String trackingId,boolean fetchLazy){
        if(fetchLazy)
            return this.saleDao.getByTrackingIdFetchLazy(trackingId);
        else
            return this.saleDao.getByTrackingId(trackingId);
    }
    @Transactional(rollbackFor = Exception.class)
    public Sale create(SaleForm saleForm, Employee employee) throws EntityNotFound {
        Sale sale = new Sale();
        Set<SaleDetails> saleDetailsList = this.saleDetailsService.getSaleDetails(sale,saleForm.getInventories());
        Wholesaler wholesaler = null;
        Consumer consumerInfo = null;
        double totalDue = 0;
        double totalPrice = 0;
        int totalQuantity = 0;
        double discount = saleForm.getDiscount()!=null?saleForm.getDiscount():0d;
        double vat =  saleForm.getVat()!=null?saleForm.getVat():0d;

        switch (saleForm.getType()){
            case WHOLESALE:
                wholesaler = this.wholesalerService.getWholesaler(saleForm.getWholesalerId());
                break;
            case CONSUMER_SALE:
                if(saleForm.getConsumerInfoId()!=null && saleForm.getConsumerInfoId()>0) {
                    consumerInfo = this.consumerService.getConsumer(saleForm.getConsumerInfoId());
                }else{
                    consumerInfo = this.consumerService.create(saleForm.getConsumerInfo(),employee);

                }
                break;
        }

        sale.setTrackingId(IdGenerator.getSaleTrackingId(this.saleDao.findMaxId(Sale.class)+1));
        sale.setWholesaler(wholesaler);
        sale.setConsumer(consumerInfo);
        sale.setDate(saleForm.getDate());
        sale.setDiscount(discount);
        sale.setVat(vat);
        sale.setSoldBy(employee);
        sale.setTotalDue(totalDue);
        sale.setTotalPrice(totalPrice);
        sale.setTotalQuantity(totalQuantity);
        sale.setTotalReceive(0);
        sale.setTotalRefundAmount(0);
        sale.setTotalRefundAmountDue(0);
        sale.setTotalRefundAmountPaid(0);
        sale.setType(saleForm.getType());
        sale.setDescription(saleForm.getDescription());
        sale.setSaleDetails(saleDetailsList);

        this.save(sale);


        /**
         * After Sale
         * */
        this.inventoryService.decreaseAfterSale(sale);

        return sale;
    }
    private Sale save(Sale sale){
        this.saleDao.save(sale);
        return sale;
    }
    public Sale update(Sale sale){
        this.saleDao.update(sale);
        return sale;
    }
}