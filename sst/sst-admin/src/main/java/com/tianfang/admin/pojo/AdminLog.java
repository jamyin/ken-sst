package com.tianfang.admin.pojo;

import java.util.Date;

public class AdminLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.id
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.admin_id
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private String adminId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.admin_account
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private String adminAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.service_name
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private String serviceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.method_name
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private String methodName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.args
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private String args;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.result
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private String result;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.create_time
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.last_update_time
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_admin_log.stat
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.id
     *
     * @return the value of sst_admin_log.id
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.id
     *
     * @param id the value for sst_admin_log.id
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.admin_id
     *
     * @return the value of sst_admin_log.admin_id
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.admin_id
     *
     * @param adminId the value for sst_admin_log.admin_id
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.admin_account
     *
     * @return the value of sst_admin_log.admin_account
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public String getAdminAccount() {
        return adminAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.admin_account
     *
     * @param adminAccount the value for sst_admin_log.admin_account
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount == null ? null : adminAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.service_name
     *
     * @return the value of sst_admin_log.service_name
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.service_name
     *
     * @param serviceName the value for sst_admin_log.service_name
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.method_name
     *
     * @return the value of sst_admin_log.method_name
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.method_name
     *
     * @param methodName the value for sst_admin_log.method_name
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.args
     *
     * @return the value of sst_admin_log.args
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public String getArgs() {
        return args;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.args
     *
     * @param args the value for sst_admin_log.args
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setArgs(String args) {
        this.args = args == null ? null : args.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.result
     *
     * @return the value of sst_admin_log.result
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public String getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.result
     *
     * @param result the value for sst_admin_log.result
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.create_time
     *
     * @return the value of sst_admin_log.create_time
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.create_time
     *
     * @param createTime the value for sst_admin_log.create_time
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.last_update_time
     *
     * @return the value of sst_admin_log.last_update_time
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.last_update_time
     *
     * @param lastUpdateTime the value for sst_admin_log.last_update_time
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_admin_log.stat
     *
     * @return the value of sst_admin_log.stat
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_admin_log.stat
     *
     * @param stat the value for sst_admin_log.stat
     *
     * @mbggenerated Mon Apr 25 10:22:54 CST 2016
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}