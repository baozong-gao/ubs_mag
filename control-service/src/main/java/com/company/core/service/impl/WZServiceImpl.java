package com.company.core.service.impl;

import com.company.core.biz.WZExeOrderBiz;
import com.company.core.biz.WZInfoBiz;
import com.company.core.entity.WZExeOrderDo;
import com.company.core.entity.WZExeOrderDoExample;
import com.company.core.entity.WzInfoDo;
import com.company.core.entity.WzInfoDoExample;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.service.OrderService;
import com.company.core.service.WZService;
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
@Service("WZService")
@Slf4j
public class WZServiceImpl implements WZService {
    
    @Autowired
    WZInfoBiz wzInfoBiz;
    
    @Override
    public List<WzInfoDo> getWZListByOrderId(String orderId){
    
        WzInfoDoExample wzInfoDoExample = new WzInfoDoExample();
        wzInfoDoExample.createCriteria().andExeOrderEqualTo(orderId);
        return wzInfoBiz.selectByExample(wzInfoDoExample);
        
    }
    
    
    
}
