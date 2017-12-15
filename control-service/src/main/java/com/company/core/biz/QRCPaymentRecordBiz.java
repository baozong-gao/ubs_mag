package com.company.core.biz;

import com.company.core.entity.QrcPaymentRecordDo;
import com.company.core.entity.QrcPaymentRecordDoExample;
import com.company.core.entity.WZExeOrderDo;
import com.company.core.entity.WZExeOrderDoExample;
import com.company.core.mapper.QrcPaymentRecordDoMapper;
import com.company.core.mapper.WZExeOrderDoMapper;
import lombok.extern.slf4j.Slf4j;
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
public class QRCPaymentRecordBiz {

    @Autowired
    QrcPaymentRecordDoMapper qrcPaymentRecordDoMapper;

    
    public List<QrcPaymentRecordDo> selectByExample(QrcPaymentRecordDoExample qrcPaymentRecordDoExample){
        return qrcPaymentRecordDoMapper.selectByExample(qrcPaymentRecordDoExample);
    }
    
    public QrcPaymentRecordDo selectByPrimaryKey(String id){
        return qrcPaymentRecordDoMapper.selectByPrimaryKey(id);
    }
    
    public long countByExample(QrcPaymentRecordDoExample qrcPaymentRecordDoExample){
        return qrcPaymentRecordDoMapper.countByExample(qrcPaymentRecordDoExample);
    }
    
}
