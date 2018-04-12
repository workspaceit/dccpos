package com.workspaceit.pos.restendpoint.accounting;

import com.workspaceit.pos.constant.EndpointRequestUriPrefix;
import com.workspaceit.pos.entity.accounting.Entry;
import com.workspaceit.pos.service.accounting.EntryService;
import com.workspaceit.pos.validation.form.authcredential.PasswordResetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth +"/entry")
public class EntryEndPoint {
    private EntryService entryService;

    @Autowired
    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
    }
    @RequestMapping("/get-by-date/{startDate}/{endDate}")
    public ResponseEntity<?> getByDate(@PathVariable("startDate") String startDate,
                                       @PathVariable("endDate") String endDate,
                                       @Valid PasswordResetForm passwordResetForm,
                                       BindingResult bindingResult){

        PropertyEditor propertyEditor = bindingResult.findEditor("newPassword",String.class);
        System.out.println(bindingResult.getRawFieldValue("newPassword"));
        System.out.println(propertyEditor.getValue());


        List<Entry> entries = this.entryService.getByDate(null,null);
        return ResponseEntity.ok(entries);
    }
}