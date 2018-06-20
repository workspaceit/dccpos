package com.workspaceit.dccpos.helper;

import com.workspaceit.dccpos.entity.accounting.Ledger;

public class LedgerHelper {
    public static double getBalance(Ledger ledger,double drAmount,double crAmount){
        double balance = 0;
        switch (ledger.getOpeningBalanceEntryType()){
            case DR:
                balance = drAmount-crAmount;
                break;
            case CR:
                balance = crAmount-drAmount;
                break;
        }
        return balance;
    }
}
