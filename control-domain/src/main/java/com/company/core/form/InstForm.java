package com.company.core.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InstForm extends BaseForm {
    
    private String instId;
    private String instType;
    private String instName;
    private String instShortName;
    private String businessLicense;
    private String category;
    private String categoryId;
    private String agentOk;
    private String agentCountLimit;
    private String limitArea;
    private String limitAreaCode;
    private String legalPersonName;
    private String legalPersonPhone;
    private String legalPersonMail;
    private String legalPersonIdType;
    private String legalPersonId;
    private String legalPersonAddress;
    
    private String contactName;
    private String contactPhone;
    private String contactMail;
    private String contactIdType;
    private String contactCertId;
    private String contactAddress;
    
    
}
