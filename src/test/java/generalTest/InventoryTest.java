package generalTest;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.constant.STOCK_STATUS;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.service.InventoryService;
import com.workspaceit.dccpos.service.ProductService;
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

import javax.annotation.PostConstruct;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class InventoryTest extends BaseTest {
    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private ProductService productService;
    private InventoryService inventoryService;
    List<Inventory> inventories;

    @PostConstruct
    public void init(){
        this.inventories =this.inventoryService.getAll();
    }

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @Transactional
    public void quantityTest(){

        if(this.inventories==null || this.inventories.size()==0)return;

        for(Inventory inventory : this.inventories ){
            int expectedQuantity = inventory.getAvailableQuantity()+inventory.getSoldQuantity();
            int purchasedQuantity = inventory.getPurchaseQuantity();
      //      Assert.assertEquals("ID : "+inventory.getId()+", Quantity mismatched ",expectedQuantity,purchasedQuantity,0);
        }

    }

    @Test
    @Transactional
    public void stockInAndSoldOutQuantity(){

        if(this.inventories==null || this.inventories.size()==0)return;

        for(Inventory inventory : this.inventories ){
            int availableQuantity = inventory.getAvailableQuantity();

            if(inventory.getStatus().equals(STOCK_STATUS.IN_STOCK)){
                Assert.assertTrue("IN STOCK Inventory Quantity = "+availableQuantity +", Expected Greater then 0 " ,
                        availableQuantity>0);
            }
            if(inventory.getStatus().equals(STOCK_STATUS.SOLD_OUT)){
                Assert.assertTrue("SOLD_OUT Inventory Quantity = "+availableQuantity +", Expected 0"
                        , availableQuantity==0);
            }
        }

    }


}
