package serviceTest;

import com.workspaceit.dccpos.config.WebConfig;
import org.junit.Assert;
import com.workspaceit.dccpos.entity.Shipment;
import com.workspaceit.dccpos.helper.FormToNameValuePair;
import com.workspaceit.dccpos.service.ShipmentService;
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
import resetendpointTest.BaseTest;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class ShipmentServiceTest extends BaseTest {
    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private FormToNameValuePair formToNameValuePair;
    private ShipmentService shipmentService;

    private LedgerService ledgerService;
    private List<Shipment> shipments;

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
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /**
     * Fetch all shipment along with lazy elements
     * */
    @PostConstruct
    public void fetchAllShipments(){
        this.shipments =  new ArrayList<>();

        long maxId = this.shipmentService.getMaxId();
        long minId = this.shipmentService.getMinId();
        for(long i=minId;i<=maxId;i++){
          Shipment shipment =  this.shipmentService.getById(i);

          if(shipment!=null)shipments.add(shipment);
        }
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @Transactional
    public void costTest(){
        for(  Shipment shipment :this.shipments){
            double totalCost = shipment.getTotalCost();
            double sumOfAllCost =  this.shipmentService.getTotalCost(shipment.getCosts());
            System.out.println(shipment.getTrackingId()+" Total cost miss matched "+totalCost+" "+sumOfAllCost);
            Assert.assertEquals(shipment.getTrackingId()+" Total cost miss matched",totalCost,sumOfAllCost,0);
        }

    }

}
