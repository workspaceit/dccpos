package com.workspaceit.dccpos.util;

import com.workspaceit.dccpos.dataModel.report.ProfitAndLossReport;
import com.workspaceit.dccpos.dataModel.report.ReportAccount;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

@Component
public class ReportUtil {
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

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
    public double calculateTotalAmount(ProfitAndLossReport profitAndLossReport){
        double totalRevenue = 0;

        List<ReportAccount> incomeAccounts =profitAndLossReport.getIncomeAccounts();
        if(incomeAccounts==null)return totalRevenue;

        totalRevenue +=  this.calculateTotalAmount(incomeAccounts);

        return totalRevenue;
    }
    public double calculateTotalAmount(List<ReportAccount> incomeAccounts){
        double total = 0;

        if(incomeAccounts==null)return total;

        for(ReportAccount incomeAccount:incomeAccounts){
            total +=  incomeAccount.getAmount();
            if(incomeAccount.getChild()==null)continue;
            total += this.calculateTotalAmount(incomeAccount.getChild());
        }


        return Double.valueOf(this.decimalFormat.format(total));
    }
    public double calculateGrossProfit(List<ReportAccount> incomeAccounts,List<ReportAccount> expenseAccounts){

        double grossProfit,totalRevenue,totalExpense;

        totalRevenue =  this.calculateTotalAmount(incomeAccounts);
        totalExpense = this.calculateTotalExpense(expenseAccounts);

        grossProfit = totalRevenue - totalExpense;

        return grossProfit;
    }

}