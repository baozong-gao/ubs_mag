package com.company.core.mapper;

import com.company.core.entity.WZBalanceInfoDo;
import com.company.core.entity.WZBalanceInfoDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WZBalanceInfoDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    long countByExample(WZBalanceInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int deleteByExample(WZBalanceInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String orgId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int insert(WZBalanceInfoDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int insertSelective(WZBalanceInfoDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    List<WZBalanceInfoDo> selectByExample(WZBalanceInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    WZBalanceInfoDo selectByPrimaryKey(String orgId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WZBalanceInfoDo record, @Param("example") WZBalanceInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WZBalanceInfoDo record, @Param("example") WZBalanceInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WZBalanceInfoDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_BALANCE_INFO
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WZBalanceInfoDo record);
}