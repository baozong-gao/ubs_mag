package com.company.core.biz;

import com.company.core.constant.StatusConstant;
import com.company.core.entity.*;
import com.company.core.mapper.UcCategoryDoMapper;
import com.company.core.mapper.UcProdDoMapper;
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
@Slf4j
@Service
public class UCCategoryBiz {

    @Autowired
    UcCategoryDoMapper ucCategoryDoMapper;
    
    public List<UcCategoryDo> selectCategoryIdList(String category, String status){
    
        UcCategoryDoExample ucCategoryDoExample = new UcCategoryDoExample();
        UcCategoryDoExample.Criteria criteria = ucCategoryDoExample.createCriteria();
        if(StringUtils.isNotBlank(status)){
            criteria.andStatusEqualTo(StatusConstant.STATUS_ENABLE);
        }
        if(StringUtils.isNotBlank(category)){
            criteria.andCategoryEqualTo(category);
        }
        return ucCategoryDoMapper.selectByExample(ucCategoryDoExample);
    }
    
    public UcCategoryDo selectByPrimaryKey(UcCategoryDoKey ucCategoryDoKey){
        return ucCategoryDoMapper.selectByPrimaryKey(ucCategoryDoKey);
    }
    
    public String getCatagoryName(String catagory){
    
        UcCategoryDoExample ucCategoryDoExample = new UcCategoryDoExample();
        ucCategoryDoExample.createCriteria().andCategoryEqualTo(catagory);
        List<UcCategoryDo> ucCategoryDoList = ucCategoryDoMapper.selectByExample(ucCategoryDoExample);
        if(ucCategoryDoList == null || ucCategoryDoList.size() == 0){
            return ucCategoryDoList.get(0).getCategoryName();
        }
    
        return "基础服务";
    }
    
}
