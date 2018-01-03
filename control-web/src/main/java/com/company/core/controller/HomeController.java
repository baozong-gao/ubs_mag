package com.company.core.controller;

import com.company.core.entity.WZBalanceInfoDo;
import com.company.core.service.WZBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.core.Enum.StatisticalTypeEnum;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by APPLE on 15/12/29.
 */
@Controller
@Slf4j
@RequestMapping(value = "/home")
public class HomeController {
    
    @Autowired
    WZBalanceService wzBalanceService;
    
    /**
     * 首页内容
     */
    @RequestMapping(value = "/first_page", method = RequestMethod.GET)
    public ModelAndView toFirstPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        List<WZBalanceInfoDo> wzBalanceInfoDoList = wzBalanceService.getWZBalanceInfoList();
        
        modelAndView.getModel().put("balanceList", wzBalanceInfoDoList);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
