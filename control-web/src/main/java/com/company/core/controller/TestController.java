package com.company.core.controller;

import com.company.core.constant.StatusConstant;
import com.company.core.entity.AccountUserDo;
import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.WzInfoDo;
import com.company.core.form.OrderForm;
import com.company.core.form.Pagination;
import com.company.core.mapper.AccountUserSelfDefineMapper;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.company.core.service.OrderService;
import com.company.core.service.WZService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
public class TestController extends BaseController {
    
    @Autowired
    AccountUserSelfDefineMapper accountUserSelfDefineMapper;
    
    @RequestMapping(value = "/test/testContoller", method = RequestMethod.GET)
    public ModelAndView toTestContoller(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        AccountUserDo accountUserDo = new AccountUserDo();
        accountUserDo.setAgentId("00000041");
        List<AccountUserDo> accountUserDoList = accountUserSelfDefineMapper.selectUsersByAgent(accountUserDo);
        
        for(AccountUserDo au: accountUserDoList){
            log.info("au=" + au.toString());
        }
        
        return modelAndView;
    }
    
}
