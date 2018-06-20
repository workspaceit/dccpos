package com.workspaceit.dccpos.dataModel.profitAndLoss;

import java.util.List;

public class ProfitAndLossReport {
    private List<ReportAccount> incomeAccounts;
    private List<ReportAccount> expenseAccounts;

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
}