package com.wuwutongkeji.dibaidanche.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/4.
 */

public class WalletCouponEntity {

    private int recordsTotal;
    private List<DataEntity> data;

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {

        private String userId;
        private String createTime;
        private String updateTime;
        private String fromUserId;
        private String consumeId;
        private String couponType;
        private String couponStatus;
        private String couponSource;
        private String expireTime;
        private String usingTime;
        private String id;
        private long deductionAmount;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getConsumeId() {
            return consumeId;
        }

        public void setConsumeId(String consumeId) {
            this.consumeId = consumeId;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(String couponStatus) {
            this.couponStatus = couponStatus;
        }

        public String getCouponSource() {
            return couponSource;
        }

        public void setCouponSource(String couponSource) {
            this.couponSource = couponSource;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getUsingTime() {
            return usingTime;
        }

        public void setUsingTime(String usingTime) {
            this.usingTime = usingTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getDeductionAmount() {
            return deductionAmount;
        }

        public void setDeductionAmount(long deductionAmount) {
            this.deductionAmount = deductionAmount;
        }
    }
}
