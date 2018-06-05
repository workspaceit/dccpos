package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.constant.ACCESS_ROLE;
import com.workspaceit.dccpos.constant.COMPANY_ROLE;
import com.workspaceit.dccpos.constant.EMPLOYEE_TYPE;
import com.workspaceit.dccpos.dao.EmployeeDao;
import com.workspaceit.dccpos.entity.*;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.validation.form.employee.EmployeeCreateForm;
import com.workspaceit.dccpos.validation.form.employee.EmployeeUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EmployeeService {
    private PersonalInformationService personalInformationService;
    private AuthCredentialService authCredentialService;
    private LedgerService ledgerService;

    private EmployeeDao employeeDao;

    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    @Autowired
    public void setAuthCredentialService(AuthCredentialService authCredentialService) {
        this.authCredentialService = authCredentialService;
    }

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Transactional
    public List<Employee> getAll(){
        return this.employeeDao.findAll();
    }
    @Transactional
    public Employee getEmployee(int id)throws EntityNotFound{
        Employee employee =  this.employeeDao.findById(id);

        if(employee==null)throw new EntityNotFound("Employee not found by id :"+id);

        return employee;
    }
    @Transactional
    public Employee getById(int id){
        return this.employeeDao.findById(id);
    }

    @Transactional
    public Employee getByPersonalInformationId(int id){
        return this.employeeDao.findByPersonalInformationId(id);
    }
    @Transactional
    public Employee getByAuthCredential(AuthCredential authCredential){
        PersonalInformation personalInformation = authCredential.getPersonalInformation();
        if(personalInformation==null){
            personalInformation = this.personalInformationService.getById(personalInformation.getId());
        }
        return this.employeeDao.findByPersonalInformationId(personalInformation.getId());
    }

    @Transactional
    public Employee getByEmployeeId(String employeeId){
        return this.employeeDao.findByEmployeeId(employeeId);
    }

    @Transactional
    public  List<Employee> getByType(EMPLOYEE_TYPE employeeType){
        return this.employeeDao.findByType(employeeType);
    }
    @Transactional
    public Employee getByEmployeeIdAndNotById(String employeeId,int id){
        return this.employeeDao.findByEmployeeIdAndNotById(employeeId,id);
    }
    @Transactional(rollbackFor = Exception.class)
    public Employee create(EmployeeCreateForm employeeForm){
        PersonalInformation personalInfo = this.personalInformationService.create(employeeForm.getPersonalInfo(),COMPANY_ROLE.EMPLOYEE);


        Employee employee = new Employee();
        employee.setEmployeeId(employeeForm.getEmployeeId());
        employee.setSalary(employeeForm.getSalary());
        employee.setPersonalInformation(personalInfo);
        employee.setType(employeeForm.getType());

        this.save(employee);



        /**
         * Creating login credential
         * */

        AccessRole accessRole = this.getAccessRoleByEmployeeType(employeeForm.getType());


        this.authCredentialService.create(employeeForm.getAuthCredential(),personalInfo,accessRole);

        /**
         * Create ledger account under SALARY GROUP
         * */
        this.ledgerService.createEmployeeSalaryLedger(personalInfo);

        return employee;
    }
    @Transactional(rollbackFor = Exception.class)
    public Employee edit(int id,EmployeeUpdateForm employeeForm) throws EntityNotFound {
        Employee employee = this.getEmployee(id);
        PersonalInformation personalInfo = employee.getPersonalInformation();

        /**
         * Creating login credential
         * */
        AuthCredential authCredential = personalInfo.getAuthCredential();


        /**
         * If Employee type change
         * Access role also change
         * Role is collection but only one role exist
         * */

        if(!employee.getType().equals(employeeForm.getType())){
            employee.setType(employeeForm.getType());

            AccessRole accessRole = this.getAccessRoleByEmployeeType(employeeForm.getType());
            Collection<AccessRole> accessRoles =  authCredential.getAccessRole();
            if(accessRoles==null){
                accessRoles = new ArrayList<>();
            }

            for(AccessRole tmpAccessRole:accessRoles){
                tmpAccessRole.setAccessRole(accessRole.getAccessRole());
                break;
            }

            this.authCredentialService.update(authCredential);
        }



        employee.setEmployeeId(employeeForm.getEmployeeId());
        employee.setSalary(employeeForm.getSalary());

        this.save(employee);


        this.personalInformationService.edit(personalInfo.getId(),employeeForm.getPersonalInfo());

        /**
         * Update ledger account under SALARY GROUP
         * */
        this.ledgerService.editEmployeeSalaryLedger(personalInfo);




        return employee;
    }
    private AccessRole getAccessRoleByEmployeeType(EMPLOYEE_TYPE employeeType){
        AccessRole accessRole = new AccessRole();
        switch (employeeType){
            case ADMIN:
                accessRole.setAccessRole(ACCESS_ROLE.ALL);
                break;
            case OFFICER:
                accessRole.setAccessRole(ACCESS_ROLE.POS_OPERATOR);
                break;
        }
        return accessRole;
    }
    private void save(Employee employee){
        this.employeeDao.save(employee);
    }
}