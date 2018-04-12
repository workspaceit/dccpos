package resetendpoint;
import com.workspaceit.pos.config.WebConfig;

import com.workspaceit.pos.helper.FormToNameValuePair;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class PasswordManagementEndPoint extends BaseTest {
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
    public void requestForNewPasswordResetToken() throws Exception {

        MvcResult result = mockMvc.perform(
                post(this.authUri+"/reset-password/request-new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("")
        )
                .andExpect(status().isUnprocessableEntity()).andReturn();
    }

    @Test
    public void submitNewPassword() throws Exception {

        MvcResult result = mockMvc.perform(
                post(this.authUri+"/reset-password/submit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("")
        )
                .andExpect(status().isOk()).andReturn();
    }
}
