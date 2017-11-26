package com.company.core.service;

import com.company.core.domain.UserBO;
import com.company.core.entity.UcReccomCodeCntlDo;
import com.company.core.form.Pagination;
import com.company.core.form.RecomCodeForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
public interface RecomCodeService {
    
    public List<UcReccomCodeCntlDo> getRecomCodeList(UcReccomCodeCntlDo ucReccomCodeCntlDo);
    
    public void createRecomCodes(RecomCodeForm recomCodeForm, UserBO userBO);
    
    public Boolean checkIfRecomCodeExisted(String recom);
    
    UcReccomCodeCntlDo getRecomCode(String recom);
    
    Pagination getAllRecomcodes(RecomCodeForm recomCodeForm);
    
    int getTotalRecomCodeByInst(String instId, String status);
    
    int getRecomCodeCount(String userCode, String status);
    
//    @Transactional
//    void dispatchRecomCodeBatch(String agentId, String toAgentId, int dispatchCount, String user);
    
    @Transactional
    void dispatchRecomCodeBatchFromInst(String instId, String toAgentId, int dispatchCount, String user);
    
    @Transactional
    void dispatchRecomCodeBatchFromAgent(String agentId, String toAgentId, int dispatchCount, String user);
    
    @Transactional
    void dispatchRecomCode(String recomCode, String agentId, UserBO userBO);
    
    @Transactional
    void disableRecomCode(String recomCode, UserBO userBO);
    
    @Transactional
    void activateRecomCode(String recomCode, UserBO userBO);
    
    @Transactional
    String dispatchRecomCodeSelected(List<String> recomCodeList, String toAgentId, String user);
    
    @Transactional
    String activateRecomCodeSelected(List<String> recomCodeList, String user);
    
    @Transactional
    String disableRecomCodeSelected(List<String> recomCodeList, String user);
    
    @Transactional
    String dispatchRecomCodeSelectedFromInst(List<String> recomCodeList, String toAgentId, String user);
    
    @Transactional
    String dispatchRecomCodeSelectedFromAgent(List<String> recomCodeList, String toAgentId, String user);
}
