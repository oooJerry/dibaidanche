package com.wuwutongkeji.dibaidanche.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Bai on 17/10/8.
 */

public class QueryDepositEntity {

    private String tradeStatus;
    private long amount;

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
