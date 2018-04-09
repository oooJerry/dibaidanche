package com.wuwutongkeji.dibaidanche.entity;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;

/**
 * Created by Mr.Bai on 17/11/19.
 */

public class WalletDepositQueryEntity {

    private AppConfig.LockType lockType;


    public AppConfig.LockType getLockType() {
        return lockType;
    }

    public void setLockType(AppConfig.LockType lockType) {
        this.lockType = lockType;
    }
}
