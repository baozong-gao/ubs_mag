package com.company.core.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Lambert 2017-06-07
 */
public class NormalUnitTest {
    
    private AccountUserSelfDefineBiz accountUserSelfDefineBiz;
    
    @Before
    public void setUp() {
        accountUserSelfDefineBiz = new AccountUserSelfDefineBiz();
    }
    
    @Test
    @Rollback(false)
    public void testMybatis() throws Exception {
        accountUserSelfDefineBiz.
    }
    
}
