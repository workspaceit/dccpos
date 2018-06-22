package com.workspaceit.dccpos.dataModel.report;

import java.util.List;

public class ReportAccount {
    private long id;
    private String title;
    private boolean isGroup;
    private List<ReportAccount> child;
    private double amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean group) {
        isGroup = group;
    }

    public List<ReportAccount> getChild() {
        return child;
    }

    public void setChild(List<ReportAccount> child) {
        this.child = child;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportAccount that = (ReportAccount) o;

        if (id != that.id) return false;
        if (isGroup != that.isGroup) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return child != null ? child.equals(that.child) : that.child == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (isGroup ? 1 : 0);
        result = 31 * result + (child != null ? child.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
