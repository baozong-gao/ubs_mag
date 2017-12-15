package unit;

import com.company.core.biz.AccountUserSelfDefineBiz;
import com.company.core.biz.WZInfoBiz;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * Lambert 2017-06-07
 */
@ContextConfiguration(locations = {"classpath*:applicationContext.xml", "classpath*:control-servlet.xml"})
public class NormalUnitTest {

    private AccountUserSelfDefineBiz accountUserSelfDefineBiz;
    
    @Autowired
    WZInfoBiz wzInfoBiz;

    @Before
    public void setUp(){
        accountUserSelfDefineBiz = new AccountUserSelfDefineBiz();
        wzInfoBiz = new WZInfoBiz();
    }

    @Test
    @Rollback(false)
    public void testMybatis() throws Exception {
    
        wzInfoBiz.selectByPrimaryKey("0000021");
        
        accountUserSelfDefineBiz.getAccountUser();
    }

}
