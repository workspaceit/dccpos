package com.workspaceit.dccpos.dataModel.profitAndLoss;

import java.util.List;

public class ProfitAndLossReport {
    private List<ReportAccount> incomeAccounts;
    private List<ReportAccount> expenseAccounts;
    private double totalRevenue;
    private double grossProfit;

    public List<ReportAccount> getIncomeAccounts() {
        return incomeAccounts;
    }

    public void setIncomeAccounts(List<ReportAccount> incomeAccounts) {
        this.incomeAccounts = incomeAccounts;
    }

    public List<ReportAccount> getExpenseAccounts() {
        return expenseAccounts;
    }

    public void setExpenseAccounts(List<ReportAccount> expenseAccounts) {
        this.expenseAccounts = expenseAccounts;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }
}