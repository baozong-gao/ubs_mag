package com.company.core.service;

import com.company.core.domain.FuncBO;
import com.company.core.form.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by fireWorks on 2016/2/3.
 */
public interface FuncService {

    Pagination<FuncBO> getFuncList(String rid);
    
    List<FuncBO> getFuncBoList(String rid);

    Map setFuncEnable(String funcId) throws Exception;

    Map setFuncDisable(String funcId) throws Exception;

    Pagination<FuncBO> getTheFunc(FuncBO funcBO);

    Pagination<FuncBO> getAllFunc(FuncBO funcBO);

    Map addNewFunc(FuncBO funcBO);

    FuncBO getById(String funcId);

    Map updateFuncInfo(FuncBO funcBO);

}
