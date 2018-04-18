package com.workspaceit.dccpos.entity.accounting;

import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "acc_groups")
public class GroupAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "parent_id")
    private Integer parentId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    private List<GroupAccount> child;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private GROUP_CODE code;

    @Column(name = "affects_gross")
    private boolean affectsGross;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<GroupAccount> getChild() {
        return child;
    }

    public void setChild(List<GroupAccount> child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GROUP_CODE getCode() {
        return code;
    }

    public void setCode(GROUP_CODE code) {
        this.code = code;
    }

    public boolean isAffectsGross() {
        return affectsGross;
    }

    public void setAffectsGross(boolean affectsGross) {
        this.affectsGross = affectsGross;
    }
}