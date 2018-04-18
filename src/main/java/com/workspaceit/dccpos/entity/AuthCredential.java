package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workspaceit.dccpos.constant.ACCESS_ACCOUNT_STATUS;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "auth_credential")
public class AuthCredential {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "personal_info_id",referencedColumnName = "id",nullable = false,updatable = false)
    private PersonalInformation personalInformation;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ACCESS_ACCOUNT_STATUS status;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_credential_id",nullable = false)
    private Collection<AccessRole> accessRole;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    public int getId() {
        return id;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ACCESS_ACCOUNT_STATUS getStatus() {
        return status;
    }

    public void setStatus(ACCESS_ACCOUNT_STATUS status) {
        this.status = status;
    }

    public Collection<AccessRole> getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(Collection<AccessRole> accessRole) {
        this.accessRole = accessRole;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}