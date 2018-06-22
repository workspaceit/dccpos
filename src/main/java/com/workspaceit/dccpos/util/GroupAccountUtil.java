package com.workspaceit.dccpos.util;

import com.workspaceit.dccpos.dataModel.report.ReportAccount;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import com.workspaceit.dccpos.service.accounting.GroupAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class GroupAccountUtil {
    private GroupAccountService groupAccountService;

    @Autowired
    public void setGroupAccountService(GroupAccountService groupAccountService) {
        this.groupAccountService = groupAccountService;
    }

    public ReportAccount groupAccountToReportAccount(int groupAccountId){

        ReportAccount reportAccount = new ReportAccount();
        List<GroupAccount> groupAccounts = new ArrayList<>();

        GroupAccount firstParentGroupAccount = groupAccountService.getById(groupAccountId);

        if(firstParentGroupAccount==null){
            return reportAccount;
        }

        reportAccount.setChild(new ArrayList<>());
        groupAccounts.add(firstParentGroupAccount);

        Integer parentId = firstParentGroupAccount.getParentId();
        while(parentId!=null && parentId!=0){
            GroupAccount grandParentGroupAccount = this.groupAccountService.getById(parentId);
            if(grandParentGroupAccount==null)continue;

            groupAccounts.add(grandParentGroupAccount);
        }
        Collections.reverse(groupAccounts);

        ReportAccount root =  reportAccount;
        for (GroupAccount groupAccount:groupAccounts) {
            ReportAccount tmpReportAccount = root;
            tmpReportAccount.setTitle(groupAccount.getName());
            tmpReportAccount.setIsGroup(true);
            root.getChild().add(tmpReportAccount);

            root = tmpReportAccount;
        }




        return  reportAccount;
    }


}
