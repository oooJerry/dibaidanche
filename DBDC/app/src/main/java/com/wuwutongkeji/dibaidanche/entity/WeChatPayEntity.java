package com.wuwutongkeji.dibaidanche.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Bai on 17/9/23.
 */

public class WeChatPayEntity {


    private XmlEntity xml;

    public XmlEntity getXml() {
        return xml;
    }

    public void setXml(XmlEntity xml) {
        this.xml = xml;
    }

    public static class XmlEntity {
        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
