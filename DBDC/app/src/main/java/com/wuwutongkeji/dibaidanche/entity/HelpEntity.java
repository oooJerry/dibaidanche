package com.wuwutongkeji.dibaidanche.entity;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public class HelpEntity {

    private String title;
    private String url;

    public HelpEntity(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
