package com.company.core.service.impl;

import com.company.core.biz.SequenceBiz;
import com.company.core.biz.UCInstBiz;
import com.company.core.constant.ErrorException;
import com.company.core.constant.StatusConstant;
import com.company.core.constant.UserConstant;
import com.company.core.domain.UserBO;
import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcInstInfoDo;
import com.company.core.form.InstForm;
import com.company.core.service.InstService;
import com.company.core.util.ComcomUtils;
import com.company.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service("InstService")
public class InstServiceImpl implements InstService {
    
    @Autowired
    UCInstBiz ucInstBiz;
    @Autowired
    SequenceBiz sequenceBiz;
    
    
    @Override
    public List<UcInstDo> getInstList() {
    
        return ucInstBiz.selectByExample();
        
    }
    
    @Override
    public List<UcInstDo> getInstListByStatus(String status) {
        
        return ucInstBiz.selectByExample();
        
    }
    
    @Override
    @Transactional
    public String createNewInst(InstForm instForm, UserBO userBO) throws Exception {
    
        //创建机构基础信息
        this.createInstBaseInfo(instForm, userBO);
        
        //创建机构详细信息
        this.createInstDetailInfo(instForm, userBO);
        
        return instForm.getInstId();
    }
    
    
    public void createInstBaseInfo(InstForm instForm, UserBO userBO){
        
        //创建机构
        UcInstDo ucInstDo = new UcInstDo();
        ucInstDo.setInstId(sequenceBiz.genInstId());
        ucInstDo.setInstType(instForm.getInstType());
        ucInstDo.setInstName(instForm.getInstName());
        ucInstDo.setStatus(StatusConstant.STATUS_NEW);
        ucInstDo.setCategory(instForm.getCategory());
        ucInstDo.setCategoryId(instForm.getCategoryId());
        ucInstDo.setAgentOk(instForm.getAgentOk());
        BigDecimal bigDecimal = new BigDecimal(instForm.getAgentCountLimit());
        ucInstDo.setAgentCountLimit(bigDecimal);
        ucInstDo.setLimitArea(instForm.getLimitArea());
        ucInstDo.setLimitAreaCode(instForm.getLimitAreaCode());
        ucInstDo.setCreateUser(userBO.getUsrName());
        ucInstDo.setCreateSource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucInstDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucInstDo.setLockVersion(String.valueOf(0));
        ucInstDo.setModifyUser(userBO.getUsrName());
        ucInstDo.setModifySource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucInstDo.setModifyTime(DateUtil.getCurrentDateTime());
    
        int i = ucInstBiz.insertInst(ucInstDo);
        if(i <= 0){
            throw new ErrorException("机构信息入库失败");
        }
        
        instForm.setInstId(ucInstDo.getInstId());
        
    }
    
    public void createInstDetailInfo(InstForm instForm, UserBO userBO){
        
        //存入机构详细信息
        UcInstInfoDo ucInstInfoDo = new UcInstInfoDo();
        ucInstInfoDo.setInstId(instForm.getInstId());
        ucInstInfoDo.setInstName(instForm.getInstName());
        ucInstInfoDo.setInstShortName(instForm.getInstShortName());
        ucInstInfoDo.setBusinessLicense(instForm.getBusinessLicense());
        ucInstInfoDo.setLegalPersonName(instForm.getLegalPersonName());
        ucInstInfoDo.setLegalPersonIdType(instForm.getLegalPersonIdType());
        ucInstInfoDo.setLegalPersonCertId(instForm.getLegalPersonId());
        ucInstInfoDo.setLegalPersonPhone(instForm.getLegalPersonPhone());
        ucInstInfoDo.setLegalPersonMail(instForm.getLegalPersonMail());
        ucInstInfoDo.setLegalPersonAddress(instForm.getLegalPersonAddress());
        ucInstInfoDo.setContactName(instForm.getContactName());
        ucInstInfoDo.setContactIdType(instForm.getContactIdType());
        ucInstInfoDo.setContactCertId(instForm.getContactCertId());
        ucInstInfoDo.setContactPhone(instForm.getContactPhone());
        ucInstInfoDo.setContactMail(instForm.getContactMail());
        ucInstInfoDo.setContactAddress(instForm.getContactAddress());
    
        ucInstInfoDo.setCreateUser(userBO.getUsrName());
        ucInstInfoDo.setCreateSource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucInstInfoDo.setCreateTime(DateUtil.getCurrentDateTime());
        ucInstInfoDo.setModifyUser(userBO.getUsrName());
        ucInstInfoDo.setModifySource(ComcomUtils.getSourceName(Thread.currentThread()));
        ucInstInfoDo.setModifyTime(DateUtil.getCurrentDateTime());
        ucInstInfoDo.setLockVersion(String.valueOf(0));
    
        int c = ucInstBiz.insertInstInfo(ucInstInfoDo);
        if(c <= 0){
            throw new ErrorException("机构详细信息入库失败");
        }
    }
    
    
    @Override
    public UcInstDo getInst(String instId) {
        
        return ucInstBiz.selectByPrimaryKey(instId);

    }
    
    @Override
    public UcInstDo getTheDefaultInst() {
        
        List<UcInstDo> instDos = getInstListByStatus(StatusConstant.STATUS_ENABLE);
        if(instDos != null && instDos.size() >0){
            for(UcInstDo u: instDos){
                if(UserConstant.USER_TYPE_DEFAULT.equals(u.getInstType())){
                    return u;
                }
            }
        }
        return null;
    }
    
    @Override
    public Boolean checkIfDupInstByName(String instName, String instShortName ) {
    
        if(StringUtils.isNotBlank(instShortName)){
            List<UcInstInfoDo> ucInstInfoDos = ucInstBiz.selectByShortName(instName);
            if(ucInstInfoDos !=null && ucInstInfoDos.size() >0){
                return true;
            }
        }
        
        if(StringUtils.isNotBlank(instName)){
            List<UcInstInfoDo> ucInstInfoDos = ucInstBiz.selectByName(instName);
            if(ucInstInfoDos !=null && ucInstInfoDos.size() >0){
                return true;
            }
        }
    

        return false;
    }
    
    
    
    
    
}
