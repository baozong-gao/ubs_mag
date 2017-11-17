package com.company.core.service;

import com.company.core.biz.UCProdBiz;
import com.company.core.domain.FuncBO;
import com.company.core.entity.UcProdDo;
import com.company.core.form.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProdCategoryService {

    
    @Autowired
    UCProdBiz ucProdBiz;
    
    
    
    public List<UcProdDo> getProdList(){
          return ucProdBiz.selectProdListEnabled();
    }

}
