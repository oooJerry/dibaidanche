package com.wuwutongkeji.dibaidanche.entity;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class UserInfoEntity {

    private LoginEntity user;
    private LockEntity consumeRecord;
    private boolean hasFreeCard;

    public LoginEntity getUser() {
        return user;
    }

    public void setUser(LoginEntity user) {
        this.user = user;
    }

    public LockEntity getConsumeRecord() {
        return consumeRecord;
    }

    public void setConsumeRecord(LockEntity consumeRecord) {
        this.consumeRecord = consumeRecord;
    }

    public boolean isHasFreeCard() {
        return hasFreeCard;
    }

    public void setHasFreeCard(boolean hasFreeCard) {
        this.hasFreeCard = hasFreeCard;
    }
}
