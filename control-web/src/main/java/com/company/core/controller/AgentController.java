package com.company.core.controller;

import com.company.core.constant.ErrorException;
import com.company.core.constant.StatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
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
    
        List<UcProdDo> ucProdDos = prodCategoryService.getProdList();
        modelAndView.getModel().put("prodList", ucProdDos);
        
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
            //检查一些代理信息, 比如费率问题 - 后续添加
           Map<String, String> result = agentService.checkAgentBefore(agentForm, userBO);
           if(result.containsKey("error")){
               return returnError(result.get("error"));
           }
            
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
        
        
        InstForm instForm = new InstForm();
        instForm.setInstId(agentId);
        
        instService.formatInstFormFromInst(instForm);  // 基本信息
        instService.formatInstFormFromInstInfo(instForm); //附加信息
        instService.formatInstFormFromFee(instForm); //费率信息
        
        List<UcProdDo> ucProdDos = prodCategoryService.getProdList();
        modelAndView.getModel().put("prodList", ucProdDos);
        
        modelAndView.getModel().put("instDetailForm", instForm);
        modelAndView.setViewName("/inst/detail_inst");
        return modelAndView;
    }
    
    
    
    
}
