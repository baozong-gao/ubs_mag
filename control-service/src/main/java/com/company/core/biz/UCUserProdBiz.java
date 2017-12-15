package com.company.core.biz;

import com.company.core.entity.UcUserInfoDo;
import com.company.core.entity.UcUserInfoDoExample;
import com.company.core.entity.UcUserProdDo;
import com.company.core.entity.UcUserProdDoExample;
import com.company.core.mapper.UcUserInfoDoMapper;
import com.company.core.mapper.UcUserProdDoMapper;
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
public class UCUserProdBiz {

    @Autowired
    UcUserProdDoMapper ucUserProdDoMapper;
    
    public UcUserProdDo selectByPrimaryKey(String id){
        
        return ucUserProdDoMapper.selectByPrimaryKey(id);
    }
    
    public UcUserProdDo selectByUserId(String userId){
    
        UcUserProdDoExample ucUserProdDoExample = new UcUserProdDoExample();
        ucUserProdDoExample.createCriteria().andUserIdEqualTo(userId);
        List<UcUserProdDo> ucUserProdDoList = ucUserProdDoMapper.selectByExample(ucUserProdDoExample);
        if(ucUserProdDoList != null && ucUserProdDoList.size() > 0){
            return ucUserProdDoList.get(0);
        }
        return null;
    }
}
