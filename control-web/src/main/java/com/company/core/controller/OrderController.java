package com.company.core.controller;

import com.company.core.biz.WZInfoBiz;
import com.company.core.constant.Constant;
import com.company.core.constant.ErrorException;
import com.company.core.constant.StatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcProdDo;
import com.company.core.entity.WzInfoDo;
import com.company.core.form.InstForm;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.company.core.service.OrderService;
import com.company.core.service.WZService;
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

/**
 * 交易管理
 */
@Controller
@Slf4j
@RequestMapping (value = "/order")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;
    @Autowired
    InstService instService;
    @Autowired
    AgentService agentService;
    @Autowired
    WZService wzService;
    
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        OrderForm orderForm = new OrderForm();
        orderForm.setPageCurrent("0");
        orderForm.setPageNo("0");
        orderForm.setPageSize("10");
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("instList", ucInstDoList);
    
        //获取-激活状态下的代理列表
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown("","", StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("agentList", ucAgentDoList);
    
        //获取-所有交易
        Pagination pagination = orderService.getOrderListPage(orderForm);
        orderForm.setPagination(pagination);
        modelAndView.getModel().put("orderListForm", orderForm);
        modelAndView.setViewName("/order/list_order");
        return modelAndView;
    }
    
    @RequestMapping(value = "/query_order_list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toQueryOrderList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("orderListForm") OrderForm orderForm) {
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("instList", ucInstDoList);
    
        //获取-激活状态下的代理列表
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown("","", StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("agentList", ucAgentDoList);
        
        //获取-所有交易
        Pagination pagination = orderService.getOrderListPage(orderForm);
        orderForm.setPagination(pagination);
        modelAndView.getModel().put("orderListForm", orderForm);
        modelAndView.setViewName("/order/list_order");
        return modelAndView;
        
    }
    
    
    @RequestMapping(value = "/detailOrderPage", method = RequestMethod.GET)
    public ModelAndView toDetailOrderPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        String id = request.getParameter("id");
        String orderId = request.getParameter("orderId");
        String payOrderId = request.getParameter("payOrderId");
        log.info("订单ID=" + id);
        log.info("订单orderId=" + orderId);
        log.info("订单payOrderId=" + payOrderId);
        
        OrderForm orderForm = new OrderForm();
        orderForm.setId(id);
        orderForm.setOrderId(orderId);
        orderForm.setPayOrder(payOrderId);
    
        List<WzInfoDo> wzInfoDoList = wzService.getWZListByOrderId(orderId);
        
        modelAndView.getModel().put("orderDetailForm", orderForm);
        modelAndView.getModel().put("wzList", wzInfoDoList);
        modelAndView.setViewName("/order/detail_order");
        return modelAndView;
    }
    
    
}
