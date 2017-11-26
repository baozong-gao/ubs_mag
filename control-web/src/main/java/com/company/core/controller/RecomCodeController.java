package com.company.core.controller;

import com.company.core.constant.StatusConstant;
import com.company.core.constant.UserConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.*;
import com.company.core.form.Pagination;
import com.company.core.form.RecomCodeForm;
import com.company.core.service.AgentService;
import com.company.core.service.InstService;
import com.company.core.service.RecomCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@Slf4j
@RequestMapping (value = "/recomCode")
public class RecomCodeController extends BaseController {
    
    private final static String LEVEL_1 = "1";
    private final static String LEVEL_2 = "2";
    private final static String LEVEL_3 = "3";
    
    @Autowired
    InstService instService;
    @Autowired
    AgentService agentService;
    @Autowired
    RecomCodeService recomCodeService;
    
    /**
     * 注册码新增 - 页面
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        
        List<UcInstDo> ucInstDoList = instService.getInstList();
        
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.setViewName("/recomCode/add_recom_code");
        return modelAndView;
    }
    
    @RequestMapping(value = "/add_new_recomCode", method = RequestMethod.POST)
    @ResponseBody
    @RequiresAuthentication
    public Map toAddNewRecomCode(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("RecomCodeForm") RecomCodeForm recomCodeForm) {
    
        UserBO userBO = getCurrentUser();
    
        log.info("新增注册码");
        try {
            UcInstDo ucInstDo = instService.getInst(recomCodeForm.getInstId());
            if(ucInstDo == null){
                return returnError("机构不存在");
            }
            if(!StatusConstant.STATUS_ENABLE.equals(ucInstDo.getStatus())){
                return returnError("机构状态不允许生成注册码");
            }
            
            recomCodeService.createRecomCodes(recomCodeForm, userBO);
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("系统异常");
        }
    
        return returnSuccess("注册码已经生成, 数目:" + recomCodeForm.getRecomCodeCount() + "个");
    }
    
    /**
     * 注册码列表 - 页面
     */
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public ModelAndView toListPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("instList", ucInstDoList);
    
        //获取-激活状态下的代理列表
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown("","", StatusConstant.STATUS_ENABLE);
        
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        
        //获取该机构下的所有注册码信息
        Pagination page= recomCodeService.getAllRecomcodes(recomCodeForm);
        recomCodeForm.setPagination(page);
        
        modelAndView.getModel().put("recomCodeListForm", recomCodeForm);
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.getModel().put("agentList", ucAgentDoList);
        modelAndView.setViewName("/recomCode/list_recom_code");
        return modelAndView;
    }
    
    /**
     * 查询注册码列表
     */
    @RequestMapping(value = "/query_recomCode_list", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toQueryRecomCodeList (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeListForm") RecomCodeForm recomCodeForm) {
        
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
    
        //获取-激活状态下的代理列表
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown("","", StatusConstant.STATUS_ENABLE);
    
        //获取该机构下的所有注册码信息
        Pagination page= recomCodeService.getAllRecomcodes(recomCodeForm);
        recomCodeForm.setPagination(page);
    
        modelAndView.getModel().put("recomCodeListForm", recomCodeForm);
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.getModel().put("agentList", ucAgentDoList);
        modelAndView.setViewName("/recomCode/list_recom_code");
        return modelAndView;
    }
    
    /**
     * 查询注册码列表
     */
    @RequestMapping(value = "/query_recomCode_list", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView toQueryRecomCodeListPost (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeListForm") RecomCodeForm recomCodeForm) {

        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);
    
        //获取-激活状态下的代理列表
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown("","", StatusConstant.STATUS_ENABLE);
    
        //获取该机构下的所有注册码信息
        Pagination page= recomCodeService.getAllRecomcodes(recomCodeForm);
        recomCodeForm.setPagination(page);
        modelAndView.getModel().put("recomCodeListForm", recomCodeForm);
        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.getModel().put("agentList", ucAgentDoList);
        modelAndView.setViewName("/recomCode/list_recom_code");
        return modelAndView;

    }

    
    /**
     * 下发注册码 页面
     */
    @RequestMapping(value = "/dispatchPage", method = RequestMethod.GET)
    public ModelAndView toDispatchPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        //获取注册码参数
        String recomCode = request.getParameter("recomCode");
        String userCode = request.getParameter("userCode");
        String userType = request.getParameter("userType");
        UcReccomCodeCntlDo ucReccomCodeCntlDo = recomCodeService.getRecomCode(recomCode);
        if(ucReccomCodeCntlDo == null){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "注册码不存在");
            modelAndView.setViewName("/recomCode/dispatch_recom_code");
            return modelAndView;
        }
        
        //注册码状态
        if(!StatusConstant.RECOMCODE_STATUS_NEW.equals(ucReccomCodeCntlDo.getStatus())){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "注册码状态不允许下发");
            modelAndView.setViewName("/recomCode/dispatch_recom_code");
            return modelAndView;
        }
        
        //必须是机构注册码, 才能下发
        if(!UserConstant.USER_INST.equals(userType)){
            modelAndView.getModel().put("statusCode", 300);
            modelAndView.getModel().put("message", "注册不属于机构,无法下拨");
            modelAndView.setViewName("/recomCode/dispatch_recom_code");
            return modelAndView;
        }
        
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        recomCodeForm.setRecomCode(recomCode);
        if(UserConstant.USER_INST.equals(userType)){
            //机构注册码, 获取机构信息
            UcInstDo ucInstDo = instService.getInst(userCode);
            if(ucInstDo == null){
                modelAndView.getModel().put("statusCode", 300);
                modelAndView.getModel().put("message", "注册码所属机构不存在");
                modelAndView.setViewName("/recomCode/dispatch_recom_code");
                return modelAndView;
            }
            recomCodeForm.setInstId(ucInstDo.getInstId());
            recomCodeForm.setInstName(ucInstDo.getInstName());
        }
        
        //获取该代理下的直属代理
        List<UcAgentDo> ucAgentDoList = agentService.getAgentListForDropDown(userCode,"", StatusConstant.STATUS_ENABLE);
        modelAndView.getModel().put("recomCodeDispatchForm", recomCodeForm);
        modelAndView.getModel().put("toAgentList", ucAgentDoList);
        modelAndView.setViewName("/recomCode/dispatch_recom_code");
        return modelAndView;
    }
    
    
    /**
     * 批量下发注册码 - 页面
     */
    @RequestMapping(value = "/dispatchBatchPage", method = RequestMethod.GET)
    public ModelAndView toDispatchBatchPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
    
        RecomCodeForm recomCodeForm = new RecomCodeForm();
    
        //获取-激活状态下的机构列表
        List<UcInstDo> ucInstDoList = instService.getInstListByStatus(StatusConstant.STATUS_ENABLE);

        modelAndView.getModel().put("instList", ucInstDoList);
        modelAndView.setViewName("/recomCode/dispatch_recom_code_batch");
        return modelAndView;
        
    }
    
    /**
     * 批量下发注册码 - 实现
     */
    @RequestMapping(value = "/dispatch_recomCode_batch", method = RequestMethod.POST)
    @ResponseBody
    public Map toDispatchRemcodeBatch (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeDispatchBatchForm") RecomCodeForm recomCodeForm) {
    
        UserBO userBO = getCurrentUser();
        
        if(StringUtils.isBlank(recomCodeForm.getInstId())){
            return returnError("未选择下发机构");
        }
        UcInstDo ucInstDo = instService.getInst(recomCodeForm.getInstId());
        if(ucInstDo == null){
            return returnError("下发机构不存在");
        }
        if(!StatusConstant.STATUS_ENABLE.equals(ucInstDo.getStatus())){
            return returnError("下发机构状态不允许下发注册码");
        }
        //获取可用的推荐码个数
        int count = recomCodeService.getTotalRecomCodeByInst(recomCodeForm.getInstId(), StatusConstant.STATUS_NEW);
        int dispatchCount = Integer.parseInt(recomCodeForm.getDispatchCount());
        if(count < dispatchCount){
            return returnError("可用的推荐码个数不足满足下发去求");
        }
        
        UcAgentDo toUcAgentDo = agentService.getAgent(recomCodeForm.getToAgentId());
        if(toUcAgentDo == null){
            return returnError("下发目前代理不存在");
        }
        if(!StatusConstant.STATUS_ENABLE.equals(toUcAgentDo.getStatus())){
            return returnError("下发目前代理未激活");
        }
        
        UcAgentLevelDo toAgentLevel = agentService.getAgentLevel(recomCodeForm.getToAgentId());
        if(toAgentLevel == null){
            //如果不存在, 代表是1级
            if(!toUcAgentDo.getInstId().equals(recomCodeForm.getInstId())){
                return returnError("目标代理不属于下发机构");
            }
        } else if(LEVEL_1.equals(toAgentLevel.getAgentLevel())){
            return returnError("只能下发给1级代理");
        }
        
        //从机构下发注册码到1级代理
        try {
            recomCodeService.dispatchRecomCodeBatchFromInst(recomCodeForm.getInstId(), recomCodeForm.getToAgentId(), dispatchCount, userBO.getUsrName());
        } catch (Exception ex){
            ex.printStackTrace();
            return returnError("系统异常");
        }
        return returnSuccess("下发成功");
    }
    
    
    /**
     * 下发选中注册码 - 页面
     */
    @RequestMapping(value = "/dispatchSelectedPage", method = RequestMethod.GET)
    public ModelAndView toDispatchSelectedPage(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {

        String recomParam = request.getParameter("recomCode");
        String instId = request.getParameter("instId");
        
        RecomCodeForm recomCodeForm = new RecomCodeForm();
        recomCodeForm.setToDispatchRecomCodes(recomParam);
        recomCodeForm.setInstId(instId);
        
        //获取-激活状态下的机构列表
        List<UcAgentDo> toAgentList = agentService.getAgentListForDropDown(instId, "", StatusConstant.STATUS_ENABLE, LEVEL_1);
        modelAndView.getModel().put("recomCodeDispatchSelectedForm", recomCodeForm);
        modelAndView.getModel().put("toAgentList", toAgentList);
        modelAndView.setViewName("/recomCode/dispatch_recom_code_selected");
        return modelAndView;
        
    }
    
    /**
     * 批量下发选中注册码 - 实现
     */
    @RequestMapping(value = "/dispatch_recomCode_selected", method = RequestMethod.POST)
    @ResponseBody
    public Map toDispatchRemcodeSelected (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeDispatchSelectedForm") RecomCodeForm recomCodeForm) {
        
        UserBO userBO = getCurrentUser();
        
        UcAgentDo ucAgentDo = agentService.getAgent(recomCodeForm.getToAgentId());
        if(ucAgentDo == null){
            return returnError("下发代理不存在");
        }
        
        if(!StatusConstant.STATUS_ENABLE.equals(ucAgentDo.getStatus())){
            return returnError("下发代理状态不正确");
        }
        
        log.info("下发到代理:" + recomCodeForm.getToAgentId());
        log.info("下发到代理:" + recomCodeForm.getToAgentId());
        String recomCodes = recomCodeForm.getToDispatchRecomCodes();
        String recomP = "";
        if(StringUtils.isNotBlank(recomCodes)){
            recomP = recomCodes.replace("\\u005B", "").replace("\\u005C", "");
        }
        String[] recomArray = recomP.split("\\s");
        log.info("下发到代理的注册码:" + recomArray);
        
        if(recomArray.length <=0){
            return returnError("注册码选中列表为空");
        }
        List<String> recomList = Arrays.asList(recomArray);
    
        //下发选中注册码
        String mesage = "";
        try {
            mesage = recomCodeService.dispatchRecomCodeSelectedFromInst(recomList, recomCodeForm.getAgentId(), userBO.getUsrName());
        } catch (Exception ex){
            ex.printStackTrace();
            return returnError("系统异常");
        }
        return returnSuccess(mesage);
    }
    
    /**
     * 注册码激活
     */
    @RequestMapping(value = "/dispatch", method = RequestMethod.POST)
    @ResponseBody
    public Map toDispatch (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, @ModelAttribute("recomCodeDispatchForm")RecomCodeForm recomCodeForm) {
        
        UserBO userBO = getCurrentUser();
        String recomCode = recomCodeForm.getRecomCode();
        if(StringUtils.isBlank(recomCode)){
            return returnError("注册码为空无法激活");
        }
        
        UcReccomCodeCntlDo ucReccomCodeCntlDo = recomCodeService.getRecomCode(recomCode);
        if(ucReccomCodeCntlDo == null){
            return returnError("注册码非法无法激活");
        }
        if(!StatusConstant.RECOMCODE_STATUS_NEW.equals(ucReccomCodeCntlDo.getStatus())){
            return returnError("注册码非新增无法下拨");
        }
    
        if(StringUtils.isBlank(recomCodeForm.getToAgentId())){
            return returnError("下发目标代理未选择");
        }
        UcAgentDo ucAgentDo = agentService.getAgent(recomCodeForm.getToAgentId());
        if(ucAgentDo == null){
            return returnError("下发目标代理不存在");
        }
        if(!StatusConstant.STATUS_ENABLE.equals(ucAgentDo.getStatus())){
            return returnError("不能下发给未激活代理");
        }
        
        recomCodeService.dispatchRecomCode(recomCode, recomCodeForm.getToAgentId(), userBO);
    
        return returnSuccess("下发成功");
    }
    
    /**
     * 注册码激活
     */
    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    @ResponseBody
    public Map toActivate (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        UserBO userBO = getCurrentUser();
        String recomCode = request.getParameter("recomCode");
        if(StringUtils.isBlank(recomCode)){
            return returnError("注册码为空无法激活");
        }
        
        UcReccomCodeCntlDo ucReccomCodeCntlDo = recomCodeService.getRecomCode(recomCode);
        if(ucReccomCodeCntlDo == null){
            return returnError("注册码非法无法激活");
        }
        if(StatusConstant.RECOMCODE_STATUS_ENABLE.equals(ucReccomCodeCntlDo.getStatus())){
            return returnError("注册码已经激活无法再次激活");
        }
    
        recomCodeService.activateRecomCode(recomCode, userBO);
    
        return returnSuccess("下发成功");
    }
    
    /**
     * 注册码激活
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    @ResponseBody
    public Map toDisable (HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        
        UserBO userBO = getCurrentUser();
        String recomCode = request.getParameter("recomCode");
        if(StringUtils.isBlank(recomCode)){
            return returnError("注册码为空无法挂起");
        }
        
        UcReccomCodeCntlDo ucReccomCodeCntlDo = recomCodeService.getRecomCode(recomCode);
        if(ucReccomCodeCntlDo == null){
            return returnError("注册码非法无法挂起");
        }
        if(StatusConstant.RECOMCODE_STATUS_ENABLE.equals(ucReccomCodeCntlDo.getStatus())){
            return returnError("注册码已经激活无法挂起");
        }
        if(StatusConstant.RECOMCODE_STATUS_DISABLE.equals(ucReccomCodeCntlDo.getStatus())){
            return returnError("注册码已经挂起无法再次挂起");
        }
        if(StatusConstant.RECOMCODE_STATUS_USED.equals(ucReccomCodeCntlDo.getStatus())){
            return returnError("注册码已经被注册无法挂起");
        }
        if(StatusConstant.RECOMCODE_STATUS_MATURED.equals(ucReccomCodeCntlDo.getStatus())){
            return returnError("注册码已经过期无法挂起");
        }
    
        //挂起
        recomCodeService.disableRecomCode(recomCode, userBO);
        
        return returnSuccess("注册码挂起成功");
    }
    
    
    /**
     * 获取推荐码的数量
     */
    @RequestMapping(value = "/checkRecomCodeCount", method = RequestMethod.GET)
    @ResponseBody
    public Map toCheckRecomCodeCount(HttpServletRequest request, HttpServletResponse response) {
        
        Map result = new HashMap();

        String instId = request.getParameter("instId");
        
        int total = recomCodeService.getRecomCodeCount(instId, "");
        int available = recomCodeService.getRecomCodeCount(instId, StatusConstant.STATUS_NEW);
        
        result.put("totalCount", total);
        result.put("availableCount", available);
        
        return result;
        
    }
    
    
}
