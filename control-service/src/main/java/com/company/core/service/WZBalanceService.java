package com.company.core.service;

import com.company.core.entity.WZBalanceInfoDo;
import com.company.core.entity.WzInfoDo;

import java.util.List;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */

public interface WZBalanceService {
    
    List<WZBalanceInfoDo> getWZBalanceInfoList();
}
