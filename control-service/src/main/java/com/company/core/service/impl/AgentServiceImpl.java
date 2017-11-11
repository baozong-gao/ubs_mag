package com.company.core.service.impl;

import com.company.core.biz.SequenceBiz;
import com.company.core.biz.UCAgentBiz;
import com.company.core.constant.ErrorException;
import com.company.core.constant.UserStatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentInfoDo;
import com.company.core.entity.UcInstInfoDo;
import com.company.core.form.AgentForm;
import com.company.core.service.AgentService;
import com.company.core.util.ComcomUtils;
import com.company.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("AgentService")
public class AgentServiceImpl implements AgentService {
    
    @Autowired
    UCAgentBiz ucAgentBiz;
    @Autowired
    SequenceBiz sequenceBiz;
    
    @Override
    public List<UcAgentDo> getAgentList() {
    
        return ucAgentBiz.selectAllAgents();
        
    }
    
    @Override
    public List<UcAgentDo> getAgentList(String instId) {
        
        return ucAgentBiz.selectAgentsByInstId(instId);
        
    }
    
    @Override
    public String createNewAgent(AgentForm agentForm, UserBO userBO) {
        
        //创建代理基础信息
        this.createAgentBaseInfo(agentForm, userBO);
        
        //创建代理详细信息
        this.createAgentDetailInfo(agentForm, userBO);
        
        return agentForm.getAgentId();
        
    }
    
    public void createAgentBaseInfo(AgentForm agentForm, UserBO userBO){
        
        //创建代理
        UcAgentDo ucAgentDo = new UcAgentDo();
        ucAgentDo.setInstId(agentForm.getInstId());
        ucAgentDo.setAgentId(sequenceBiz.genAgentId());
        ucAgentDo.setAgentType(agentForm.getAgentType());
        ucAgentDo.setAgentName(agentForm.getAgentName());
        ucAgentDo.setStatus(UserStatusConstant.STATUS_NEW);
        ucAgentDo.setCategory(agentForm.getCategory());
        ucAgentDo.setCategoryId(agentForm.getCategoryId());
        ucAgentDo.setAgentOk(agentForm.getAgentOk());
        BigDecimal bigDecimal = new BigDecimal(agentForm.getAgentCountLimit());
        ucAgentDo.setAgentCountLimit(bigDecimal);
        ucAgentDo.setLimitArea(agentForm.getLimitArea());
        ucAgentDo.setLimitAreaCode(agentForm.getLimitAreaCode());
        ucAgentDo.setCreateUser(userBO.getUsrName());
        ucAgentDo.setCreateSource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucAgentDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucAgentDo.setLockVersion(String.valueOf(0));
        ucAgentDo.setModifyUser(userBO.getUsrName());
        ucAgentDo.setModifySource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucAgentDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int i = ucAgentBiz.insertAgent(ucAgentDo);
        if(i <= 0){
            throw new ErrorException("代理信息入库失败");
        }
        
        agentForm.setAgentId(ucAgentDo.getAgentId());
        
    }
    
    public void createAgentDetailInfo(AgentForm agentForm, UserBO userBO){
        
        //存入代理详细信息
        UcAgentInfoDo ucAgentInfoDo = new UcAgentInfoDo();
        ucAgentInfoDo.setAgentId(agentForm.getAgentId());
        ucAgentInfoDo.setAgentName(agentForm.getAgentName());
        ucAgentInfoDo.setAgentShortName(agentForm.getAgentShortName());
        ucAgentInfoDo.setBusinessLicense(agentForm.getBusinessLicense());
        ucAgentInfoDo.setLegalPersonName(agentForm.getLegalPersonName());
        ucAgentInfoDo.setLegalPersonIdType(agentForm.getLegalPersonIdType());
        ucAgentInfoDo.setLegalPersonCertId(agentForm.getLegalPersonId());
        ucAgentInfoDo.setLegalPersonPhone(agentForm.getLegalPersonPhone());
        ucAgentInfoDo.setLegalPersonMail(agentForm.getLegalPersonMail());
        ucAgentInfoDo.setLegalPersonAddress(agentForm.getLegalPersonAddress());
        ucAgentInfoDo.setContactName(agentForm.getContactName());
        ucAgentInfoDo.setContactIdType(agentForm.getContactIdType());
        ucAgentInfoDo.setContactCertId(agentForm.getContactCertId());
        ucAgentInfoDo.setContactPhone(agentForm.getContactPhone());
        ucAgentInfoDo.setContactMail(agentForm.getContactMail());
        ucAgentInfoDo.setContactAddress(agentForm.getContactAddress());
        
        ucAgentInfoDo.setCreateUser(userBO.getUsrName());
        ucAgentInfoDo.setCreateSource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucAgentInfoDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucAgentInfoDo.setModifyUser(userBO.getUsrName());
        ucAgentInfoDo.setModifySource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucAgentInfoDo.setModifyTime(DateUtil.getCurrentDateTime());
        ucAgentInfoDo.setLockVersion(String.valueOf(0));
        
        int c = ucAgentBiz.insertAgentInfo(ucAgentInfoDo);
        if(c <= 0){
            throw new ErrorException("代理详细信息入库失败");
        }
    }
    
    @Override
    public UcAgentDo getAgent(String agentId) {
        return ucAgentBiz.selectAgent(agentId);
    }
    
    @Override
    public Boolean checkIfDupAgentByName(String agentName, String agentShortName ) {
        
        if(StringUtils.isNotBlank(agentName)){
            List<UcAgentInfoDo> ucAgentInfoDos = ucAgentBiz.selectByName(agentName);
            if(ucAgentInfoDos !=null && ucAgentInfoDos.size() >0){
                return true;
            }
        }
        
        if(StringUtils.isNotBlank(agentShortName)){
            List<UcAgentInfoDo> ucAgentInfoDos = ucAgentBiz.selectByShortName(agentName);
            if(ucAgentInfoDos !=null && ucAgentInfoDos.size() >0){
                return true;
            }
        }
        return false;
    }
    
}
