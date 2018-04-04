package com.workspaceit.pos.api;

import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oauth/api/employee")
public class EmployeeEndPoint {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Employee> employees = this.employeeService.getAll();
        return ResponseEntity.ok(employees);
    }
}