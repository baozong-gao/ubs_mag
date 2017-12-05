package com.company.core.service.impl;

import com.company.core.biz.UCFeeBiz;
import com.company.core.biz.UCUserAgentBiz;
import com.company.core.biz.UCUserInfoBiz;
import com.company.core.constant.Constant;
import com.company.core.constant.StatusConstant;
import com.company.core.constant.UserConstant;
import com.company.core.entity.*;
import com.company.core.form.AccountUserForm;
import com.company.core.form.InstForm;
import com.company.core.form.Pagination;
import com.company.core.service.AccountUserService;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    UCUserInfoBiz ucUserInfoBiz;
    @Autowired
    UCFeeBiz ucFeeBiz;
    
    @Override
    public Pagination getAccountUserListPage(AccountUserForm accountUserForm) {
        
        //如果手机号, 用户号, 姓名不为空, 则按照用户信息查找, 其余情况都按照机构信息查找
        if(StringUtils.isNotBlank(accountUserForm.getMobilePhone()) ||
                StringUtils.isNotBlank(accountUserForm.getUserId()) ||
                StringUtils.isNotBlank(accountUserForm.getUserName())){
            return getAccountUserDoListByUserInfo(accountUserForm);
        } else {
            return getAccountUserDoListByUserAgent(accountUserForm);
        }
    }
    
    
    public Pagination getAccountUserDoListByUserAgent(AccountUserForm accountUserForm){
    
        int pageCurrent = Integer.parseInt(accountUserForm.getPageCurrent());
        int pageSize = Integer.parseInt(accountUserForm.getPageSize());
    
        AccountUserDo accountUserDo = new AccountUserDo();
        UcUserInfoDo ucUserInfoDo = new UcUserInfoDo();
        List<AccountUserDo> accountUserDoList = new ArrayList<>();
    
        UcUserAgentDoExample ucUserAgentDoExample = formatUserAgentSearchCriteria(accountUserForm);
        //获取满足的记录条数
        long tranSize = ucUserAgentBiz.countByExample(ucUserAgentDoExample);
        Pagination<AccountUserDo> page = new Pagination<AccountUserDo>((int) tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<UcUserAgentDo> ucUserAgentDoList = ucUserAgentBiz.selectByExample(ucUserAgentDoExample);
        for (UcUserAgentDo ucUserAgentDo : ucUserAgentDoList) {
            accountUserDo = new AccountUserDo();
            convertToTarget(accountUserDo, ucUserAgentDo);  // userAgentInfo
            ucUserInfoDo = ucUserInfoBiz.selectByPrimaryKey(ucUserAgentDo.getUserId());
            if (ucUserInfoDo != null) {
                convertToTarget(accountUserDo, ucUserInfoDo); //userInfo
            }
            accountUserDoList.add(accountUserDo);
        }
        page.addResult(accountUserDoList);
        return page;
    }
    
    
    public Pagination getAccountUserDoListByUserInfo(AccountUserForm accountUserForm){
    
        int pageCurrent = Integer.parseInt(accountUserForm.getPageCurrent());
        int pageSize = Integer.parseInt(accountUserForm.getPageSize());
        
        UcUserInfoDoExample ucUserInfoDoExample = formatUserInfoSearchCriteria(accountUserForm);
        //获取满足的记录条数
        long tranSize = ucUserInfoBiz.countByExample(ucUserInfoDoExample);
        Pagination<AccountUserDo> page = new Pagination<AccountUserDo>((int) tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<UcUserInfoDo> ucUserInfoDoList = ucUserInfoBiz.selectByExample(ucUserInfoDoExample);
    
        AccountUserDo accountUserDo = new AccountUserDo();
        UcUserAgentDo ucUserAgentDo = new UcUserAgentDo();
        List<AccountUserDo> accountUserDoList = new ArrayList<>();
        for(UcUserInfoDo ucUserInfoDo : ucUserInfoDoList){
            accountUserDo = new AccountUserDo();
            convertToTarget(accountUserDo, ucUserInfoDo);  // userInfo
            ucUserAgentDo = ucUserAgentBiz.selectByPrimaryKey(ucUserInfoDo.getId());
            if(ucUserAgentDo != null){
                convertToTarget(accountUserDo, ucUserAgentDo); //userAgent
            }
            accountUserDoList.add(accountUserDo);
        }
        page.addResult(accountUserDoList);
        return page;
        
    }
    
    
    public UcUserAgentDoExample formatUserAgentSearchCriteria(AccountUserForm accountUserForm){
    
        UcUserAgentDoExample ucUserAgentDoExample = new UcUserAgentDoExample();
        UcUserAgentDoExample.Criteria criteria = ucUserAgentDoExample.createCriteria();
        
        //如果机构账户登录的
        if(UserConstant.USER_INST.equals(accountUserForm.getUserType())){
            if(StringUtils.isNotBlank(accountUserForm.getAgentId())){
                criteria.andAgentIdEqualTo(accountUserForm.getAgentId());
            } else {
                List<String> agentIdList = agentService.getAgentIdList(accountUserForm.getUserCode(), StatusConstant.STATUS_ENABLE);
                if(agentIdList != null && agentIdList.size() > 0){
                    criteria.andAgentIdIn(agentIdList);
                }
            }
        } else if(UserConstant.USER_AGENT.equals(accountUserForm.getUserType())){ //如果代理账户登录的
            if(StringUtils.isNotBlank(accountUserForm.getAgentId())){
                criteria.andAgentIdEqualTo(accountUserForm.getAgentId());
            }
        } else {
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
        }

        if(StringUtils.isNotBlank(accountUserForm.getStatus())){
            criteria.andStatusEqualTo(accountUserForm.getStatus());
        }
        
        return ucUserAgentDoExample;
    }
    
    public UcUserInfoDoExample formatUserInfoSearchCriteria(AccountUserForm accountUserForm){
    
        UcUserInfoDoExample ucUserInfoDoExample = new UcUserInfoDoExample();
        UcUserInfoDoExample.Criteria criteria = ucUserInfoDoExample.createCriteria();
        
        if(StringUtils.isNotBlank(accountUserForm.getUserId())){
            criteria.andIdEqualTo(accountUserForm.getUserId());
        }
        if(StringUtils.isNotBlank(accountUserForm.getUserName())){
            criteria.andUserNameLike("%" + accountUserForm.getUserName() + "%");
        }
        if(StringUtils.isNotBlank(accountUserForm.getMobilePhone())){
            criteria.andMobileEqualTo(accountUserForm.getMobilePhone());
        }
        return ucUserInfoDoExample;
    }

    
    public void convertToTarget(AccountUserDo accountUserDo, UcUserAgentDo ucUserAgentDo){
    
        accountUserDo.setAgentId(ucUserAgentDo.getAgentId());
        accountUserDo.setUserId(ucUserAgentDo.getUserId());
        accountUserDo.setStatus(ucUserAgentDo.getStatus());
        
    }
    
    public void convertToTarget(AccountUserDo accountUserDo, UcUserInfoDo ucUserInfoDo){
        
        accountUserDo.setMobile(ucUserInfoDo.getMobile());
        
        if(StringUtils.isNotBlank(ucUserInfoDo.getUserName())){
            accountUserDo.setUserName(ucUserInfoDo.getUserName());
        }
        if(StringUtils.isNotBlank(ucUserInfoDo.getNickName())){
            accountUserDo.setNickName(ucUserInfoDo.getNickName());
        }
        
    }
    
    @Override
    public void formatAccountUserFormFromFee(AccountUserForm accountUserForm) {
        
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_USER);
        ucFeeDo.setUserCode(accountUserForm.getUserId());
        
        //之后扩充时, 需要替换掉
        ucFeeDo.setCategory(Constant.CATEGORY_DEFAULT);
        ucFeeDo.setCategoryId("P001");
        ucFeeDo.setFeeType("DF");
        UcFeeDo df = ucFeeBiz.getFee(ucFeeDo);
        if(df != null){
            accountUserForm.setDefaultFeeFixed(df.getFeeMode());
        }
        
        ucFeeDo.setFeeType("DR");
        UcFeeDo dr = ucFeeBiz.getFee(ucFeeDo);
        if(dr != null){
            accountUserForm.setDefaultFeeRate(dr.getFeeMode());
        }
        
        ucFeeDo.setFeeType("EF");
        UcFeeDo ef = ucFeeBiz.getFee(ucFeeDo);
        if(ef != null){
            accountUserForm.setEffectiveFeeFixed(ef.getFeeMode());
        }
        
        ucFeeDo.setFeeType("ER");
        UcFeeDo er = ucFeeBiz.getFee(ucFeeDo);
        if(er != null){
            accountUserForm.setEffectiveFeeRate(er.getFeeMode());
        }
        
    }
    
}
