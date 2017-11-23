package com.company.core.mapper;

import com.company.core.entity.UcAgentLevelDo;
import com.company.core.entity.UcAgentLevelDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcAgentLevelDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int countByExample(UcAgentLevelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int deleteByExample(UcAgentLevelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String agentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int insert(UcAgentLevelDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int insertSelective(UcAgentLevelDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    List<UcAgentLevelDo> selectByExample(UcAgentLevelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    UcAgentLevelDo selectByPrimaryKey(String agentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UcAgentLevelDo record, @Param("example") UcAgentLevelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UcAgentLevelDo record, @Param("example") UcAgentLevelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UcAgentLevelDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT_LEVEL
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UcAgentLevelDo record);
}