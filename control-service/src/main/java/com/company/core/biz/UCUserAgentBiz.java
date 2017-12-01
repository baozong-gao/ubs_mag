package com.company.core.biz;

import com.company.core.entity.UcUserAgentDo;
import com.company.core.entity.UcUserAgentDoExample;
import com.company.core.mapper.UcUserAgentDoMapper;
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
public class UCUserAgentBiz {

    @Autowired
    UcUserAgentDoMapper ucUserAgentDoMapper;
    
    public long countByExample(UcUserAgentDoExample ucUserAgentDoExample){
        return ucUserAgentDoMapper.countByExample(ucUserAgentDoExample);
    }
    
    public List<UcUserAgentDo> selectByExample(UcUserAgentDoExample ucUserAgentDoExample){
        return ucUserAgentDoMapper.selectByExample(ucUserAgentDoExample);
    }
    
    public UcUserAgentDo selectByPrimaryKey(String id){
        
        return ucUserAgentDoMapper.selectByPrimaryKey(id);
    }
}
