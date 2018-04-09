package com.wuwutongkeji.dibaidanche.entity;

/**
 * Created by Mr.Bai on 17/9/17.
 */

public class UploadFileEntity {


    private String name;
    private String path;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
