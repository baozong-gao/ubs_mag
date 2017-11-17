package com.company.core.service.impl;

import com.company.core.biz.*;
import com.company.core.constant.*;
import com.company.core.domain.AgentBO;
import com.company.core.domain.UserBO;
import com.company.core.entity.*;
import com.company.core.form.AgentForm;
import com.company.core.form.InstForm;
import com.company.core.form.Pagination;
import com.company.core.service.AgentService;
import com.company.core.util.ComcomUtils;
import com.company.core.util.DateUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    UCFeeBiz ucFeeBiz;
    @Autowired
    UCInstBiz ucInstBiz;
    
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
    public List<UcAgentDo> getAgentIdListOfAgentOwnEnabled(String agentId) {
        
        List<UcAgentDo> agentIdList = new ArrayList<>();
        UcAgentDo ucAgentDo = null;
        List<UcAgentLevelDo> list = ucAgentLevelBiz.getAllDownAgentLevelList(agentId);
        if (list != null && list.size() > 0) {
            for (UcAgentLevelDo s : list) {
                ucAgentDo = ucAgentBiz.selectAgent(agentId);
                if(StatusConstant.STATUS_ENABLE.equals(ucAgentDo.getStatus())){
                    agentIdList.add(ucAgentDo);
                }
            }
        }
        return agentIdList;
    }
    
    
    @Override
    public Map<String , String> checkAgentBefore(AgentForm agentForm, UserBO userBO) {
    
        Map<String , String> result = new HashMap<>();
    
        //检查机构信息
        UcInstDo ucInstDo = ucInstBiz.selectByPrimaryKey(agentForm.getInstId());
        if(ucInstDo == null){
            result.put("error", "机构不存在");
        }
        if(!StatusConstant.STATUS_ENABLE.equals(ucInstDo.getStatus())){
            result.put("error", "机构非激活状态不允许新增代理");
        }
        if(!"Y".equals(ucInstDo.getAgentOk())){
            result.put("error", "机构不允许新增代理");
        }
    
        //检查费率信息 - 代理的默认费率不能低于机构
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_INST);
        ucFeeDo.setUserCode(agentForm.getInstId());
        ucFeeDo.setCategory(agentForm.getCategory());
        ucFeeDo.setCategoryId(agentForm.getCategoryId());
        ucFeeDo.setFeeType("DF");
        UcFeeDo df = ucFeeBiz.getFee(ucFeeDo);
        if(df != null && !ucFeeBiz.zeroFee(df.getFeeMode())){
            //非空, 非0机构默认固定费用
            int i = ucFeeBiz.compareFees(agentForm.getDefaultFeeFixed(), df.getFeeMode());
            if(i < 0){
                result.put("error", "代理默认固定费用低于机构");
            }
        }
        ucFeeDo.setFeeType("DR");
        UcFeeDo dr = ucFeeBiz.getFee(ucFeeDo);
        if(dr != null && !ucFeeBiz.zeroFee(dr.getFeeMode())){
            //非空, 非0机构默认费率
            int i = ucFeeBiz.compareFees(agentForm.getDefaultFeeRate(), dr.getFeeMode());
            if(i < 0){
                result.put("error", "代理默认费率低于机构");
            }
        }
    
        //代理的实收费率不能低于默认费率
        int a = ucFeeBiz.compareFees(agentForm.getEffectiveFeeFixed(), agentForm.getDefaultFeeFixed());
        if(a < 0){
            result.put("error", "代理实收固定费用低于默认固定费用");
        }
        int b = ucFeeBiz.compareFees(agentForm.getEffectiveFeeRate(), agentForm.getDefaultFeeRate());
        if(b < 0){
            result.put("error", "代理实收费率低于默认费率");
        }

        return result;
    }
    
    
    @Override
    public String createNewAgent(AgentForm agentForm, UserBO userBO) {
        
        //创建代理基础信息
        this.createAgentBaseInfo(agentForm, userBO);
        
        //创建代理详细信息
        this.createAgentDetailInfo(agentForm, userBO);
        
        //创建代理费率
        this.createAgentFeeInfo(agentForm, userBO);
        
        return agentForm.getAgentId();
        
    }
    
    @Override
    public void createAgentBaseInfo(AgentForm agentForm, UserBO userBO) {
        
        //创建代理
        UcAgentDo ucAgentDo = new UcAgentDo();
        ucAgentDo.setInstId(agentForm.getInstId());
        ucAgentDo.setAgentId(sequenceBiz.genAgentId());
    
        //机构发展的代理商类型为1,  机构自身的代理商号是0,
        if(UserConstant.USER_TYPE_DEFAULT.equals(agentForm.getAgentType()) || UserConstant.USER_TYPE_AGENT.equals(agentForm.getAgentType())){
            //机构默认代理, 直接激活
            ucAgentDo.setAgentType(UserConstant.USER_TYPE_DEFAULT);
            ucAgentDo.setStatus(StatusConstant.STATUS_ENABLE);
        }else if(UserConstant.USER_TYPE_AGENT.equals(agentForm.getAgentType())) {
            ucAgentDo.setAgentType(UserConstant.USER_TYPE_AGENT);
            ucAgentDo.setStatus(StatusConstant.STATUS_NEW);
        }
        ucAgentDo.setAgentName(agentForm.getAgentName());
        ucAgentDo.setCategory(agentForm.getCategory());
        ucAgentDo.setCategoryId(agentForm.getCategoryId());
        ucAgentDo.setAgentOk(agentForm.getAgentOk());
        BigDecimal bigDecimal = new BigDecimal(agentForm.getAgentCountLimit());
        ucAgentDo.setAgentCountLimit(bigDecimal);
        ucAgentDo.setLimitArea(agentForm.getLimitArea());
        ucAgentDo.setLimitAreaCode(agentForm.getLimitAreaCode());
        
        ucAgentDo.setCreateUser(userBO.getUsrName());
        ucAgentDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentDo.setCreateTime(DateUtil.getCurrentDateTime());
        
        ucAgentDo.setLockVersion(String.valueOf(0));
        ucAgentDo.setModifyUser(userBO.getUsrName());
        ucAgentDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentDo.setModifyTime(DateUtil.getCurrentDateTime());
        
        int i = ucAgentBiz.insertAgent(ucAgentDo);
        if (i <= 0) {
            throw new ErrorException("代理信息入库失败");
        }
        
        agentForm.setAgentId(ucAgentDo.getAgentId());
        
    }
    
    @Override
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
        ucAgentInfoDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentInfoDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucAgentInfoDo.setModifyUser(userBO.getUsrName());
        ucAgentInfoDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucAgentInfoDo.setModifyTime(DateUtil.getCurrentDateTime());
        ucAgentInfoDo.setLockVersion(String.valueOf(0));
        
        int c = ucAgentBiz.insertAgentInfo(ucAgentInfoDo);
        if (c <= 0) {
            throw new ErrorException("代理详细信息入库失败");
        }
    }
    
    @Override
    public void createAgentFeeInfo(AgentForm agentForm, UserBO userBO) {
        
        //存入机构费率
        UcFeeDo ucFeeDo = new UcFeeDo();
        ucFeeDo.setUserType(UserConstant.USER_AGENT);
        ucFeeDo.setUserCode(agentForm.getInstId());
    
        if(StringUtils.isNotBlank(agentForm.getCategory())){
            ucFeeDo.setCategory(agentForm.getCategory());
        } else {
            ucFeeDo.setCategory(SystemConstant.DEFAULT_CATEGORY);  //默认是1
        }
        if(StringUtils.isNotBlank(agentForm.getCategoryId())){
            ucFeeDo.setCategoryId(agentForm.getCategoryId());
        } else {
            ucFeeDo.setCategoryId(SystemConstant.DEFAULT_CATEGORY_ID);  //默认是P001
        }
        ucFeeDo.setStatus(StatusConstant.STATUS_ENABLE);
        ucFeeDo.setPercentFlag("N");
    
        ucFeeDo.setCreateUser(userBO.getUsrName());
        ucFeeDo.setCreateSource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucFeeDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucFeeDo.setModifyUser(userBO.getUsrName());
        ucFeeDo.setModifySource(SystemConstant.DEFAULT_SOURCE_CODE);
        ucFeeDo.setModifyTime(DateUtil.getCurrentDateTime());
        ucFeeDo.setLockedVersion(String.valueOf(0));
    
        //默认固定单笔
        if(StringUtils.isNotBlank(agentForm.getDefaultFeeFixed())){
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_FIXED_DESC);
            ucFeeDo.setFeeMode(agentForm.getDefaultFeeFixed());
            int i = ucFeeBiz.insertFee(ucFeeDo);
            if(i <=0 ){
                throw new ErrorException("数据新增失败");
            }
        }
        //默认固定比例
        if(StringUtils.isNotBlank(agentForm.getDefaultFeeRate())){
            ucFeeDo.setFeeType(Constant.FEE_DEFAULT_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_DEFAULT_RATE_DESC);
            ucFeeDo.setFeeMode(agentForm.getDefaultFeeRate());
            int c = ucFeeBiz.insertFee(ucFeeDo);
            if(c <=0 ){
                throw new ErrorException("数据新增失败");
            }
        }
        //实收固定单笔
        if(StringUtils.isNotBlank(agentForm.getEffectiveFeeFixed())){
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_FIXED);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_FIXED_DESC);
            ucFeeDo.setFeeMode(agentForm.getEffectiveFeeFixed());
            int d = ucFeeBiz.insertFee(ucFeeDo);
            if(d <=0 ){
                throw new ErrorException("数据新增失败");
            }
        }
        //实收固定比例
        if(StringUtils.isNotBlank(agentForm.getEffectiveFeeRate())){
            ucFeeDo.setFeeType(Constant.FEE_EFFECTIVE_RATE);
            ucFeeDo.setFeeDesc(Constant.FEE_EFFECTIVE_RATE_DESC);
            ucFeeDo.setFeeMode(agentForm.getEffectiveFeeRate());
            int f = ucFeeBiz.insertFee(ucFeeDo);
            if(f <=0 ){
                throw new ErrorException("数据新增失败");
            }
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
    
    @Override
    public UcAgentDo getDefautlAgentOfInst(String instId) {
    
        UcAgentDo ucAgentDos = ucAgentBiz.selectDefaultAgent(instId);
        if (ucAgentDos != null) {
            return ucAgentDos;
        }
        return null;
    }
    
    @Override
    public Pagination getAgentListPage(AgentForm agentForm) {
        
        int pageCurrent = Integer.parseInt(agentForm.getPageCurrent());
        int pageSize = Integer.parseInt(agentForm.getPageSize());
        UcAgentDoExample ucagentDoExample = formatAgentSearchCriteria(agentForm);
        
        //获取满足的记录条数
        int tranSize = ucAgentBiz.countByExample(ucagentDoExample);
        Pagination<AgentBO> page = new Pagination<AgentBO>(tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<UcAgentDo> ucagentDos = ucAgentBiz.selectByExample(ucagentDoExample);
        List<AgentBO> list = new ArrayList<>();
        AgentBO agentBO = null;
        if(ucagentDos != null && ucagentDos.size() > 0){
            for(UcAgentDo ur: ucagentDos){
                agentBO = new AgentBO();
                agentBO.setInstId(ur.getInstId());
                agentBO.setAgentId(ur.getAgentId());
                agentBO.setAgentName(ur.getAgentName());
                agentBO.setStatus(ur.getStatus());
                agentBO.setCreateUser(ur.getCreateUser());
                agentBO.setCreateTime(ur.getCreateTime());
                list.add(agentBO);
            }
        }
        page.addResult(list);
        return page;
    }
    
    public UcAgentDoExample formatAgentSearchCriteria(AgentForm agentForm){
    
        UcAgentDoExample ucAgentDoExample = new UcAgentDoExample();
        UcAgentDoExample.Criteria criteria = ucAgentDoExample.createCriteria();
        
        if(StringUtils.isNotBlank(agentForm.getInstId())){
            criteria.andInstIdEqualTo(agentForm.getInstId());
        }
        if(StringUtils.isNotBlank(agentForm.getAgentId())){
            criteria.andAgentIdEqualTo(agentForm.getAgentId());
        }
        if(StringUtils.isNotBlank(agentForm.getAgentName())){
            criteria.andAgentNameLike("%" + agentForm.getAgentName() + "%");
        }
        if(StringUtils.isNotBlank(agentForm.getStatus())){
            criteria.andStatusEqualTo(agentForm.getStatus());
        }
        return ucAgentDoExample;
    }
    
}
