package resetendpoint;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.dao.EntryItemDao;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class SampleTest extends BaseTest{

    private MockMvc mockMvc;


    private WebApplicationContext wac;

    private FormToNameValuePair formToNameValuePair;

    private EntryItemDao entryItemDao;

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
    @Transactional
    public void test() throws Exception {
        System.out.println(entryItemDao.findDrBalance(1, ACCOUNTING_ENTRY.DR));

    }


}
