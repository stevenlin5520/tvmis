package com.steven.television.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TTelevision {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.tv_id
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String tvId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.tv_name
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String tvName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.tv_desc
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String tvDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.tv_screen
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String tvScreen;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.tv_location
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String tvLocation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.tv_type
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private Integer tvType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.tv_length
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private Long tvLength;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.status
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.create_time
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.update_time
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.supplier_id
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String supplierId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.audit_state
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String auditState;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_television.audit_remark
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    private String auditRemark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.tv_id
     *
     * @return the value of t_television.tv_id
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getTvId() {
        return tvId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.tv_id
     *
     * @param tvId the value for t_television.tv_id
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setTvId(String tvId) {
        this.tvId = tvId == null ? null : tvId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.tv_name
     *
     * @return the value of t_television.tv_name
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getTvName() {
        return tvName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.tv_name
     *
     * @param tvName the value for t_television.tv_name
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setTvName(String tvName) {
        this.tvName = tvName == null ? null : tvName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.tv_desc
     *
     * @return the value of t_television.tv_desc
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getTvDesc() {
        return tvDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.tv_desc
     *
     * @param tvDesc the value for t_television.tv_desc
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setTvDesc(String tvDesc) {
        this.tvDesc = tvDesc == null ? null : tvDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.tv_screen
     *
     * @return the value of t_television.tv_screen
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getTvScreen() {
        return tvScreen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.tv_screen
     *
     * @param tvScreen the value for t_television.tv_screen
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setTvScreen(String tvScreen) {
        this.tvScreen = tvScreen == null ? null : tvScreen.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.tv_location
     *
     * @return the value of t_television.tv_location
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getTvLocation() {
        return tvLocation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.tv_location
     *
     * @param tvLocation the value for t_television.tv_location
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setTvLocation(String tvLocation) {
        this.tvLocation = tvLocation == null ? null : tvLocation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.tv_type
     *
     * @return the value of t_television.tv_type
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public Integer getTvType() {
        return tvType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.tv_type
     *
     * @param tvType the value for t_television.tv_type
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setTvType(Integer tvType) {
        this.tvType = tvType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.tv_length
     *
     * @return the value of t_television.tv_length
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public Long getTvLength() {
        return tvLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.tv_length
     *
     * @param tvLength the value for t_television.tv_length
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setTvLength(Long tvLength) {
        this.tvLength = tvLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.status
     *
     * @return the value of t_television.status
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.status
     *
     * @param status the value for t_television.status
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.create_time
     *
     * @return the value of t_television.create_time
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.create_time
     *
     * @param createTime the value for t_television.create_time
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.update_time
     *
     * @return the value of t_television.update_time
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.update_time
     *
     * @param updateTime the value for t_television.update_time
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.supplier_id
     *
     * @return the value of t_television.supplier_id
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.supplier_id
     *
     * @param supplierId the value for t_television.supplier_id
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.audit_state
     *
     * @return the value of t_television.audit_state
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getAuditState() {
        return auditState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.audit_state
     *
     * @param auditState the value for t_television.audit_state
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setAuditState(String auditState) {
        this.auditState = auditState == null ? null : auditState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_television.audit_remark
     *
     * @return the value of t_television.audit_remark
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getAuditRemark() {
        return auditRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_television.audit_remark
     *
     * @param auditRemark the value for t_television.audit_remark
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark == null ? null : auditRemark.trim();
    }

    /**
     * 节目类别
     * 节目：1体育；2动漫；3电影；4娱乐；5访谈；6科教；7农业；8戏剧；9军事；10选秀；11少儿；12经济；13法制；21人文历史；22自然地理
     * 广告：91公益广告；92商业广告
     */
    private int tvCategory;

    public int getTvCategory() {
        return tvCategory;
    }

    public void setTvCategory(int tvCategory) {
        this.tvCategory = tvCategory;
    }
}