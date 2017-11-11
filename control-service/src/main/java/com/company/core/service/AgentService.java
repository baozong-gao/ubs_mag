package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.form.AgentForm;

import java.util.List;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface AgentService {
    
    public List<UcAgentDo> getAgentList();
    
    public List<UcAgentDo> getAgentList(String instId);
    
    public String createNewAgent(AgentForm agentForm, UserBO userBO);
    
    public UcAgentDo getAgent(String agentId);
    
    public Boolean checkIfDupAgentByName(String agentName, String agentShortName);
}
