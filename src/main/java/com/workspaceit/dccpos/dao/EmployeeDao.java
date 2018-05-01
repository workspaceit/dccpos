package com.workspaceit.dccpos.dao;

import com.workspaceit.dccpos.constant.EMPLOYEE_TYPE;
import com.workspaceit.dccpos.entity.Employee;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao extends BaseDao{
    public List<Employee> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Employee")
                .list();
    }
    public List<Employee> findByType(EMPLOYEE_TYPE employeeType){
        Session session = this.getCurrentSession();
        return session.createQuery(" FROM Employee emp where emp.type=:employeeType")
                .setParameter("employeeType",employeeType)
                .list();
    }
    public Employee findByEmployeeId(String employeeId){
        Session session = this.getCurrentSession();
        return (Employee)session.createQuery(" FROM Employee emp where emp.employeeId =:employeeId")
                .setParameter("employeeId",employeeId)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Employee findByEmployeeIdAndNotById(String employeeId, int id){
        Session session = this.getCurrentSession();
        return (Employee)session.createQuery(" FROM Employee emp where id!=:id and emp.employeeId =:employeeId")
                .setParameter("employeeId",employeeId)
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Employee findById(int id){
        Session session = this.getCurrentSession();
        return (Employee)session.createQuery(" FROM Employee emp where emp.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Employee findByPersonalInformationId(int id){
        Session session = this.getCurrentSession();
        return (Employee)session.createQuery(" FROM Employee emp where emp.personalInformation.id =:id")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
}