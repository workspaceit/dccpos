package com.workspaceit.dccpos.entity;

import com.workspaceit.dccpos.constant.ACCESS_ROLE;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "acess_role")
public class AccessRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "auth_credential_id",insertable = false,updatable = false)
    private int authCredentialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ACCESS_ROLE accessRole;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthCredentialId() {
        return authCredentialId;
    }

    public void setAuthCredentialId(int authCredentialId) {
        this.authCredentialId = authCredentialId;
    }

    public ACCESS_ROLE getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(ACCESS_ROLE accessRole) {
        this.accessRole = accessRole;
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

        AccessRole acessRole = (AccessRole) o;

        if (id != acessRole.id) return false;
        if (authCredentialId != acessRole.authCredentialId) return false;
        if (accessRole != acessRole.accessRole) return false;
        return createdAt != null ? createdAt.equals(acessRole.createdAt) : acessRole.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + authCredentialId;
        result = 31 * result + (accessRole != null ? accessRole.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
