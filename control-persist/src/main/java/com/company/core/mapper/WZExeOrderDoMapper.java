package com.company.core.mapper;

import com.company.core.entity.WZExeOrderDo;
import com.company.core.entity.WZExeOrderDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WZExeOrderDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    long countByExample(WZExeOrderDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int deleteByExample(WZExeOrderDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int insert(WZExeOrderDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int insertSelective(WZExeOrderDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    List<WZExeOrderDo> selectByExample(WZExeOrderDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    WZExeOrderDo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WZExeOrderDo record, @Param("example") WZExeOrderDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WZExeOrderDo record, @Param("example") WZExeOrderDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WZExeOrderDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WZ_EXE_ORDER
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WZExeOrderDo record);
}