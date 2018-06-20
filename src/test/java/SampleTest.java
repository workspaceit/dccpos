

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.dataModel.profitAndLoss.ProfitAndLossReport;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
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
import java.util.Date;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class SampleTest extends BaseTest {

    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private FormToNameValuePair formToNameValuePair;

    @Autowired
    private ReportService reportService;

    @Autowired
    private LedgerService ledgerService;


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
        ProfitAndLossReport profitAndLossReport = this.reportService.getProfitAndLossReport(null,null);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(ledgerService.getBalance(1,formatter.parse("2017-12-12"),formatter.parse("2018-02-12")));
        ObjectMapper objectMapper = new   ObjectMapper();
        String jsonObj = objectMapper.writeValueAsString(profitAndLossReport);
       // System.out.println(jsonObj);
    }
}