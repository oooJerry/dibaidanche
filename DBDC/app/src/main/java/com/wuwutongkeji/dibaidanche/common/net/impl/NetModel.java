package com.wuwutongkeji.dibaidanche.common.net.impl;

/**
 * Created by Mr.Bai on 2017/9/11.
 */
public class NetModel<T> {


    private String desc;
    private String returnCode;
    private boolean success;
    private T data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
