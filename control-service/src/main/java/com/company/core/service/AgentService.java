package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentLevelDo;
import com.company.core.form.AgentForm;
import com.company.core.form.Pagination;

import java.util.List;
import java.util.Map;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface AgentService {
    
    public List<UcAgentDo> getAgentList();
    
    public List<UcAgentDo> getAgentList(String instId);
    
    public List<UcAgentLevelDo> getAgentListOfAgentOwn(String agent);
    
    public List<String> getAgentIdListOfAgentOwn(String agent);
    
    List<UcAgentDo> getAgentList(String instId, String agentType, String status);
    
    public List<String> getAgentIdList(String instId, String status);
    
    List<UcAgentDo> getAgentIdListOfAgentOwnEnabled(String agentId);
    
    public String createNewAgent(AgentForm agentForm, UserBO userBO);
    
    void createAgentBaseInfo(AgentForm agentForm, UserBO userBO);
    
    void createAgentDetailInfo(AgentForm agentForm, UserBO userBO);
    
    void createAgentFeeInfo(AgentForm agentForm, UserBO userBO);
    
    public UcAgentDo getAgent(String agentId);
    
    UcAgentLevelDo getAgentLevel(String agentId);
    
    String getAgentLevelName(String agentId);
    
    public Boolean checkIfDupAgentByName(String agentName, String agentShortName);
    
    public UcAgentDo getAgentOfInstOwn(String instId);
    
    
    UcAgentDo getDefautlAgentOfInst(String instId);
    
    Pagination getAgentListPage(AgentForm agentForm);
    
    public Map<String , String> checkAgentBefore(AgentForm agentForm, UserBO userBO);
}
