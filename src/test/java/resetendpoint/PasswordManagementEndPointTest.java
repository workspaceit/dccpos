package resetendpoint;
import com.workspaceit.dccpos.config.WebConfig;

import com.workspaceit.dccpos.helper.FormToNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class PasswordManagementEndPointTest extends BaseTest {
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
                post(this.publicUri+"/reset-password/request-new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("email", "")
                        ))))
                ).andExpect(status().isUnprocessableEntity()).andReturn();
    }

    @Test
    public void submitNewPassword() throws Exception {

        MvcResult result = mockMvc.perform(
                post(this.publicUri+"/reset-password/submit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("")).andExpect(status().isUnprocessableEntity()).andReturn();
    }
}
