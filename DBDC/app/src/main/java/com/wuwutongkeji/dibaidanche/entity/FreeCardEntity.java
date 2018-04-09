package com.wuwutongkeji.dibaidanche.entity;

import java.io.Serializable;

/**
 * Created by Mr.Bai on 17/11/19.
 */

public class FreeCardEntity implements Serializable{

    private String cardType;

    private long remainTime;

    private String createTime;

    private String expireTime;

    private String id;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(long remainTime) {
        this.remainTime = remainTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
