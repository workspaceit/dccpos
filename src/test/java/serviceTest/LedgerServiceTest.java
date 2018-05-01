package serviceTest;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.dao.EntryItemDao;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import org.junit.Assert;
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

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class LedgerServiceTest extends BaseTest{

    private MockMvc mockMvc;


    private WebApplicationContext wac;

    private FormToNameValuePair formToNameValuePair;

    private EntryItemDao entryItemDao;

    private LedgerService ledgerService;

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }
    @Autowired
    public void setFormToNameValuePair(FormToNameValuePair formToNameValuePair) {
        this.formToNameValuePair = formToNameValuePair;
    }

    @Autowired
    public void setEntryItemDao(EntryItemDao entryItemDao) {
        this.entryItemDao = entryItemDao;
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void currentBalanceConsistenceTest() throws Exception {
        List<Ledger> ledgerList = this.ledgerService.getAll();

        for(Ledger ledger:ledgerList){
           double balance  = this.ledgerService.getCurrentBalance(ledger.getId());

            Assert.assertEquals(ledger.getName()+" Ledger balance in equal",balance,
                    ledger.getCurrentBalance(),0);
        }


    }
}