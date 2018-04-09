package com.wuwutongkeji.dibaidanche.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mr.Bai on 17/9/14.
 */

public class LoginEntity implements Serializable{

    private String area;
    private boolean authId;
    private Date authTime;
    private long balance;
    private String birth;
    private String city;
    private String createTime;
    private String credit;
    private String discountCoupon;
    private String enable;
    private String gender;
    private String id;
    private String idNumber;
    private String identifyCode;
    private String invitationCode;
    private String loginToken;
    private String mobile;
    private String name;
    private String nickname;
    private String photoUrl;
    private boolean payDeposit; // 是否有押金
    private boolean payBalance; // 是否首冲
    private String province;
    private Date registerTime;
    private Date updateTime;
    private int useCount;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isAuthId() {
        return authId;
    }

    public void setAuthId(boolean authId) {
        this.authId = authId;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDiscountCoupon() {
        return discountCoupon;
    }

    public void setDiscountCoupon(String discountCoupon) {
        this.discountCoupon = discountCoupon;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isPayDeposit() {
        return payDeposit;
    }

    public void setPayDeposit(boolean payDeposit) {
        this.payDeposit = payDeposit;
    }

    public boolean isPayBalance() {
        return payBalance;
    }

    public void setPayBalance(boolean payBalance) {
        this.payBalance = payBalance;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }
}
