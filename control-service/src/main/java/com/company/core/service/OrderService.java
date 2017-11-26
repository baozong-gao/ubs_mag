package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcInstInfoDo;
import com.company.core.form.InstForm;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface OrderService {
    
    
    Pagination getOrderListPage(OrderForm orderForm);
}
