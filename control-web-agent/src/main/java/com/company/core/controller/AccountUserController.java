package com.company.core.controller;

import com.company.core.constant.StatusConstant;
import com.company.core.constant.UserConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcInstDo;
import com.company.core.form.AccountUserForm;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.service.AccountUserService;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

/**
 * 账户用户
 */
@Controller
@Slf4j
@RequestMapping (value = "/accountUser")
public class AccountUserController extends BaseController {
    
    @Autowired
    AccountUserService accountUserService;
    @Autowired
    InstService instService;
    @Autowired
    AgentService agentService;
    
    
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        UserBO userBO = getCurrentUser();
        OrderForm orderForm = new OrderForm();
        if(!UserConstant.USER_INST.equals(userBO.getUserCodeType()) && !UserConstant.USER_AGENT.equals(userBO.getUserCodeType())){
            //登录账户类型错误
            modelAndView.setViewName("/accountUser/list_accountUser");
            return modelAndView;
        }
    
        AccountUserForm accountUserForm = new AccountUserForm();
        accountUserForm.setPageCurrent("0");
        accountUserForm.setPageNo("0");
        accountUserForm.setPageSize("10");
        
        //获取-激活状态下的代理列表
        orderForm.setUserType(userBO.getUserCodeType());
        if(UserConstant.USER_INST.equals(userBO.getUserCodeType())){
            List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown(userBO.getUserCode(),"", StatusConstant.STATUS_ENABLE);
            modelAndView.getModel().put("agentList", ucAgentDoList);
            accountUserForm.setInstId(userBO.getUserCode());
        } else if(UserConstant.USER_AGENT.equals(userBO.getUserCodeType())){
            List<UcAgentDo> ucAgentDoList = agentService.getAgentListOfAgentOwn(userBO.getUserCode(), StatusConstant.STATUS_ENABLE);
            modelAndView.getModel().put("agentList", ucAgentDoList);
            accountUserForm.setAgentId(userBO.getUserCode());
        }
        
        //获取-所有用户
        Pagination pagination = accountUserService.getAccountUserListPage(accountUserForm);
        accountUserForm.setPagination(pagination);
        modelAndView.getModel().put("accountUserListForm", accountUserForm);
        modelAndView.setViewName("/accountUser/list_accountUser");
        return modelAndView;
    }
    
    @RequestMapping(value = "/query_accountUser_list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toQueryAccountUserList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("accountUserListForm") AccountUserForm accountUserForm) {
    
        UserBO userBO = getCurrentUser();
        if(!UserConstant.USER_INST.equals(userBO.getUserCodeType()) && !UserConstant.USER_AGENT.equals(userBO.getUserCodeType())){
            //登录账户类型错误
            modelAndView.setViewName("/accountUser/list_accountUser");
            return modelAndView;
        }
        accountUserForm.setUserType(userBO.getUserCodeType());
        accountUserForm.setUserCode(userBO.getUserCode());

        //获取-激活状态下的代理列表
        if(UserConstant.USER_INST.equals(userBO.getUserCodeType())){
            List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown(userBO.getUserCode(),"", StatusConstant.STATUS_ENABLE);
            modelAndView.getModel().put("agentList", ucAgentDoList);
        } else if(UserConstant.USER_AGENT.equals(userBO.getUserCodeType())){
            List<UcAgentDo> ucAgentDoList = agentService.getAgentListOfAgentOwn(userBO.getUserCode(), StatusConstant.STATUS_ENABLE);
            modelAndView.getModel().put("agentList", ucAgentDoList);
        }
    
        //获取-所有用户
        Pagination pagination = accountUserService.getAccountUserListPage(accountUserForm);
        accountUserForm.setPagination(pagination);
        modelAndView.getModel().put("accountUserListForm", accountUserForm);
        modelAndView.setViewName("/accountUser/list_accountUser");
        return modelAndView;

    }
    
    
    @RequestMapping(value = "/detailPage", method = RequestMethod.GET)
    public ModelAndView toDetailPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        String userId = request.getParameter("userId");
        if(StringUtils.isBlank(userId)){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "查看费率的用户号参数为空");
            modelAndView.setViewName("/accountUser/detail_user");
            return modelAndView;
        }
        
        AccountUserForm accountUserForm = new AccountUserForm();
        accountUserForm.setUserId(userId);
        
        accountUserService.formatAccountUserFormFromFee(accountUserForm); //费率信息
        
        modelAndView.getModel().put("accountUserFeeForm", accountUserForm);
        modelAndView.setViewName("/accountUser/detail_user");
        return modelAndView;
    }
    
}
