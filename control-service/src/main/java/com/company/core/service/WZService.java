package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentInfoDo;
import com.company.core.entity.UcAgentLevelDo;
import com.company.core.entity.WzInfoDo;
import com.company.core.form.AgentForm;
import com.company.core.form.Pagination;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */

public interface WZService {
    
    
    List<WzInfoDo> getWZListByOrderId(String orderId);
}
