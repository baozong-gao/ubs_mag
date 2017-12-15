package com.company.core.mapper;

import com.company.core.entity.UcUserAgentDo;
import com.company.core.entity.UcUserAgentDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UcUserAgentDoCUSTOMMapper {
    
    List<String> selectOnlyIdByExample(UcUserAgentDoExample example);
    
}