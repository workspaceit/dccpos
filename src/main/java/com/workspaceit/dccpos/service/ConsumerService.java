package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.COMPANY_ROLE;
import com.workspaceit.dccpos.dao.ConsumerDao;
import com.workspaceit.dccpos.entity.Consumer;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.consumer.ConsumerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsumerService {
    private ConsumerDao consumerDao;
    private PersonalInformationService personalInformationService;

    @Autowired
    public void setConsumerDao(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }

    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    @Transactional
    public Consumer getConsumer(int id)throws EntityNotFound{
        Consumer consumer =  this.consumerDao.findById(id);
        if(consumer==null)throw new EntityNotFound("Consumer not found by Id: "+id);
        return consumer;
    }
    @Transactional
    public Consumer getById(int id){
        return this.consumerDao.findById(id);
    }
    @Transactional
    public Consumer getByConsumerId(String consumerId){
        return this.consumerDao.findByConsumerId(consumerId);
    }


    @Transactional(rollbackFor = Exception.class)
    public Consumer create(ConsumerForm consumerForm){
        Consumer consumer = new Consumer();

        PersonalInformation personalInformation = this.personalInformationService.create(consumerForm.getPersonalInfo(), COMPANY_ROLE.CONSUMER);
        consumer.setPersonalInformation(personalInformation);
        consumer.setConsumerId(consumerForm.getConsumerId());
        this.save(consumer);


        return consumer;
    }
    @Transactional(rollbackFor = Exception.class)
    public Consumer save(Consumer consumer){
        this.consumerDao.save(consumer);
        return consumer;
    }

}