package com.company.core.controller;

import com.company.core.constant.ErrorException;
import com.company.core.domain.UserBO;
import com.company.core.form.InstForm;
import com.company.core.service.InstService;
import com.company.core.shiro.MonitorRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping (value = "/inst")
public class InstController extends BaseController {
    
    @Autowired
    InstService instService;
    
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        modelAndView.setViewName("/inst/add_inst");
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/add_new_inst", method = RequestMethod.POST)
    @ResponseBody
    @RequiresAuthentication
    public Map toAddNewInst(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("instForm") InstForm instForm) {
        
        UserBO userBO = getCurrentUser();
        
        log.info("机构开户");
        Boolean dupshortname = instService.checkIfDupInstByName("", instForm.getInstShortName());
        if(dupshortname){
            return returnSuccess("机构简称重复");
        }
        Boolean dupname = instService.checkIfDupInstByName(instForm.getInstName(), "");
        if(dupname){
            return returnSuccess("机构全称重复");
        }
    
        //新增机构
        String inst = "";
        try {
           inst = instService.createNewInst(instForm, userBO);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ErrorException){
                return returnError(e.getMessage());
            }else {
                return returnError("系统异常");
            }
        }
    
        return returnSuccess("新增机构成功, 机构号为:" + inst);
        
    }
    
    
}
