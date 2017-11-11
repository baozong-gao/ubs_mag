package com.company.core.controller;

import com.company.core.constant.ErrorException;
import com.company.core.constant.UserStatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcInstDo;
import com.company.core.form.AgentForm;
import com.company.core.form.InstForm;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
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
    
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(UserStatusConstant.STATUS_ENABLE);
        
        modelAndView.getModel().put("instList", ucInstDoList);
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
            return returnSuccess("代理简称重复");
        }
        Boolean dupname = agentService.checkIfDupAgentByName(agentForm.getAgentName(), "");
        if(dupname){
            return returnSuccess("代理全称重复");
        }
    
        //新增代理
        String agent = "";
        try {
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
    
    
}
