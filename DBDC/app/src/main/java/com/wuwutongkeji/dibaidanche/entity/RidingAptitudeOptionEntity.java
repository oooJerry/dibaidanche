package com.wuwutongkeji.dibaidanche.entity;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;

/**
 * Created by Mr.Bai on 17/10/7.
 */

public class RidingAptitudeOptionEntity {


    private String consumeId;
    private AppConfig.LockType lockType;

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId;
    }

    public AppConfig.LockType getLockType() {
        return lockType;
    }

    public void setLockType(AppConfig.LockType lockType) {
        this.lockType = lockType;
    }
}
