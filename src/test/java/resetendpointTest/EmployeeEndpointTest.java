package resetendpointTest;

import com.workspaceit.dccpos.config.WebConfig;

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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class EmployeeEndpointTest extends BaseTest{

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
    public void createEmployee() throws Exception {

        MvcResult result = mockMvc.perform(
                post(this.authUri+"/employee/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("")
                        )
                .andExpect(status().isUnprocessableEntity()).andReturn();
    }
    @Test
    public void editEmployee() throws Exception {

        MvcResult result = mockMvc.perform(
                post(this.authUri+"/employee/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("")
        )
                .andExpect(status().isUnprocessableEntity()).andReturn();
    }
    @Test
    public void allEmployee() throws Exception {

        MvcResult result = mockMvc.perform(
                get(this.authUri+"/employee/get-all"))
                .andExpect(status().isOk()).andReturn();
    }
}
