package com.company.core.service.impl;

import com.company.core.biz.SequenceBiz;
import com.company.core.biz.UCAgentBiz;
import com.company.core.biz.UCAgentLevelBiz;
import com.company.core.constant.ErrorException;
import com.company.core.constant.StatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentInfoDo;
import com.company.core.entity.UcAgentLevelDo;
import com.company.core.form.AgentForm;
import com.company.core.service.AgentService;
import com.company.core.util.ComcomUtils;
import com.company.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("AgentService")
public class AgentServiceImpl implements AgentService {
    
    @Autowired
    UCAgentBiz ucAgentBiz;
    @Autowired
    SequenceBiz sequenceBiz;
    @Autowired
    UCAgentLevelBiz ucAgentLevelBiz;
    
    @Override
    public List<UcAgentDo> getAgentList() {
        
        return ucAgentBiz.selectAllAgents();
        
    }
    
    @Override
    public List<UcAgentDo> getAgentList(String instId) {
        
        return ucAgentBiz.selectAgentsByInstId(instId);
        
    }
    
    @Override
    public List<UcAgentDo> getAgentList(String instId, String agentType, String status) {
        
        return ucAgentBiz.selectAllAgents(instId, agentType, status, "");
        
    }
    
    @Override
    public List<String> getAgentIdList(String instId, String status) {
        
        List<String> agentIdList = new ArrayList<>();
        List<UcAgentDo> ucAgentDos = ucAgentBiz.selectAgentsByInstId(instId);
        if (ucAgentDos != null && ucAgentDos.size() > 0) {
            for (UcAgentDo uc : ucAgentDos) {
                if (StringUtils.isNotBlank(status)) {
                    if (status.equals(uc.getStatus())) {
                        agentIdList.add(uc.getAgentId());
                    } else {
                        continue;
                    }
                } else {
                    agentIdList.add(uc.getAgentId());
                }
            }
        }
        return agentIdList;
    }
    
    @Override
    public List<UcAgentLevelDo> getAgentListOfAgentOwn(String agent) {
        
        return ucAgentLevelBiz.getAllDownAgentLevelList(agent);
        
    }
    
    ;
    
    
    @Override
    public List<String> getAgentIdListOfAgentOwn(String agentId) {
        
        List<String> agentIdList = new ArrayList<>();
        List<UcAgentLevelDo> list = ucAgentLevelBiz.getAllDownAgentLevelList(agentId);
        if (list != null && list.size() > 0) {
            for (UcAgentLevelDo s : list) {
                agentIdList.add(s.getAgentId());
            }
        }
        return agentIdList;
    }
    
    
    @Override
    public String createNewAgent(AgentForm agentForm, UserBO userBO) {
        
        //创建代理基础信息
        this.createAgentBaseInfo(agentForm, userBO);
        
        //创建代理详细信息
        this.createAgentDetailInfo(agentForm, userBO);
        
        return agentForm.getAgentId();
        
    }
    
    public void createAgentBaseInfo(AgentForm agentForm, UserBO userBO) {
        
        //创建代理
        UcAgentDo ucAgentDo = new UcAgentDo();
        ucAgentDo.setInstId(agentForm.getInstId());
        ucAgentDo.setAgentId(sequenceBiz.genAgentId());
        //ucAgentDo.setAgentType(agentForm.getAgentType());
        ucAgentDo.setAgentType("1");   //机构发展的代理商类型为1,  机构自身的代理商号是0
        ucAgentDo.setAgentName(agentForm.getAgentName());
        ucAgentDo.setStatus(StatusConstant.STATUS_NEW);
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
        if (i <= 0) {
            throw new ErrorException("代理信息入库失败");
        }
        
        agentForm.setAgentId(ucAgentDo.getAgentId());
        
    }
    
    public void createAgentDetailInfo(AgentForm agentForm, UserBO userBO) {
        
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
        if (c <= 0) {
            throw new ErrorException("代理详细信息入库失败");
        }
    }
    
    @Override
    public UcAgentDo getAgent(String agentId) {
        return ucAgentBiz.selectAgent(agentId);
    }
    
    
    @Override
    public UcAgentLevelDo getAgentLevel(String agentId) {
        
        return ucAgentLevelBiz.getAgentLevel(agentId);
        
    }
    
    @Override
    public String getAgentLevelName(String agentId) {
        
        String levelName = "";
        UcAgentLevelDo ucAgentLevelDo = ucAgentLevelBiz.getAgentLevel(agentId);
        if (ucAgentLevelDo == null) {
            return "1级: ";
        } else {
            return ucAgentLevelDo.getAgentLevel() + "级: ";
        }
    }
    
    @Override
    public Boolean checkIfDupAgentByName(String agentName, String agentShortName) {
        
        if (StringUtils.isNotBlank(agentName)) {
            List<UcAgentInfoDo> ucAgentInfoDos = ucAgentBiz.selectByName(agentName);
            if (ucAgentInfoDos != null && ucAgentInfoDos.size() > 0) {
                return true;
            }
        }
        
        if (StringUtils.isNotBlank(agentShortName)) {
            List<UcAgentInfoDo> ucAgentInfoDos = ucAgentBiz.selectByShortName(agentName);
            if (ucAgentInfoDos != null && ucAgentInfoDos.size() > 0) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public UcAgentDo getAgentOfInstOwn(String instId) {
        
        List<UcAgentDo> ucAgentDos = ucAgentBiz.selectAgentsByInstId(instId);
        if (ucAgentDos == null || ucAgentDos.size() == 0) {
            return null;
        }
        return ucAgentDos.get(0);
    }
    
}
