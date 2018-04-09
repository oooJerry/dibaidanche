package com.wuwutongkeji.dibaidanche.entity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public class WalletRecordEntity {


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
        private long amount;
        private String createTime;
        private String successTime;
        private String id;

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSuccessTime() {
            return successTime;
        }

        public void setSuccessTime(String successTime) {
            this.successTime = successTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
