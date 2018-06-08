package requiredPreExistingRecordTest;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
import com.workspaceit.dccpos.entity.accounting.GroupAccount;
import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.service.accounting.GroupAccountService;
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
import org.springframework.web.context.WebApplicationContext;
import resetendpointTest.BaseTest;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class RequiredRecordTest extends BaseTest {
    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private FormToNameValuePair formToNameValuePair;
    private LedgerService ledgerService;
    private GroupAccountService groupAccountService;

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }
    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }
    @Autowired
    public void setGroupAccountService(GroupAccountService groupAccountService) {
        this.groupAccountService = groupAccountService;
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void ledgerTest(){
        Ledger cogsLedger = this.ledgerService.getByCode(LEDGER_CODE.COGS);
        Ledger cashLedger = this.ledgerService.getByCode(LEDGER_CODE.CASH);
        Ledger shipmentCostLedger = this.ledgerService.getByCode(LEDGER_CODE.SHIPMENT_COST);
        Ledger dueShipmentCostLedger = this.ledgerService.getByCode(LEDGER_CODE.DUE_SHIPMENT_COST);
        Ledger inventoryLedger = this.ledgerService.getByCode(LEDGER_CODE.INVENTORY);
        Ledger investmentLedger = this.ledgerService.getByCode(LEDGER_CODE.INVESTMENT);
        Ledger saleLedger = this.ledgerService.getByCode(LEDGER_CODE.SALE);
        Ledger saleDueLedger = this.ledgerService.getByCode(LEDGER_CODE.DUE_SALE);

        Assert.assertTrue("COGS Ledger not found ",cogsLedger!=null);
        Assert.assertTrue("Cash Ledger not found ",cashLedger!=null);
        Assert.assertTrue("Shipment cost Ledger not found ",shipmentCostLedger!=null);
        Assert.assertTrue("Shipment cost Due Ledger not found ",dueShipmentCostLedger!=null);
        Assert.assertTrue("Inventory Ledger not found ",inventoryLedger!=null);
        Assert.assertTrue("Investment Ledger not found ",investmentLedger!=null);
        Assert.assertTrue("Sale Ledger not found ",saleLedger!=null);
        Assert.assertTrue("Sale Due Ledger not found ",saleDueLedger!=null);


    }
    @Test
    public void groupTest(){
        for(GROUP_CODE groupCode : GROUP_CODE.values()){
            GroupAccount groupAccount =  this.groupAccountService.getByCode(groupCode);
            Assert.assertTrue(groupCode+" Group account not found ",groupAccount!=null);

        }
    }
}
