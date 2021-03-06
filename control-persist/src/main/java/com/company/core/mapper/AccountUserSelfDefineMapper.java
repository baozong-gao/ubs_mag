package com.company.core.mapper;

import com.company.core.entity.AccountUserDo;
import com.company.core.entity.UcUserAgentDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountUserSelfDefineMapper {
    
    
    List<AccountUserDo> selectUsersByAgent(@Param("record") AccountUserDo record);

    
}