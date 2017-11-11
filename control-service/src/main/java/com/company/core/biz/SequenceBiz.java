package com.company.core.biz;

import com.company.core.entity.UcInstDo;
import com.company.core.entity.UcInstDoExample;
import com.company.core.entity.UcInstInfoDo;
import com.company.core.entity.UcInstInfoDoExample;
import com.company.core.mapper.SeqMapper;
import com.company.core.mapper.UcInstDoMapper;
import com.company.core.mapper.UcInstInfoDoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @Author: weiwankun
 * @Date: 2017/11/9
 */
@Service
@Slf4j
public class SequenceBiz {
    
    @Autowired
    SeqMapper seqMapper;
    
    /**
     * 生成INST_ID
     *
     * @return
     */
    public String genInstId() {
        String seq = String.valueOf(seqMapper.getSequenceNextVal("UBS_INST_SEQ"));
        return StringUtils.leftPad(seq, 8, "0");
    }
    
    /**
     * 生成AGENT_ID
     *
     * @return
     */
    public String genAgentId() {
        String seq = String.valueOf(seqMapper.getSequenceNextVal("UBS_AGENT_SEQ"));
        return StringUtils.leftPad(seq, 8, "0");
    }

}
