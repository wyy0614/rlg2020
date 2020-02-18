package com.itdr.pojo;

import java.util.Date;

public class PayInfo {
    private Integer id;

    private Integer uid;

    private Long orderNo;

    private String payPlatform;

    private String plantformNumber;

    private String plantformStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform == null ? null : payPlatform.trim();
    }

    public String getPlantformNumber() {
        return plantformNumber;
    }

    public void setPlantformNumber(String plantformNumber) {
        this.plantformNumber = plantformNumber == null ? null : plantformNumber.trim();
    }

    public String getPlantformStatus() {
        return plantformStatus;
    }

    public void setPlantformStatus(String plantformStatus) {
        this.plantformStatus = plantformStatus == null ? null : plantformStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}