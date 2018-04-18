package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by mi on 1/11/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "temp_file")
public class TempFile {
    private int id;
    private int token;
    private String path;
    private String fileName;
    private Timestamp createdDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token")
    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @JsonIgnore
    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TempFile tempFile = (TempFile) o;

        if (id != tempFile.id) return false;
        if (token != tempFile.token) return false;
        if (path != null ? !path.equals(tempFile.path) : tempFile.path != null) return false;
        if (fileName != null ? !fileName.equals(tempFile.fileName) : tempFile.fileName != null) return false;
        return createdDate != null ? createdDate.equals(tempFile.createdDate) : tempFile.createdDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + token;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }
}
