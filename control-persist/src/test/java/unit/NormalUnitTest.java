package unit;

import com.company.core.entity.AccountUserDo;
import com.company.core.mapper.AccountUserSelfDefineMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * Lambert 2017-06-07
 */
public class NormalUnitTest {
    
    @Autowired
    AccountUserSelfDefineMapper accountUserSelfDefineMapper;
    
//    @Before
//    public void setUp() {
//        accountUserSelfDefineMapper = new AccountUserSelfDefineMapper();
//    }
    
    @Test
    @Rollback(false)
    public void testMybatis() throws Exception {
    
        AccountUserDo accountUserDo = new AccountUserDo();
        accountUserDo.setAgentId("000082");
        
        accountUserSelfDefineMapper.selectUsersByAgent(accountUserDo);
    }
    
}
