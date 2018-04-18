package com.workspaceit.dccpos.validation.form.employee;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EmployeeForm {


    @Length(max = 50,message = "Value too large")
    protected String employeeId;

    @NotNull(message = "Salary required")
    @Min(value = 0,message = "Salary can't be less then zero")
    protected Float salary;


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }


}