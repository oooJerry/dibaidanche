package com.wuwutongkeji.dibaidanche.entity;

/**
 * Created by Mr.Bai on 17/10/5.
 */

public class ConsumeEntity {

    private long consumeBalance;
    private int consumeCoupon;
    private int cousumeStatus;
    private String rideTime;
    private LoginEntity user;

    public long getConsumeBalance() {
        return consumeBalance;
    }

    public void setConsumeBalance(int consumeBalance) {
        this.consumeBalance = consumeBalance;
    }

    public int getConsumeCoupon() {
        return consumeCoupon;
    }

    public void setConsumeCoupon(int consumeCoupon) {
        this.consumeCoupon = consumeCoupon;
    }

    public int getCousumeStatus() {
        return cousumeStatus;
    }

    public void setCousumeStatus(int cousumeStatus) {
        this.cousumeStatus = cousumeStatus;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public LoginEntity getUser() {
        return user;
    }

    public void setUser(LoginEntity user) {
        this.user = user;
    }
}
