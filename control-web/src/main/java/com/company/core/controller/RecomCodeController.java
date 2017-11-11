package com.company.core.controller;

import com.company.core.entity.UcInstDo;
import com.company.core.form.RecomCodeForm;
import com.company.core.service.InstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
@RequestMapping (value = "/recomCode")
public class RecomCodeController {
    
    @Autowired
    InstService instService;
    
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toCreatePage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        
        List<UcInstDo> ucInstDoList = instService.getInstList();
        
        modelAndView.getModel().put("ucInstDoList", ucInstDoList);
        modelAndView.setViewName("/recomCode/add_recom_code");
        return modelAndView;
    }
    
    
    
}
