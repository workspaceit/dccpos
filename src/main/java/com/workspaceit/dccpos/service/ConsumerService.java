package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.COMPANY_ROLE;
import com.workspaceit.dccpos.dao.ConsumerDao;
import com.workspaceit.dccpos.entity.Consumer;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.FormFilterHelper;
import com.workspaceit.dccpos.helper.IdGenerator;
import com.workspaceit.dccpos.validation.form.consumer.ConsumerForm;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Consumer> getAll(){
        return this.consumerDao.findAll();
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
    public Consumer create(ConsumerForm consumerForm, Employee employee){
        FormFilterHelper.doBasicFiler(consumerForm);

        Consumer consumer = new Consumer();

        PersonalInformation personalInformation = this.personalInformationService.create(consumerForm.getPersonalInfo(), COMPANY_ROLE.CONSUMER);
        consumer.setPersonalInformation(personalInformation);
        consumer.setConsumerId(consumerForm.getConsumerId());
        consumer.setCreatedBy(employee);
        this.save(consumer);


        return consumer;
    }


    public ConsumerForm requestNewConsumerForm(){
        long maxId = this.consumerDao.findMaxId(Consumer.class);
        ConsumerForm consumerForm = new ConsumerForm();
        PersonalInfoCreateForm personalInfoCreateForm =  new PersonalInfoCreateForm();
        personalInfoCreateForm.setFullName("GUEST");
        consumerForm.setPersonalInfo(personalInfoCreateForm);
        consumerForm.setConsumerId(IdGenerator.getConsumerId(maxId+1));
        return consumerForm;
    }
    @Transactional(rollbackFor = Exception.class)
    public Consumer save(Consumer consumer){
        this.consumerDao.save(consumer);
        return consumer;
    }

}