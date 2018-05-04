package resetendpointTest;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.constant.SALE_TYPE;
import com.workspaceit.dccpos.dao.EntryItemDao;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.Sale;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.service.EmployeeService;
import com.workspaceit.dccpos.service.PersonalInformationService;
import com.workspaceit.dccpos.service.SaleService;
import com.workspaceit.dccpos.service.accounting.LedgerService;
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

import java.util.Date;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class SampleTest extends BaseTest{

    private MockMvc mockMvc;


    private WebApplicationContext wac;

    private FormToNameValuePair formToNameValuePair;




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

    }
}