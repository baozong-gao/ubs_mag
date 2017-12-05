package com.company.core.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountUserForm extends BaseForm {
    
    private String userType;
    private String userCode;
    private String userCodeName;
    private String instId;
    private String instName;
    private String agentId;
    private String agentName;
    
    private String id;
    private String userId;
    private String userName;
    private String status;
    private String mobilePhone;
    
    //费率信息
    private String defaultFeeFixed; //默认单笔固定
    private String defaultFeeRate;  //默认交易费率
    private String EffectiveFeeFixed; //实收交易单笔
    private String EffectiveFeeRate;  //实收费率

    private String categoryName;
    private String categoryIdName;
    
}
