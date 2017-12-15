package com.company.core.biz;

import com.company.core.entity.WZExeOrderDo;
import com.company.core.entity.WZExeOrderDoExample;
import com.company.core.entity.WzInfoDo;
import com.company.core.entity.WzInfoDoExample;
import com.company.core.mapper.WZExeOrderDoMapper;
import com.company.core.mapper.WzInfoDoMapper;
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
public class WZInfoBiz {

    @Autowired
    WzInfoDoMapper wzInfoDoMapper;
    
    public List<WzInfoDo> selectByExample(WzInfoDoExample wzInfoDoExample){
        return wzInfoDoMapper.selectByExample(wzInfoDoExample);
    }
    
    public WzInfoDo selectByPrimaryKey(String id){
    
        WzInfoDo wzInfoDo = wzInfoDoMapper.selectByPrimaryKey(id);
        return wzInfoDo;
    }
}
