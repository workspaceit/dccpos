package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.EmployeeDao;
import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.entity.PersonalInformation;
import com.workspaceit.pos.validation.form.EmployeeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private PersonalInformationService personalInformationService;

    private EmployeeDao employeeDao;

    @Autowired
    public void setPersonalInformationService(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    @Transactional
    public List<Employee> getAll(){
        return this.employeeDao.getAll();
    }
    @Transactional
    public Employee getByEmployeeId(String employeeId){
        return this.employeeDao.getByEmployeeId(employeeId);
    }
    @Transactional(rollbackFor = Exception.class)
    public Employee create(EmployeeForm employeeForm){
        PersonalInformation personalInfo = this.personalInformationService.create(employeeForm.getPersonalInfo());

        Employee employee = new Employee();
        employee.setEmployeeId(employeeForm.getEmployeeId());
        employee.setSalary(employee.getSalary());
        employee.setPersonalInformation(personalInfo);

        this.save(employee);
        return employee;
    }
    private void save(Employee employee){
        this.employeeDao.save(employee);
    }
}