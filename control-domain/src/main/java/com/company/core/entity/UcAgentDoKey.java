package com.company.core.entity;

public class UcAgentDoKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_AGENT.INST_ID
     *
     * @mbggenerated
     */
    private String instId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_AGENT.AGENT_ID
     *
     * @mbggenerated
     */
    private String agentId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_AGENT.INST_ID
     *
     * @return the value of UC_AGENT.INST_ID
     *
     * @mbggenerated
     */
    public String getInstId() {
        return instId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_AGENT.INST_ID
     *
     * @param instId the value for UC_AGENT.INST_ID
     *
     * @mbggenerated
     */
    public void setInstId(String instId) {
        this.instId = instId == null ? null : instId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_AGENT.AGENT_ID
     *
     * @return the value of UC_AGENT.AGENT_ID
     *
     * @mbggenerated
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_AGENT.AGENT_ID
     *
     * @param agentId the value for UC_AGENT.AGENT_ID
     *
     * @mbggenerated
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }
}