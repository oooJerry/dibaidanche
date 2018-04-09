package com.wuwutongkeji.dibaidanche.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/4.
 */

public class CreditEntity {

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

        private int score;
        private String creditType; 
        private String creditDesc; 
        private String createTime;
        private String updateTime;
        private String id;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getCreditType() {
            return creditType;
        }

        public void setCreditType(String creditType) {
            this.creditType = creditType;
        }

        public String getCreditDesc() {
            return creditDesc;
        }

        public void setCreditDesc(String creditDesc) {
            this.creditDesc = creditDesc;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
