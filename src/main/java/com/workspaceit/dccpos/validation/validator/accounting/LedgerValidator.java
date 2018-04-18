package com.workspaceit.dccpos.validation.validator.accounting;

import com.workspaceit.dccpos.util.CustomValidationUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class LedgerValidator {
    public void validate(Errors errors){
        CustomValidationUtil.rejectIfEmptyOrWhitespace(errors,"ledgerName","Ledger name required");
        CustomValidationUtil.rejectIfNull(errors,"bankOrCash","Bank or cash account required");
        CustomValidationUtil.rejectIfNull(errors,"accountingEntry","Accounting entry DR or CR required");
    }
}