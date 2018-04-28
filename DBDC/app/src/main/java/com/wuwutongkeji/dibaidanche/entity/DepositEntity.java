package com.wuwutongkeji.dibaidanche.entity;

import java.util.List;

/**
 * Created by Mr.Bai on 2017/9/15.
 */

public class DepositEntity {

    private long deposit;
    private long minBalance;
    private long yearCard;
    private long monthCard;
    private long seasonCard;
    private long sixMonthCard;

    public long getMonthCard() {
        return monthCard;
    }

    public void setMonthCard(long monthCard) {
        this.monthCard = monthCard;
    }

    public long getSeasonCard() {
        return seasonCard;
    }

    public void setSeasonCard(long seasonCard) {
        this.seasonCard = seasonCard;
    }

    public long getSixMonthCard() {
        return sixMonthCard;
    }

    public void setSixMonthCard(long sixMonthCard) {
        this.sixMonthCard = sixMonthCard;
    }

    private List<Long> balanceList;

    public long getDeposit() {
        return deposit;
    }

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public long getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(long minBalance) {
        this.minBalance = minBalance;
    }

    public long getYearCard() {
        return yearCard;
    }

    public void setYearCard(long yearCard) {
        this.yearCard = yearCard;
    }

    public List<Long> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<Long> balanceList) {
        this.balanceList = balanceList;
    }
}
