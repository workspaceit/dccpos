

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.dataModel.report.ProfitAndLossReport;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.service.accounting.EntryItemService;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.service.accounting.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import resetendpointTest.BaseTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class SampleTest extends BaseTest {

    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private FormToNameValuePair formToNameValuePair;

    @Autowired
   private LedgerService ledgerService;
    @Autowired
    private EntryItemService entryItemService;

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }
    @Autowired
    public void setFormToNameValuePair(FormToNameValuePair formToNameValuePair) {
        this.formToNameValuePair = formToNameValuePair;
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void test() throws Exception {
        Ledger ledger = ledgerService.getLedger(6);
        System.out.println(ledger.getOpeningBalanceEntryType());


        double drAmount = this.entryItemService.getSum(ledger.getId(), ACCOUNTING_ENTRY.DR);
        double crAmount = this.entryItemService.getSum(ledger.getId(), ACCOUNTING_ENTRY.CR);
        System.out.println(drAmount);
        System.out.println(crAmount);

    }
}