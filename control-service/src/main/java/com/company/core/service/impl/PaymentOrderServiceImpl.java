package com.company.core.service.impl;

import com.company.core.biz.QRCPaymentRecordBiz;
import com.company.core.biz.UCUserAgentBiz;
import com.company.core.biz.WZExeOrderBiz;
import com.company.core.entity.QrcPaymentRecordDo;
import com.company.core.entity.QrcPaymentRecordDoExample;
import com.company.core.form.Pagination;
import com.company.core.form.PaymentOrderInfoForm;
import com.company.core.service.AccountUserService;
import com.company.core.service.AgentService;
import com.company.core.service.PaymentOrderService;
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
@Service("PaymentOrderService")
@Slf4j
public class PaymentOrderServiceImpl implements PaymentOrderService {
    
    @Autowired
    WZExeOrderBiz WZExeOrderBiz;
    @Autowired
    AccountUserService  accountUserService;
    @Autowired
    UCUserAgentBiz ucUserAgentBiz;
    @Autowired
    AgentService agentService;
    @Autowired
    QRCPaymentRecordBiz qrcPaymentRecordBiz;
    
    
    @Override
    public Pagination getOrderListPage(PaymentOrderInfoForm paymentOrderInfoForm) {
        
        int pageCurrent = Integer.parseInt(paymentOrderInfoForm.getPageCurrent());
        int pageSize = Integer.parseInt(paymentOrderInfoForm.getPageSize());
        QrcPaymentRecordDoExample qrcPaymentRecordDoExample = formatInstSearchCriteria(paymentOrderInfoForm);
        
        //获取满足的记录条数
        long tranSize = qrcPaymentRecordBiz.countByExample(qrcPaymentRecordDoExample);
        Pagination<QrcPaymentRecordDo> page = new Pagination<QrcPaymentRecordDo>((int) tranSize, pageCurrent, pageSize);
        PageHelper.startPage(pageCurrent, pageSize);
    
        //排序
        qrcPaymentRecordDoExample.setOrderByClause("ORDER_DATE DESC, ORDER_TIME DESC");
        List<QrcPaymentRecordDo> qrcPaymentRecordDoList = qrcPaymentRecordBiz.selectByExample(qrcPaymentRecordDoExample);
        page.addResult(qrcPaymentRecordDoList);
        return page;
    }
    
    
    public QrcPaymentRecordDoExample formatInstSearchCriteria(PaymentOrderInfoForm paymentOrderInfoForm){
        
        QrcPaymentRecordDoExample qrcPaymentRecordDoExample = new QrcPaymentRecordDoExample();
        QrcPaymentRecordDoExample.Criteria criteria = qrcPaymentRecordDoExample.createCriteria();
    
        if (StringUtils.isNotBlank(paymentOrderInfoForm.getOrderId())) {
            criteria.andOrderIdEqualTo(paymentOrderInfoForm.getOrderId());
        }
        if (StringUtils.isNotBlank(paymentOrderInfoForm.getOrderType())) {
            criteria.andOrderTypeEqualTo(paymentOrderInfoForm.getOrderType());
        }
        if (StringUtils.isNotBlank(paymentOrderInfoForm.getOrderStatus())) {
            criteria.andBipiStatusEqualTo(paymentOrderInfoForm.getOrderStatus());
        }
        criteria.andOrderDateBetween(paymentOrderInfoForm.getStartOrderDate(), paymentOrderInfoForm.getEndOrderDate());
        
        return qrcPaymentRecordDoExample;
    }
}
