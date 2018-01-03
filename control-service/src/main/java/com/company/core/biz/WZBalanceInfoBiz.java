package com.company.core.biz;

import com.company.core.entity.WZBalanceInfoDo;
import com.company.core.entity.WZBalanceInfoDoExample;
import com.company.core.entity.WZExeOrderDo;
import com.company.core.entity.WZExeOrderDoExample;
import com.company.core.mapper.WZBalanceInfoDoMapper;
import com.company.core.mapper.WZExeOrderDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class WZBalanceInfoBiz {

    @Autowired
    WZBalanceInfoDoMapper wzBalanceInfoDoMapper;
    
    public List<WZBalanceInfoDo> selectByExample(WZBalanceInfoDoExample wzBalanceInfoDoExample){
        return wzBalanceInfoDoMapper.selectByExample(wzBalanceInfoDoExample);
    }
    
    public WZBalanceInfoDo selectByPrimaryKey(String id){
        return wzBalanceInfoDoMapper.selectByPrimaryKey(id);
    }
    
}
