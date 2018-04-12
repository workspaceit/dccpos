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
    public Employee getByEmployeeId(String employeeId){
        Session session = this.getCurrentSession();
        return (Employee)session.createQuery(" FROM Employee emp where emp.employeeId =:employeeId")
                .setParameter("employeeId",employeeId)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Employee getByEmployeeIdAndNotById(String employeeId,int id){
        Session session = this.getCurrentSession();
        return (Employee)session.createQuery(" FROM Employee emp where id!=:id and emp.employeeId =:employeeId")
                .setParameter("employeeId",employeeId)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Employee getById(int id){
        Session session = this.getCurrentSession();
        return (Employee)session.createQuery(" FROM Employee emp where emp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
}