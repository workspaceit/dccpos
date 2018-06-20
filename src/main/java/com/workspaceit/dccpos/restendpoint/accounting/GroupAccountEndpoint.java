package com.workspaceit.dccpos.restendpoint.accounting;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import com.workspaceit.dccpos.service.accounting.GroupAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/group-account")
@CrossOrigin
public class GroupAccountEndpoint {
    private GroupAccountService groupAccountService;

    @Autowired
    public void setGroupAccountService(GroupAccountService groupAccountService) {
        this.groupAccountService = groupAccountService;
    }

    @RequestMapping("/all")
    public ResponseEntity<?> getAll(){
      List<GroupAccount> groupAccountList =  this.groupAccountService.getAll();

      return ResponseEntity.ok(groupAccountList);
    }
}