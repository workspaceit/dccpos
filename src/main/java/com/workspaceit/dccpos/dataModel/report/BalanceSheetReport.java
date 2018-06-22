package com.workspaceit.dccpos.dataModel.report;

import java.util.List;

public class BalanceSheetReport {
    private List<ReportAccount> assetsAccounts;
    private List<ReportAccount> liabilityAccounts;
    private double grossProfit;
    private double totalAsset;
    private double totalLiability;

    public List<ReportAccount> getAssetsAccounts() {
        return assetsAccounts;
    }

    public void setAssetsAccounts(List<ReportAccount> assetsAccounts) {
        this.assetsAccounts = assetsAccounts;
    }

    public List<ReportAccount> getLiabilityAccounts() {
        return liabilityAccounts;
    }

    public void setLiabilityAccounts(List<ReportAccount> liabilityAccounts) {
        this.liabilityAccounts = liabilityAccounts;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(double totalAsset) {
        this.totalAsset = totalAsset;
    }

    public double getTotalLiability() {
        return totalLiability;
    }

    public void setTotalLiability(double totalLiability) {
        this.totalLiability = totalLiability;
    }
}
