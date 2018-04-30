package com.workspaceit.dccpos.entity;

import com.workspaceit.dccpos.constant.EMPLOYEE_TYPE;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "personal_info_id",referencedColumnName = "id",updatable = false)
    private PersonalInformation personalInformation;

    @Column(name = "employee_id")
    private String employeeId;


    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EMPLOYEE_TYPE type;

    @Column(name = "salary")
    private float salary;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public EMPLOYEE_TYPE getType() {
        return type;
    }

    public void setType(EMPLOYEE_TYPE type) {
        this.type = type;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (Float.compare(employee.salary, salary) != 0) return false;
        if (personalInformation != null ? !personalInformation.equals(employee.personalInformation) : employee.personalInformation != null)
            return false;
        if (employeeId != null ? !employeeId.equals(employee.employeeId) : employee.employeeId != null) return false;
        if (type != employee.type) return false;
        return createdAt != null ? createdAt.equals(employee.createdAt) : employee.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (personalInformation != null ? personalInformation.hashCode() : 0);
        result = 31 * result + (employeeId != null ? employeeId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (salary != +0.0f ? Float.floatToIntBits(salary) : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
