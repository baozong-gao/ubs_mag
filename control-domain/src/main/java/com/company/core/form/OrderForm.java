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
public class OrderForm extends BaseForm {
    
    private String userType;
    private String userCode;
    private String instId;
    private String instName;
    private String agentId;
    private String agentName;
    
    private String id;
    private String orderId;
    private String userId;
    private String userName;
    private String status;
    private String orderStatus;
    private String processStatus;
    private String totAmount;
    private String totDegree;
    private String totCount;
    private String serviceFee;
    private String chnlFee;
    private String feeRate;
    private String payOrder;
    private String exeBegTime;
    private String exeEndTime;
    
}
