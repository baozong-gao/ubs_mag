package com.company.core.entity;

public class UcFeeDoKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_FEE.USER_TYPE
     *
     * @mbg.generated
     */
    private String userType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_FEE.USER_CODE
     *
     * @mbg.generated
     */
    private String userCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_FEE.CATEGORY_ID
     *
     * @mbg.generated
     */
    private String categoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_FEE.CATEGORY
     *
     * @mbg.generated
     */
    private String category;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_FEE.FEE_TYPE
     *
     * @mbg.generated
     */
    private String feeType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_FEE.USER_TYPE
     *
     * @return the value of UC_FEE.USER_TYPE
     *
     * @mbg.generated
     */
    public String getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_FEE.USER_TYPE
     *
     * @param userType the value for UC_FEE.USER_TYPE
     *
     * @mbg.generated
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_FEE.USER_CODE
     *
     * @return the value of UC_FEE.USER_CODE
     *
     * @mbg.generated
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_FEE.USER_CODE
     *
     * @param userCode the value for UC_FEE.USER_CODE
     *
     * @mbg.generated
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_FEE.CATEGORY_ID
     *
     * @return the value of UC_FEE.CATEGORY_ID
     *
     * @mbg.generated
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_FEE.CATEGORY_ID
     *
     * @param categoryId the value for UC_FEE.CATEGORY_ID
     *
     * @mbg.generated
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_FEE.CATEGORY
     *
     * @return the value of UC_FEE.CATEGORY
     *
     * @mbg.generated
     */
    public String getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_FEE.CATEGORY
     *
     * @param category the value for UC_FEE.CATEGORY
     *
     * @mbg.generated
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_FEE.FEE_TYPE
     *
     * @return the value of UC_FEE.FEE_TYPE
     *
     * @mbg.generated
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_FEE.FEE_TYPE
     *
     * @param feeType the value for UC_FEE.FEE_TYPE
     *
     * @mbg.generated
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }
}