package com.workspaceit.dccpos.restendpoint.accounting;


import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.dataModel.report.BalanceSheetReport;
import com.workspaceit.dccpos.dataModel.report.ProfitAndLossReport;
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

    @RequestMapping(value = "/get-profit-and-loss",method = RequestMethod.GET)
    public ResponseEntity<ProfitAndLossReport> getProfitAndLoss(@RequestParam(value = "startDate",required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date startDate,
                                                      @RequestParam(value = "finishDate",required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date finishDate
    ){

       return ResponseEntity.ok(this.reportService.getProfitAndLossReport(startDate,finishDate ));
    }

    @RequestMapping(value = "/get-balance-sheet",method = RequestMethod.GET)
    public ResponseEntity<BalanceSheetReport> getBalanceSheet(@RequestParam(value = "startDate",required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date startDate,
                                                     @RequestParam(value = "finishDate",required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date finishDate
    ){

        return ResponseEntity.ok(this.reportService.getBalanceSheetReport(startDate,finishDate ));
    }
}