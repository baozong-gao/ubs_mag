package com.company.core.service.impl;

import com.company.core.biz.WZBalanceInfoBiz;
import com.company.core.biz.WZInfoBiz;
import com.company.core.entity.WZBalanceInfoDo;
import com.company.core.entity.WZBalanceInfoDoExample;
import com.company.core.entity.WzInfoDo;
import com.company.core.entity.WzInfoDoExample;
import com.company.core.service.WZBalanceService;
import com.company.core.service.WZService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("WZBalanceService")
@Slf4j
public class WZBalanceServiceImpl implements WZBalanceService {
    
    @Autowired
    WZBalanceInfoBiz wzBalanceInfoBiz;
    
    @Override
    public List<WZBalanceInfoDo> getWZBalanceInfoList() {
    
        WZBalanceInfoDoExample wzBalanceInfoDoExample = new WZBalanceInfoDoExample();
        wzBalanceInfoDoExample.createCriteria().andOrgIdIsNotNull();
        List<WZBalanceInfoDo> wzBalanceInfoDoList = wzBalanceInfoBiz.selectByExample(wzBalanceInfoDoExample);
        
        return wzBalanceInfoDoList;
    }
}
