package com.workspaceit.pos.dao;

import com.workspaceit.pos.entity.Employee;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao extends BaseDao{
    public List<Employee> getAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Employee")
                .list();
    }
}