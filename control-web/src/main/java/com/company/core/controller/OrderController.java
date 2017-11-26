package com.company.core.controller;

import com.company.core.constant.Constant;
import com.company.core.constant.ErrorException;
import com.company.core.constant.StatusConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcProdDo;
import com.company.core.form.InstForm;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.service.OrderService;
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
    
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        OrderForm orderForm = new OrderForm();
        orderForm.setPageCurrent("0");
        orderForm.setPageNo("0");
        orderForm.setPageSize("10");
    
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
    
        //获取-所有交易
        Pagination pagination = orderService.getOrderListPage(orderForm);
        orderForm.setPagination(pagination);
        modelAndView.getModel().put("orderListForm", orderForm);
        modelAndView.setViewName("/order/list_order");
        return modelAndView;
        
    }
    
    
}
