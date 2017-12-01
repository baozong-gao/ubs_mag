package com.company.core.service.impl;

import com.company.core.biz.UCUserAgentBiz;
import com.company.core.biz.WZExeOrderBiz;
import com.company.core.biz.WZInfoBiz;
import com.company.core.constant.StatusConstant;
import com.company.core.entity.*;
import com.company.core.form.AccountUserForm;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.service.AccountUserService;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.company.core.service.WZService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("AccountUserService")
@Slf4j
public class AccountUserServiceImpl implements AccountUserService {
    
    @Autowired
    UCUserAgentBiz ucUserAgentBiz;
    @Autowired
    AgentService agentService;
    @Autowired
    InstService instService;
    
    @Override
    public Pagination getAccountUserListPage(AccountUserForm accountUserForm) {
        
        int pageCurrent = Integer.parseInt(accountUserForm.getPageCurrent());
        int pageSize = Integer.parseInt(accountUserForm.getPageSize());
        UcUserAgentDoExample ucUserAgentDoExample = formatInstSearchCriteria(accountUserForm);
        
        //获取满足的记录条数
        long tranSize = ucUserAgentBiz.countByExample(ucUserAgentDoExample);
        Pagination<UcUserAgentDo> page = new Pagination<UcUserAgentDo>((int) tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<UcUserAgentDo> ucUserAgentDoList = ucUserAgentBiz.selectByExample(ucUserAgentDoExample);

        page.addResult(ucUserAgentDoList);
        return page;
    }
    
    
    public UcUserAgentDoExample formatInstSearchCriteria(AccountUserForm accountUserForm){
    
        UcUserAgentDoExample ucUserAgentDoExample = new UcUserAgentDoExample();
        UcUserAgentDoExample.Criteria criteria = ucUserAgentDoExample.createCriteria();
        
        if(StringUtils.isNotBlank(accountUserForm.getUserId())){
            criteria.andUserIdEqualTo(accountUserForm.getUserId());
        }
        if(StringUtils.isNotBlank(accountUserForm.getInstId())){
            List<String> agentIdList = agentService.getAgentIdList(accountUserForm.getInstId(), StatusConstant.STATUS_ENABLE);
            if(agentIdList != null && agentIdList.size() > 0){
                criteria.andAgentIdIn(agentIdList);
            }
        } else {
            if(StringUtils.isNotBlank(accountUserForm.getAgentId())){
                criteria.andAgentIdEqualTo(accountUserForm.getAgentId());
            }
        }
        if(StringUtils.isNotBlank(accountUserForm.getStatus())){
            criteria.andStatusEqualTo(accountUserForm.getStatus());
        }
        
        return ucUserAgentDoExample;
    }
    
}
