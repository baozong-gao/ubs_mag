package com.company.core.mapper;

import com.company.core.entity.UcCategoryDo;
import com.company.core.entity.UcCategoryDoExample;
import com.company.core.entity.UcCategoryDoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcCategoryDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    long countByExample(UcCategoryDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int deleteByExample(UcCategoryDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(UcCategoryDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int insert(UcCategoryDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int insertSelective(UcCategoryDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    List<UcCategoryDo> selectByExample(UcCategoryDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    UcCategoryDo selectByPrimaryKey(UcCategoryDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UcCategoryDo record, @Param("example") UcCategoryDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UcCategoryDo record, @Param("example") UcCategoryDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UcCategoryDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_CATEGORY
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UcCategoryDo record);
}