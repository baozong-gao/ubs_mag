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
public class RecomCodeForm  extends BaseForm {
    
    private String userId;
    private String instId;
    private String agentId;
    private String count;
    
}
