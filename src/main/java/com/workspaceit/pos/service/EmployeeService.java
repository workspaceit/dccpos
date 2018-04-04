package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.EmployeeDao;
import com.workspaceit.pos.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private EmployeeDao employeeDao;

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    @Transactional
    public List<Employee> getAll(){
        return this.employeeDao.getAll();
    }
}