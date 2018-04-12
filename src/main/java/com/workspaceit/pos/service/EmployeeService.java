package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.EmployeeDao;
import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.entity.PersonalInformation;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.service.accounting.LedgerService;
import com.workspaceit.pos.validation.form.employee.EmployeeCreateForm;
import com.workspaceit.pos.validation.form.employee.EmployeeUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return this.employeeDao.getAll();
    }
    @Transactional
    public Employee getEmployee(int id)throws EntityNotFound{
        Employee employee =  this.employeeDao.getById(id);

        if(employee==null)throw new EntityNotFound("Employee not found by id :"+id);

        return employee;
    }
    @Transactional
    public Employee getById(int id){
        return this.employeeDao.getById(id);
    }

    @Transactional
    public Employee getByEmployeeId(String employeeId){
        return this.employeeDao.getByEmployeeId(employeeId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Employee create(EmployeeCreateForm employeeForm){
        PersonalInformation personalInfo = this.personalInformationService.create(employeeForm.getPersonalInfo());


        Employee employee = new Employee();
        employee.setEmployeeId(employeeForm.getEmployeeId());
        employee.setSalary(employee.getSalary());
        employee.setPersonalInformation(personalInfo);

        this.save(employee);

        /**
         * Creating login credential
         * */
        this.authCredentialService.create(employeeForm.getAuthCredential(),personalInfo);

        /**
         * Create ledger account under SALARY GROUP
         * */
        this.ledgerService.createEmployeeSalaryLedger(personalInfo);

        return employee;
    }
    @Transactional(rollbackFor = Exception.class)
    public Employee edit(int id,EmployeeUpdateForm employeeForm) throws EntityNotFound {


        Employee employee = this.getEmployee(id);
        employee.setEmployeeId(employeeForm.getEmployeeId());
        employee.setSalary(employee.getSalary());

        this.save(employee);

        PersonalInformation personalInfo = employee.getPersonalInformation();
        this.personalInformationService.edit(personalInfo.getId(),employeeForm.getPersonalInfo());

        /**
         * Update ledger account under SALARY GROUP
         * */
        this.ledgerService.editEmployeeSalaryLedger(personalInfo);

        return employee;
    }
    private void save(Employee employee){
        this.employeeDao.save(employee);
    }
}