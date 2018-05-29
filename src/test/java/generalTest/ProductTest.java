package generalTest;

import com.workspaceit.dccpos.config.WebConfig;
import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.constant.STOCK_STATUS;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.service.InventoryService;
import com.workspaceit.dccpos.service.ProductService;
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
public class ProductTest extends BaseTest {
    private MockMvc mockMvc;
    private WebApplicationContext wac;
    private ProductService productService;
    private InventoryService inventoryService;

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
    public void costTest(){

        List<Product> products = this.productService.getAll();
        for(Product product : products ){
            List<Inventory> inventories =   this.inventoryService.getInStockByProductId(product.getId());
            if(inventories==null || inventories.size()==0)continue;


            double maxSellingPrice = this.inventoryService.getMaxSellingPrice(inventories);
            double minSellingPrice = this.inventoryService.getMinSellingPrice(inventories);
            double goodQuantity = this.inventoryService.getAvailableQuantity(inventories, PRODUCT_CONDITION.GOOD);
            double damagedQuantity = this.inventoryService.getAvailableQuantity(inventories, PRODUCT_CONDITION.DAMAGED);

            Assert.assertEquals(product.getName()+" Total cost min selling price ",minSellingPrice,product.getMinPrice(),0);
            Assert.assertEquals(product.getName()+" Total cost max selling price ",maxSellingPrice,product.getMaxPrice(),0);
            Assert.assertEquals(product.getName()+" Total good quantity ",goodQuantity,product.getGoodQuantity(),0);
            Assert.assertEquals(product.getName()+" Total damage quantity ",damagedQuantity,product.getDamagedQuantity(),0);

            for(Inventory inventory : inventories ){
                int expectedQuantity = inventory.getAvailableQuantity()+inventory.getSoldQuantity();;
                int purchasedQuantity = inventory.getPurchaseQuantity();
                int availableQuantity = inventory.getPurchaseQuantity();

                Assert.assertTrue("IN STOCK Inventory Quantity " , availableQuantity>0);

                Assert.assertEquals(product.getName()+" Quantity mismatched ",expectedQuantity,purchasedQuantity,0);



            }

        }

    }

}
