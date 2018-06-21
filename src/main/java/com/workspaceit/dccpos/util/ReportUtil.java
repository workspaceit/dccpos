package com.workspaceit.dccpos.util;

import com.workspaceit.dccpos.dataModel.profitAndLoss.ProfitAndLossReport;
import com.workspaceit.dccpos.dataModel.profitAndLoss.ReportAccount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportUtil {
    public double calculateTotalExpense(List<ReportAccount> expenseAccounts){
        double totalExpense = 0;

        if(expenseAccounts==null)return totalExpense;

        for(ReportAccount expenseAccount:expenseAccounts){
            totalExpense += expenseAccount.getAmount();
            if(expenseAccount.getChild()==null)continue;
            totalExpense += this.calculateTotalExpense(expenseAccount.getChild());
        }

        return totalExpense;
    }
    public double calculateTotalExpense(ProfitAndLossReport profitAndLossReport){
        double totalExpense = 0;

        List<ReportAccount> expenseAccounts = profitAndLossReport.getExpenseAccounts();
        if(expenseAccounts==null)return totalExpense;

        totalExpense =  this.calculateTotalExpense(expenseAccounts);

        return totalExpense;
    }
    public double calculateTotalRevenue(ProfitAndLossReport profitAndLossReport){
        double totalRevenue = 0;

        List<ReportAccount> incomeAccounts =profitAndLossReport.getIncomeAccounts();
        if(incomeAccounts==null)return totalRevenue;

        totalRevenue +=  this.calculateTotalRevenue(incomeAccounts);

        return totalRevenue;
    }
    public double calculateTotalRevenue(List<ReportAccount> incomeAccounts){
        double totalRevenue = 0;

        if(incomeAccounts==null)return totalRevenue;

        for(ReportAccount incomeAccount:incomeAccounts){
            totalRevenue +=  incomeAccount.getAmount();
            if(incomeAccount.getChild()==null)continue;
            totalRevenue += this.calculateTotalRevenue(incomeAccount.getChild());
        }

        return totalRevenue;
    }
    public double calculateGrossProfit(List<ReportAccount> incomeAccounts,List<ReportAccount> expenseAccounts){

        double grossProfit,totalRevenue,totalExpense;

        totalRevenue =  this.calculateTotalRevenue(incomeAccounts);
        totalExpense = this.calculateTotalExpense(expenseAccounts);

        grossProfit = totalRevenue - totalExpense;

        return grossProfit;
    }

}