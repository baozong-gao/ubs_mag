package com.company.core.entity;

import lombok.Data;

@Data
public class AccountUserDo {
    
    //UC_USER_AGENT
    private String userId;
    private String agentId;
    private String status;
    private String createSource;
    private String modifySource;
    private String modifyBy;
    private String createTime;
    private String updateTime;
    private String lockVersion;
    
    private UcUserInfoDo ucUserInfoDo;
    
    //UC_USER_INFO
    private String id;
    private String mobile;
    private String loginId;
    private String userName;
    private String nickName;
    private String loginPassword;
    private String payPassword;
    private String handPassword;
    private String salt;
    private String userType;
    private String userStatus;
    private String userRegSource;
    private String userRegDeviceId;
    private String userPromoteSource;
    private String userResv1;
    private String userResv2;
    private String userResv3;
    
    //UC_USER_PROD
    private String prodId;
    private String prodName;
    private String openStatus;
    private String openMethod;
}