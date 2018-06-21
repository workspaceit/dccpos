package com.workspaceit.dccpos.service.accounting;

import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.dataModel.profitAndLoss.ProfitAndLossReport;
import com.workspaceit.dccpos.dataModel.profitAndLoss.ReportAccount;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.util.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReportService {
    private ReportUtil reportUtil;
    private LedgerService ledgerService;
    private GroupAccountService groupAccountService;

    @Autowired
    public void setReportUtil(ReportUtil reportUtil) {
        this.reportUtil = reportUtil;
    }

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Autowired
    public void setGroupAccountService(GroupAccountService groupAccountService) {
        this.groupAccountService = groupAccountService;
    }

    public ProfitAndLossReport getProfitAndLossReport(Date startDate, Date finishDate) {
        ProfitAndLossReport profitAndLossReport = new ProfitAndLossReport();
        List<ReportAccount> expenses = new ArrayList<>();
        List<ReportAccount> income = new ArrayList<>();

        ReportAccount expenseReportAccount = this.getReportAccounts(startDate,finishDate,GROUP_CODE.EXPENSE);
        ReportAccount incomeReportAccount = this.getReportAccounts(startDate,finishDate,GROUP_CODE.INCOME);


        if(expenseReportAccount.getChild()!=null)
            expenses.addAll(expenseReportAccount.getChild() );

        if(incomeReportAccount.getChild()!=null)
            income.addAll( incomeReportAccount.getChild() );

        double totalRevenue = this.reportUtil.calculateTotalRevenue(income);
        double grossProfit = this.reportUtil.calculateGrossProfit(income,expenses);

        profitAndLossReport.setIncomeAccounts(income);
        profitAndLossReport.setExpenseAccounts(expenses);
        profitAndLossReport.setTotalRevenue(totalRevenue);
        profitAndLossReport.setGrossProfit(grossProfit);

        return profitAndLossReport;
    }


    public ReportAccount getReportAccounts(Date startDate, Date finishDate, GROUP_CODE groupCode) {

        GroupAccount targetGroupAccount = this.groupAccountService.getByCode(groupCode);
        ReportAccount  parentReportAccount =  new ReportAccount();
        List<Ledger> ledgers = this.ledgerService.getByGroupId(targetGroupAccount.getId());
        List<ReportAccount> childReportGroup = this.getReportAccounts(ledgers,startDate,finishDate);


        parentReportAccount.setTitle(targetGroupAccount.getName());
        parentReportAccount.setIsGroup(true);
        parentReportAccount.setChild(new ArrayList<>());
        parentReportAccount.getChild().addAll(childReportGroup);


        List<GroupAccount> groupAccountChild = targetGroupAccount.getChild();

        if(groupAccountChild==null){
            return parentReportAccount;
        }

        for(GroupAccount childGroup : groupAccountChild){
            if(childGroup.getCode()==null)continue;

            ReportAccount childReportAccounts = this.getReportAccounts(startDate,finishDate,childGroup.getCode());
            parentReportAccount.getChild().add(childReportAccounts);

        }

        return parentReportAccount;
    }
    private List<ReportAccount> getReportAccounts( List<Ledger> ledgers,Date start,Date finish) {
        List<ReportAccount>  reportAccounts = new ArrayList<>();
        for(Ledger ledger:ledgers){
            GroupAccount groupAccount =  ledger.getGroupAccount();

            if(groupAccount==null)continue;

            ReportAccount  childReportAccount = new ReportAccount();
            childReportAccount.setTitle(ledger.getName());
            childReportAccount.setIsGroup(false);


            try {
               double balance =  this.ledgerService.getBalance(ledger.getId(),start,finish);
               childReportAccount.setAmount(balance);
            } catch (EntityNotFound entityNotFound) {
                System.out.println(entityNotFound.getMessage());
                childReportAccount.setAmount(0);
            }

            reportAccounts.add(childReportAccount);
        }
        return reportAccounts;
    }

}