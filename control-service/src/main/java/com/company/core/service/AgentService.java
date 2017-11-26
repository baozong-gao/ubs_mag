package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentInfoDo;
import com.company.core.entity.UcAgentLevelDo;
import com.company.core.form.AgentForm;
import com.company.core.form.Pagination;
import org.springframework.transaction.annotation.Transactional;

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
    
    //为下拉框重新定义返回
    List<UcAgentDo> getAgentListForDropDown(String instId, String agentType, String status);
    
    //为下拉框重新定义返回
    List<UcAgentDo> getAgentListForDropDown(String instId, String agentType, String status, String level);
    
    public List<String> getAgentIdList(String instId, String status);
    
    List<UcAgentDo> getAgentIdListOfAgentOwnEnabled(String agentId);
    
    public String createNewAgent(AgentForm agentForm, UserBO userBO);
    
    void createAgentBaseInfo(AgentForm agentForm, UserBO userBO);
    
    void createAgentDetailInfo(AgentForm agentForm, UserBO userBO);
    
    void createAgentFeeInfo(AgentForm agentForm, UserBO userBO);
    
    void createAgentLevelInfo(AgentForm agentForm, UserBO userBO);
    
    void formatAgentFormFromAgent(AgentForm agentForm);
    
    void formatAgentFormFromAgentInfo(AgentForm agentForm);
    
    void formatAgentFormFromFee(AgentForm agentForm);
    
    public UcAgentDo getAgent(String agentId);
    
    UcAgentInfoDo getAgentInfo(String agentId);
    
    UcAgentLevelDo getAgentLevel(String agentId);
    
    String getAgentLevelName(String agentId);
    
    public Boolean checkIfDupAgentByName(String agentName, String agentShortName);
    
    public UcAgentDo getAgentOfInstOwn(String instId);
    
    
    UcAgentDo getDefautlAgentOfInst(String instId);
    
    Pagination getAgentListPage(AgentForm agentForm);
    
    List<UcAgentDo> getAgentListOfAgentOwn(String agentId, String status);
    
    public Map<String , String> checkAgentBefore(AgentForm agentForm, UserBO userBO);
    
    void updateAgent(AgentForm agentForm, UserBO userBO);
    
    void updateAgentBaseInfo(AgentForm agentForm, UserBO userBO);
    
    void updateAgentDetailInfo(AgentForm agentForm, UserBO userBO);
    
    void updateAgentFeeInfo(AgentForm agentForm, UserBO userBO);
    
    @Transactional
    void activateAgent(String agentId, UserBO userBO) throws Exception;
    
    @Transactional
    void cancelAgent(String agentId, UserBO userBO) throws Exception;
    
    @Transactional
    void disableAgent(String agentId, UserBO userBO) throws Exception;
    
    String checkFees(AgentForm agentForm);
    
    String checkFeesAgentOpen(AgentForm agentForm);
    
    @Transactional
    void createAgentAcct(UcAgentDo ucAgentDo, UserBO shiroUserBo);
}
