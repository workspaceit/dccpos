package com.workspaceit.dccpos.util.accounting;

import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class EntryItemUtil {
        public double getTotalAmount(Collection<EntryItem> entryItems, GROUP_CODE groupCode){
            if(entryItems==null || entryItems.size()==0){
                return 0;
            }

            double total = 0;

            total = entryItems.stream()
                    .filter(entryItem -> entryItem.getLedger().getGroupAccount().getCode().equals(groupCode))
                    .mapToDouble(entryItem->entryItem.getAmount()).sum();

            return total;
        }
}
