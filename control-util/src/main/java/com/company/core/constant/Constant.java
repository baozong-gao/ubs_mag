package com.company.core.constant;

import java.util.Arrays;
import java.util.List;

public class Constant {
    
    
    public final static int RECOM_CODE_LENGTH_6 = 6;
    public final static List<String> EMPTY_LIST = Arrays.asList("BBBBBBBB");
    
    //手续费类型
    public final static String FEE_DEFAULT_FIXED = "DF";
    public final static String FEE_DEFAULT_FIXED_DESC = "默认成本固定单笔";
    public final static String FEE_DEFAULT_RATE = "DR";
    public final static String FEE_DEFAULT_RATE_DESC = "默认成本比例费率";
    public final static String FEE_EFFECTIVE_FIXED = "EF";
    public final static String FEE_EFFECTIVE_FIXED_DESC = "实收固定单笔";
    public final static String FEE_EFFECTIVE_RATE = "ER";
    public final static String FEE_EFFECTIVE_RATE_DESC = "实收比例费率";
    
    
    //用户角色选线
    public final static String INST_ROLE_AUTH_ID = "5";
    public final static String AGENT_ROLE_AUTH_ID = "6";
    public final static String TEST_ROLE_AUTH_ID = "7";
    public final static String MAINTAIN_ROLE_AUTH_ID = "8";
    
    
    //类别category 默认
    public final static String CATEGORY_DEFAULT = "0";
    
    
    //机构类型 默认
    public final static String INST_TYPE_DEFAULT = "0";   //直属机构
    public final static String INST_TYPE_EXTENDS = "1";   //发展机构
    
    //代理类型 默认
    public final static String AGENT_TYPE_DEFAULT = "0";   //直属代理
    public final static String AGENT_TYPE_EXTENDS = "1";   //发展代理
    
}
