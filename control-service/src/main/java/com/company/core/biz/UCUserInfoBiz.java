package com.company.core.biz;

import com.company.core.entity.UcUserAgentDo;
import com.company.core.entity.UcUserAgentDoExample;
import com.company.core.entity.UcUserInfoDo;
import com.company.core.entity.UcUserInfoDoExample;
import com.company.core.mapper.UcUserAgentDoMapper;
import com.company.core.mapper.UcUserInfoDoMapper;
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
public class UCUserInfoBiz {

    @Autowired
    UcUserInfoDoMapper ucUserInfoDoMapper;
    
    public long countByExample(UcUserInfoDoExample ucUserInfoDoExample){
        return ucUserInfoDoMapper.countByExample(ucUserInfoDoExample);
    }
    
    public List<UcUserInfoDo> selectByExample(UcUserInfoDoExample ucUserInfoDoExample){
        return ucUserInfoDoMapper.selectByExample(ucUserInfoDoExample);
    }
    
    public UcUserInfoDo selectByPrimaryKey(String id){
        
        return ucUserInfoDoMapper.selectByPrimaryKey(id);
    }
}
