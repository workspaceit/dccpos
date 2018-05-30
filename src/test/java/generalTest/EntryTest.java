package generalTest;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.dao.EntryItemDao;
import com.workspaceit.dccpos.entity.accounting.Entry;
import com.workspaceit.dccpos.entity.accounting.EntryItem;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.helper.NumberHelper;
import com.workspaceit.dccpos.service.accounting.EntryService;
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

import static com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY.CR;
import static com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY.DR;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class EntryTest extends BaseTest{

    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private FormToNameValuePair formToNameValuePair;
    private EntryItemDao entryItemDao;

    private EntryService entryService;

    @Autowired
    public void setEntryService(EntryService entryService) {
        this.entryService = entryService;
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
    public void entryItemTest() throws Exception {
        List<Entry> entryList = this.entryService.getAll();
        double drAmount = 0;
        double crAmount = 0;
        for(Entry entry:entryList) {
            List<EntryItem> entryItems =   entry.getEntryItems();
            for(EntryItem entryItem :entryItems){
                switch(entryItem.getAccountingEntry()){
                    case DR:
                        drAmount += entryItem.getAmount();
                        break;
                    case  CR:
                        crAmount += entryItem.getAmount();
                        break;

                }
            }

            Assert.assertEquals("ID : "+entry.getId()+", Dr Cr mismatched ",
                    NumberHelper.round(drAmount,2),
                    NumberHelper.round(crAmount,2),0);
        }





    }
}