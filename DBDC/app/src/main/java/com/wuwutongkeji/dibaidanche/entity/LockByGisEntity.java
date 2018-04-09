package com.wuwutongkeji.dibaidanche.entity;

import java.io.Serializable;

/**
 * Created by Mr.Bai on 17/10/1.
 */

public class LockByGisEntity implements Serializable{


    /**
     * bicycleNum : 704110
     * bicyclePower : 83
     * bicyclePoweradc : 100
     * bicycleStatus : NORMAL
     * createTime : 2001-01-01 00:00:00
     * gisType : 1
     * gmtUsingTime : 2017-09-07 18:42:21
     * id : 1348
     * lastAlive : 2001-01-01 00:00:00
     * lat : 45.704502
     * lockNum : 704110
     * lockPassword : 
     * lockStatus : 0
     * lockType : INTELLIGENT_1
     * lon : 126.622581
     * mapType : 1
     * phoneLat : 45.705582
     * phoneLon : 126.624813
     * recentUser : 130585
     * recordTimes : 6
     * updateTime : 2017-09-08 23:15:19
     * usingStatus : BICYCLE_FREE
     */

    private String bicycleNum;
    private String bicyclePower;
    private String bicyclePoweradc;
    private String bicycleStatus;
    private String createTime;
    private String gisType;
    private String gmtUsingTime;
    private String id;
    private String lastAlive;
    private double lat;
    private String lockNum;
    private String lockPassword;
    private String lockStatus;
    private String lockType;
    private double lon;
    private String mapType;
    private String phoneLat;
    private String phoneLon;
    private String recentUser;
    private String recordTimes;
    private String updateTime;
    private String usingStatus;

    public String getBicycleNum() {
        return bicycleNum;
    }

    public void setBicycleNum(String bicycleNum) {
        this.bicycleNum = bicycleNum;
    }

    public String getBicyclePower() {
        return bicyclePower;
    }

    public void setBicyclePower(String bicyclePower) {
        this.bicyclePower = bicyclePower;
    }

    public String getBicyclePoweradc() {
        return bicyclePoweradc;
    }

    public void setBicyclePoweradc(String bicyclePoweradc) {
        this.bicyclePoweradc = bicyclePoweradc;
    }

    public String getBicycleStatus() {
        return bicycleStatus;
    }

    public void setBicycleStatus(String bicycleStatus) {
        this.bicycleStatus = bicycleStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGisType() {
        return gisType;
    }

    public void setGisType(String gisType) {
        this.gisType = gisType;
    }

    public String getGmtUsingTime() {
        return gmtUsingTime;
    }

    public void setGmtUsingTime(String gmtUsingTime) {
        this.gmtUsingTime = gmtUsingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastAlive() {
        return lastAlive;
    }

    public void setLastAlive(String lastAlive) {
        this.lastAlive = lastAlive;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLockNum() {
        return lockNum;
    }

    public void setLockNum(String lockNum) {
        this.lockNum = lockNum;
    }

    public String getLockPassword() {
        return lockPassword;
    }

    public void setLockPassword(String lockPassword) {
        this.lockPassword = lockPassword;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getPhoneLat() {
        return phoneLat;
    }

    public void setPhoneLat(String phoneLat) {
        this.phoneLat = phoneLat;
    }

    public String getPhoneLon() {
        return phoneLon;
    }

    public void setPhoneLon(String phoneLon) {
        this.phoneLon = phoneLon;
    }

    public String getRecentUser() {
        return recentUser;
    }

    public void setRecentUser(String recentUser) {
        this.recentUser = recentUser;
    }

    public String getRecordTimes() {
        return recordTimes;
    }

    public void setRecordTimes(String recordTimes) {
        this.recordTimes = recordTimes;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsingStatus() {
        return usingStatus;
    }

    public void setUsingStatus(String usingStatus) {
        this.usingStatus = usingStatus;
    }
}
