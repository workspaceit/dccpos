package com.workspaceit.pos.entity;

import com.workspaceit.pos.constant.COMPANY_ROLE;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company_role")
public class CompanyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private COMPANY_ROLE companyRole;

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

    public COMPANY_ROLE getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(COMPANY_ROLE companyRole) {
        this.companyRole = companyRole;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
