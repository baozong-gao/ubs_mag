package com.company.core.biz;

import com.company.core.entity.AccountUserDo;
import com.company.core.entity.WzInfoDo;
import com.company.core.entity.WzInfoDoExample;
import com.company.core.mapper.AccountUserSelfDefineMapper;
import com.company.core.mapper.WzInfoDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class AccountUserSelfDefineBiz {

    @Autowired
    AccountUserSelfDefineMapper accountUserSelfDefineMapper;
    
    
    public List<AccountUserDo> getAccountUser(){
    
        AccountUserDo accountUserDo = new AccountUserDo();
        
        List<AccountUserDo> accountUserDoList = accountUserSelfDefineMapper.selectUsersByAgent(accountUserDo);
        
        return accountUserDoList;
        
    }

    public static void main(String args[]){
    
        AccountUserSelfDefineBiz accountUserSelfDefineBiz = new AccountUserSelfDefineBiz();
    
        List<AccountUserDo> accountUserDoList = accountUserSelfDefineBiz.getAccountUser();
        
    }
    

}
