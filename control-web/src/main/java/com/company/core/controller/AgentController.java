package com.company.core.controller;

import com.company.core.constant.ErrorException;
import com.company.core.constant.StatusConstant;
import com.company.core.constant.UserConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentLevelDo;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcProdDo;
import com.company.core.form.AgentForm;
import com.company.core.form.InstForm;
import com.company.core.form.Pagination;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.company.core.service.ProdCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping (value = "/agent")
public class AgentController extends BaseController {
    
    @Autowired
    AgentService agentService;
    @Autowired
    InstService instService;
    @Autowired
    ProdCategoryService prodCategoryService;
    
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("instList", ucInstDoList);
    
//        List<UcProdDo> ucProdDos = prodCategoryService.getProdList();
//        modelAndView.getModel().put("prodList", ucProdDos);
        
        modelAndView.setViewName("/agent/add_agent");
        return modelAndView;
    }
    
    @RequestMapping(value = "/add_new_agent", method = RequestMethod.POST)
    @ResponseBody
    @RequiresAuthentication
    public Map toAddNewAgent(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("agentForm") AgentForm agentForm) {
        
        UserBO userBO = getCurrentUser();
        
        log.info("代理开户");
        log.info("代理开户-检查机构信息");
        if(StringUtils.isBlank(agentForm.getInstId())){
            return returnError("机构号不能为空, 请选择机构号");
        }
        UcInstDo ucInstDo = instService.getInst(agentForm.getInstId());
        if(ucInstDo == null){
            return returnError("机构号不存在");
        }
        if(!"Y".equals(ucInstDo.getAgentOk())){
            return returnError("机构不允许开代理");
        }
        
        Boolean dupshortname = agentService.checkIfDupAgentByName("", agentForm.getAgentShortName());
        if(dupshortname){
            return returnError("代理简称重复");
        }
        Boolean dupname = agentService.checkIfDupAgentByName(agentForm.getAgentName(), "");
        if(dupname){
            return returnError("代理全称重复");
        }
    
        //当选择默认代理时
        if(UserConstant.USER_TYPE_DEFAULT.equals(agentForm.getAgentType())){
            Boolean defaultAgentCreated = agentService.checkIfDefaultAgentCreated();
            if(defaultAgentCreated){
                return returnError("默认代理已经开通, 请选择其他代理类型");
            }
        }else{
            Boolean defaultAgentCreated = agentService.checkIfDefaultAgentCreated();
            if(!defaultAgentCreated){
                return returnError("默认代理未开通, 请先开通默认代理");
            }
        }
    
        //检查费率
        String error = agentService.checkFeesAgentOpen(agentForm);
        if(StringUtils.isNotBlank(error)){
            return returnError(error);
        }
    
        //新增代理
        String agent = "";
        try {
//            //检查一些代理信息, 比如费率问题 - 后续添加
//           Map<String, String> result = agentService.checkAgentBefore(agentForm, userBO);
//           if(result.containsKey("error")){
//               return returnError(result.get("error"));
//           }
            
           agent = agentService.createNewAgent(agentForm, userBO);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ErrorException){
                return returnError(e.getMessage());
            }else {
                return returnError("系统异常");
            }
        }
    
        return returnSuccess("新增代理成功, 代理号为:" + agent);
        
    }
    
    
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        AgentForm agentForm = new AgentForm();
        agentForm.setPageCurrent("0");
        agentForm.setPageNo("0");
        agentForm.setPageSize("10");
        
        //获取-所以机构列表
        List<UcInstDo> allInstList = instService.getInstList();
        Pagination pagination = agentService.getAgentListPage(agentForm);
        
        agentForm.setPagination(pagination);
        modelAndView.getModel().put("agentListForm", agentForm);
        modelAndView.getModel().put("instList", allInstList);
        modelAndView.setViewName("/agent/list_agent");
        return modelAndView;
    }
    
    @RequestMapping(value = "/query_agent_list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toQueryAgentList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("agentListForm") AgentForm agentForm) {
    
        //获取-所以机构列表
        List<UcInstDo> allInstList = instService.getInstList();
        modelAndView.getModel().put("instList", allInstList);
    
        Pagination pagination = agentService.getAgentListPage(agentForm);
        
        agentForm.setPagination(pagination);
        modelAndView.getModel().put("agentForm", agentForm);
        modelAndView.setViewName("/agent/list_agent");
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/detailPage", method = RequestMethod.GET)
    public ModelAndView toDetailPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        String agentId = request.getParameter("agentId");
        log.info("查询代理号=" + agentId);
        
        AgentForm agentForm = new AgentForm();
        agentForm.setAgentId(agentId);
        
        agentService.formatAgentFormFromAgent(agentForm);  // 基本信息
        agentService.formatAgentFormFromAgentInfo(agentForm); //附加信息
        agentService.formatAgentFormFromFee(agentForm); //费率信息
        
        List<UcProdDo> ucProdDos = prodCategoryService.getProdList();
        modelAndView.getModel().put("prodList", ucProdDos);
        
        modelAndView.getModel().put("agentDetailForm", agentForm);
        modelAndView.setViewName("/agent/detail_agent");
        return modelAndView;
    }
    
    @RequestMapping(value = "/update_agent", method = RequestMethod.POST)
    public Map<String, Object> toUpdateAgent (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("agentUpdateForm") AgentForm agentForm) {
        
        try {
            UserBO userBO = getCurrentUser();
    
            //检查费率
            String error = agentService.checkFees(agentForm);
            if(StringUtils.isNotBlank(error)){
                return returnError(error);
            }
            
            agentService.updateAgent(agentForm, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnSuccess("数据更新成功");
        
    }
    
    
    @RequestMapping(value = "/activate_agent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toActivateAgent (HttpServletRequest request, HttpServletResponse response) {
        
        try {
            
            String agentId = request.getParameter("agentId");
            if(StringUtils.isBlank(agentId)){
            
            }
            UcAgentDo ucAgentDo = agentService.getAgent(agentId);
            if(ucAgentDo == null){
                return returnError("代理不存在");
            }
            if(StatusConstant.STATUS_CANNEL.equals(ucAgentDo.getStatus())){
                return returnError("代理已注销");
            }
            if(StatusConstant.STATUS_ENABLE.equals(ucAgentDo.getStatus())){
                return returnError("代理已经激活,不需要重复激活");
            }
            
            UcAgentLevelDo ucAgentLevelDo = agentService.getAgentLevel(agentId);
            if(ucAgentLevelDo == null){
                return returnError("代理等级未知");
            }
            if(!"1".equals(ucAgentLevelDo)){
                return returnError("只能激活一级代理");
            }
            
            UserBO userBO = getCurrentUser();
            
            //激活
            agentService.activateAgent(agentId, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnSuccess("代理激活成功");
        
    }
    
    @RequestMapping(value = "/disable_agent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toDisableAgent (HttpServletRequest request, HttpServletResponse response) {
        
        try {
    
            String agentId = request.getParameter("agentId");
            if(StringUtils.isBlank(agentId)){
        
            }
            UcAgentDo ucAgentDo = agentService.getAgent(agentId);
            if(ucAgentDo == null){
                return returnError("代理不存在");
            }
            if(StatusConstant.STATUS_CANNEL.equals(ucAgentDo.getStatus())){
                return returnError("代理已注销, 无法禁用");
            }
            if(StatusConstant.STATUS_DISABLE.equals(ucAgentDo.getStatus())){
                return returnError("代理已经挂起,不需要重复挂起");
            }
    
            UcAgentLevelDo ucAgentLevelDo = agentService.getAgentLevel(agentId);
            if(ucAgentLevelDo == null){
                return returnError("代理等级未知");
            }
            if(!"1".equals(ucAgentLevelDo)){
                return returnError("只能挂起一级代理");
            }
            
            UserBO userBO = getCurrentUser();
            
            agentService.disableAgent(agentId, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnSuccess("代理挂起成功");
        
    }
    
    
    @RequestMapping(value = "/cancel_agent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toCancelAgent (HttpServletRequest request, HttpServletResponse response) {
        
        try {
    
            String agentId = request.getParameter("agentId");
            if(StringUtils.isBlank(agentId)){
        
            }
            UcAgentDo ucAgentDo = agentService.getAgent(agentId);
            if(ucAgentDo == null){
                return returnError("代理不存在");
            }
            if(StatusConstant.STATUS_CANNEL.equals(ucAgentDo.getStatus())){
                return returnError("代理已注销, 不需要重复注销");
            }
            
            UcAgentLevelDo ucAgentLevelDo = agentService.getAgentLevel(agentId);
            if(ucAgentLevelDo == null){
                return returnError("代理等级未知");
            }
            if(!"1".equals(ucAgentLevelDo)){
                return returnError("只能注销一级代理");
            }
            
            UserBO userBO = getCurrentUser();
    
            agentService.cancelAgent(agentId,  userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnSuccess("代理注销成功");
        
    }
    
    @RequestMapping(value = "/feePage", method = RequestMethod.GET)
    public ModelAndView toFeePage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        String agentId = request.getParameter("agentId");
        if(StringUtils.isBlank(agentId)){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "查看费率的代理号参数为空");
            modelAndView.setViewName("/agent/fee_agent");
            return modelAndView;
        }
        
        UcAgentDo ucAgentDo = agentService.getAgent(agentId);
        if(ucAgentDo == null){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "查看费率的代理不存在");
            modelAndView.setViewName("/agent/fee_agent");
            return modelAndView;
        }
        
        AgentForm agentForm = new AgentForm();
        agentForm.setInstId(ucAgentDo.getInstId());
        agentForm.setAgentId(ucAgentDo.getAgentId());
        
        agentService.formatAgentFormFromFee(agentForm); //费率信息
        
        modelAndView.getModel().put("agentFeeForm", agentForm);
        modelAndView.setViewName("/agent/fee_agent");
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/update_agent_fee", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toUpdateAgentFee (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("agentFeeForm") AgentForm agentForm) {
        
        try {
            UserBO userBO = getCurrentUser();
            
            //检查费率
            String error = agentService.checkFeesAgentOpen(agentForm);
            if(StringUtils.isNotBlank(error)){
                return returnError(error);
            }
    
            agentService.updateAgentFeeInfo(agentForm, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnSuccess("数据更新成功");
        
    }
    
    @RequestMapping(value = "/defaultFeePage", method = RequestMethod.GET)
    public ModelAndView toDefaulFeePage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        UserBO userBO = getCurrentUser();
        if(!UserConstant.USER_AGENT.equals(userBO.getUserCodeType())){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "登录账户类型不允许注销代理");
            modelAndView.setViewName("/agent/default_agent_fee");
            return modelAndView;
        }
        
        UcAgentDo ucAgentDo = agentService.getAgent(userBO.getUserCode());
        if(ucAgentDo == null){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "查看费率的代理不存在");
            modelAndView.setViewName("/agent/default_agent_fee");
            return modelAndView;
        }
        
        AgentForm agentForm = new AgentForm();
        agentForm.setInstId(ucAgentDo.getInstId());
        agentForm.setAgentId(ucAgentDo.getAgentId());
        agentForm.setUserType(userBO.getUserCodeType());
        agentForm.setUserCode(userBO.getUserCode());
        
        agentService.formatAgentFormFromFee(agentForm);
        
        modelAndView.getModel().put("defaultAgentFeeForm", agentForm);
        modelAndView.setViewName("/agent/default_agent_fee");
        return modelAndView;
    }
    
    @RequestMapping(value = "/update_default_agent_fee", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toUpdateDefaultAgentFee (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("defaultAgentFeeForm") AgentForm agentForm) {
        
        try {
            UserBO userBO = getCurrentUser();
            
            //检查费率
            String error = agentService.checkFeesAgentOpen(agentForm);
            if(StringUtils.isNotBlank(error)){
                return returnError(error);
            }
            
            agentService.updateAgentFeeInfo(agentForm, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("数据更新失败");
        }
        
        return returnSuccess("数据更新成功");
        
    }
    
    @RequestMapping(value = "/defaultInfoPage", method = RequestMethod.GET)
    public ModelAndView toDefaultInfoPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        UserBO userBO = getCurrentUser();
        if(!UserConstant.USER_AGENT.equals(userBO.getUserCodeType())){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "登录账户类型不是代理");
            modelAndView.setViewName("/agent/default_agent_info");
            return modelAndView;
        }
        
        UcAgentDo ucAgentDo = agentService.getAgent(userBO.getUserCode());
        if(ucAgentDo == null){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "登录代理不存在");
            modelAndView.setViewName("/agent/default_agent_info");
            return modelAndView;
        }
        
        AgentForm agentForm = new AgentForm();
        agentForm.setAgentId(ucAgentDo.getAgentId());
        
        agentService.formatAgentFormFromAgent(agentForm);  // 基本信息
        agentService.formatAgentFormFromAgentInfo(agentForm); //附加信息
        
        modelAndView.getModel().put("defaultAgentDetailForm", agentForm);
        modelAndView.setViewName("/agent/default_agent_info");
        return modelAndView;
    }

//    @RequestMapping(value = "/update_default_agent_info", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> toUpdateDefaultAgentInfo (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("defaultAgentUpdateForm") AgentForm agentForm) {
//
//        try {
//            UserBO userBO = getCurrentUser();
//
//            agentService.updateAgent(agentForm, userBO);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return returnError("数据更新失败");
//        }
//
//        return returnSuccess("数据更新成功");
//
//    }

    
}
