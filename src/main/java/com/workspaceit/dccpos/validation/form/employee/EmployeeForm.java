package com.workspaceit.dccpos.validation.form.employee;

import com.workspaceit.dccpos.constant.EMPLOYEE_TYPE;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EmployeeForm {


    @Length(max = 50,message = "Value too large")
    protected String employeeId;

    @NotNull(message = "Salary required")
    @Min(value = 0,message = "Salary can't be less then zero")
    protected Float salary;

    @NotNull(message = "Type required")
    protected EMPLOYEE_TYPE type;


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


    public EMPLOYEE_TYPE getType() {
        return type;
    }

    public void setType(EMPLOYEE_TYPE type) {
        this.type = type;
    }
}