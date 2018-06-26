

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.entity.Wholesaler;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.service.CompanyService;
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

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class SampleTest extends BaseTest {

    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private FormToNameValuePair formToNameValuePair;

    @Autowired
    CompanyService companyService;

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
        Wholesaler wholesaler = (Wholesaler)companyService
                .getByFieldName(Wholesaler.class,"company","email","a@a.com");
        System.out.println(wholesaler.getId());

    }
}