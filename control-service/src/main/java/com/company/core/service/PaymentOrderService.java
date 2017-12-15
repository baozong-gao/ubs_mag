package com.company.core.service;

import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.form.PaymentOrderInfoForm;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface PaymentOrderService {
    
    Pagination getOrderListPage(PaymentOrderInfoForm paymentOrderInfoForm);
    
}
