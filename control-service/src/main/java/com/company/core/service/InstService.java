package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcInstDo;
import com.company.core.form.InstForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface InstService {
    
    public List<UcInstDo> getInstList();
    
    public List<UcInstDo> getInstListByStatus(String status);
    
    public String createNewInst(InstForm instForm, UserBO userBO) throws Exception;
    
    public UcInstDo getInst(String instId);
    
    public Boolean checkIfDupInstByName(String instName, String instShortName );
    
}
