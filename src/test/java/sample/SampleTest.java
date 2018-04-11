package sample;


import com.workspaceit.pos.config.WebConfig;
import com.workspaceit.pos.service.AuthCredentialService;
import com.workspaceit.pos.service.accounting.LedgerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by mi_rafi on 1/4/18.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class SampleTest {



    @Autowired
    LedgerService ledgerService;
    @Autowired
    AuthCredentialService authCredentialService;





    @Test
    public void test(){

   }


}
