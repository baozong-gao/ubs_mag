package com.company.core.entity;

import lombok.Data;

@Data
public class AccountUserDo {
    
    //UC_USER_AGENT
    private String agentId;
    private String createSource;
    private String lockVersion;
    private String modifyBy;
    private String status;
    private String userId;
    
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
    private String modifySource;
    private String modifyUser;
    private String userResv1;
    private String userResv2;
    private String userResv3;
    private String lockVerison;
    private String createTime;
    private String updateTime;
    
}