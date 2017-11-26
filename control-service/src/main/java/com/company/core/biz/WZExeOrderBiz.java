package com.company.core.biz;

import com.company.core.entity.*;
import com.company.core.mapper.UcInstDoMapper;
import com.company.core.mapper.UcInstInfoDoMapper;
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
public class WZExeOrderBiz {

    @Autowired
    WZExeOrderDoMapper  wzExeOrderDoMapper;

    
    public List<WZExeOrderDo> selectByExample(WZExeOrderDoExample wzExeOrderDoExample){
        return wzExeOrderDoMapper.selectByExample(wzExeOrderDoExample);
    }
    
    public WZExeOrderDo selectByPrimaryKey(String id){
        return wzExeOrderDoMapper.selectByPrimaryKey(id);
    }
    
    public long countByExample(WZExeOrderDoExample wzExeOrderDoExample){
        return wzExeOrderDoMapper.countByExample(wzExeOrderDoExample);
    }
    
}
