package generalTest;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.entity.Employee;
import com.workspaceit.dccpos.entity.PersonalInformation;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.service.EmployeeService;
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
public class EmployeeTest extends BaseTest{

    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private FormToNameValuePair formToNameValuePair;
    private EmployeeService employeeService;
    private LedgerService ledgerService;


    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
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


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void employeeLedgerTest() throws Exception {
        List<Employee> employees =  this.employeeService.getAll();
        if(employees==null)return;
        for(Employee employee:employees){
            PersonalInformation personalInformation =  employee.getPersonalInformation();
            Assert.assertTrue("Emp Id : "+employee.getEmployeeId()+". Employee has no personal info ",personalInformation!=null);

            Ledger  ledger = this.ledgerService.getByPersonalInfoId(employee.getPersonalInformation().getId());
            Assert.assertTrue("Emp Id : "+employee.getEmployeeId()+". Employee has no salary ledger account ",ledger!=null);

        }
    }
}