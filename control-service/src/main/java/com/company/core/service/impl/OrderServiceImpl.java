package com.company.core.service.impl;

import com.company.core.biz.*;
import com.company.core.entity.*;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.service.OrderService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("OrderService")
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    WZExeOrderBiz WZExeOrderBiz;
    
    
    @Override
    public Pagination getOrderListPage(OrderForm orderForm) {
        
        int pageCurrent = Integer.parseInt(orderForm.getPageCurrent());
        int pageSize = Integer.parseInt(orderForm.getPageSize());
        WZExeOrderDoExample wzExeOrderDoExample = formatInstSearchCriteria(orderForm);
        
        //获取满足的记录条数
        long tranSize = WZExeOrderBiz.countByExample(wzExeOrderDoExample);
        Pagination<WZExeOrderDo> page = new Pagination<WZExeOrderDo>((int) tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
        List<WZExeOrderDo> wzExeOrderDoList = WZExeOrderBiz.selectByExample(wzExeOrderDoExample);
//        List<InstBO> list = new ArrayList<>();
//        InstBO instBO = null;
//        if(ucInstDos != null && ucInstDos.size() > 0){
//            for(UcInstDo ur: ucInstDos){
//                instBO = new InstBO();
//                instBO.setInstId(ur.getInstId());
//                instBO.setInstName(ur.getInstName());
//                instBO.setStatus(ur.getStatus());
//                instBO.setCreateUser(ur.getCreateUser());
//                instBO.setCreateTime(ur.getCreateTime());
//                list.add(instBO);
//            }
//        }
        page.addResult(wzExeOrderDoList);
        return page;
    }
    
    
    public WZExeOrderDoExample formatInstSearchCriteria(OrderForm orderForm){
        
        WZExeOrderDoExample wzExeOrderDoExample = new WZExeOrderDoExample();
        WZExeOrderDoExample.Criteria criteria = wzExeOrderDoExample.createCriteria();
        
        if(StringUtils.isNotBlank(orderForm.getId())){
            criteria.andIdEqualTo(orderForm.getId());
        }
        if(StringUtils.isNotBlank(orderForm.getOrderId())){
            criteria.andOrderIdEqualTo(orderForm.getOrderId());
        }
        if(StringUtils.isNotBlank(orderForm.getUserId())){
            criteria.andUserIdEqualTo(orderForm.getUserId());
        }
        if(StringUtils.isNotBlank(orderForm.getStatus())){
            criteria.andStatusEqualTo(orderForm.getStatus());
        }
        if(StringUtils.isNotBlank(orderForm.getPayOrder())){
            criteria.andPayOrderEqualTo(orderForm.getPayOrder());
        }
        return wzExeOrderDoExample;
    }
    
    
    
    
    
    
    
}
