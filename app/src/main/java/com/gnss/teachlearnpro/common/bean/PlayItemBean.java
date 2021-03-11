package com.gnss.teachlearnpro.common.bean;

public class PlayItemBean {
    public String title;
    public String progress;
    public String url;
    public int id;
    public boolean isCollect;

    public PlayItemBean(int id,String title, String progress, String url,boolean isCollect) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.url = url;
        this.isCollect = isCollect;
    }
}

