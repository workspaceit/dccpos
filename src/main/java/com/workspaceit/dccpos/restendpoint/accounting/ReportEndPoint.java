package com.workspaceit.dccpos.restendpoint.accounting;


import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.service.accounting.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth+"/report")
@CrossOrigin
public class ReportEndPoint {
    private ReportService reportService;


    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/get-profit-and-loss/{startDate}/{finishDate}",method = RequestMethod.GET)
    public ResponseEntity<?> getAll(  @PathVariable("startDate") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date startDate,
                                      @PathVariable("finishDate") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date finishDate
    ){
        System.out.println(startDate);
        System.out.println(finishDate);
       return ResponseEntity.ok(this.reportService.getProfitAndLossReport(startDate,finishDate ));
    }
}
