package com.workspaceit.dccpos.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consumer")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "personal_info_id",referencedColumnName = "id",updatable = false)
    private PersonalInformation personalInformation;

    @Column(name = "consumer_id")
    private String consumerId;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",referencedColumnName = "id")
    private Employee createdBy;

    @JsonIgnore
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

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
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

        Consumer consumer = (Consumer) o;

        if (id != consumer.id) return false;
        if (personalInformation != null ? !personalInformation.equals(consumer.personalInformation) : consumer.personalInformation != null)
            return false;
        if (consumerId != null ? !consumerId.equals(consumer.consumerId) : consumer.consumerId != null) return false;
        if (createdBy != null ? !createdBy.equals(consumer.createdBy) : consumer.createdBy != null) return false;
        return createdAt != null ? createdAt.equals(consumer.createdAt) : consumer.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (personalInformation != null ? personalInformation.hashCode() : 0);
        result = 31 * result + (consumerId != null ? consumerId.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
