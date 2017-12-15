package com.company.core.controller;

import com.company.core.constant.StatusConstant;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.WzInfoDo;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.form.PaymentOrderInfoForm;
import com.company.core.service.*;
import com.company.core.util.DateUtil;
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
import java.util.Date;
import java.util.List;

/**
 * 支付管理
 */
@Controller
@Slf4j
@RequestMapping (value = "/paymentOrder")
public class PaymentOrderController extends BaseController {

    @Autowired
    PaymentOrderService paymentOrderService;
    @Autowired
    InstService instService;
    @Autowired
    AgentService agentService;
    @Autowired
    WZService wzService;
    
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        PaymentOrderInfoForm paymentOrderInfoForm = new PaymentOrderInfoForm();
        paymentOrderInfoForm.setPageCurrent("0");
        paymentOrderInfoForm.setPageNo("0");
        paymentOrderInfoForm.setPageSize("10");
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("instList", ucInstDoList);
    
        //获取-激活状态下的代理列表
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown("","", StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("agentList", ucAgentDoList);
        
        paymentOrderInfoForm.setStartOrderDate(DateUtil.date2String(new Date(), "yyyyMMdd"));
        paymentOrderInfoForm.setEndOrderDate(DateUtil.date2String(new Date(), "yyyyMMdd"));
        
        //获取-所有交易
        Pagination pagination = paymentOrderService.getOrderListPage(paymentOrderInfoForm);
        paymentOrderInfoForm.setPagination(pagination);
        modelAndView.getModel().put("paymentOrderListForm", paymentOrderInfoForm);
        modelAndView.setViewName("/paymentOrder/list_order");
        modelAndView.getModel().put("errorCode", "S");
        modelAndView.getModel().put("errorMessage", "");
        return modelAndView;
    }
    
    @RequestMapping(value = "/query_order_list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toQueryOrderList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("paymentOrderInfoForm") PaymentOrderInfoForm paymentOrderInfoForm) {
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("instList", ucInstDoList);
    
        //获取-激活状态下的代理列表
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown("","", StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("agentList", ucAgentDoList);
        
        //日期判断
        if(StringUtils.isBlank(paymentOrderInfoForm.getStartOrderDate())){
            paymentOrderInfoForm.setStartOrderDate(DateUtil.date2String(new Date(), "yyyyMMdd"));
        }
        if(StringUtils.isBlank(paymentOrderInfoForm.getEndOrderDate())){
            paymentOrderInfoForm.setEndOrderDate(DateUtil.date2String(new Date(), "yyyyMMdd"));
        }
        //开始日期不能大于结束日期
        if(paymentOrderInfoForm.getStartOrderDate().compareTo(paymentOrderInfoForm.getEndOrderDate()) ==1){
            modelAndView.getModel().put("errorCode", "F");
            modelAndView.getModel().put("errorMessage", "开始日期不能大于结束日期");
            modelAndView.getModel().put("paymentOrderListForm", paymentOrderInfoForm);
            modelAndView.setViewName("/paymentOrder/list_order");
            return modelAndView;
        }
        
        //获取-所有交易
        Pagination pagination = paymentOrderService.getOrderListPage(paymentOrderInfoForm);
        paymentOrderInfoForm.setPagination(pagination);
        modelAndView.getModel().put("paymentOrderListForm", paymentOrderInfoForm);
        modelAndView.setViewName("/paymentOrder/list_order");
        return modelAndView;
        
    }
    
}
