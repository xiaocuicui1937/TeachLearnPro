package com.gnss.teachlearnpro.common.bean;

public class HomeDetailBean {
    public int id;
    public String title;
    public String logoUrl;
    public String index;
    public String content;
    public String readCount;
    public String collectCount;
    public String author;

    public HomeDetailBean(int id, String title, String logoUrl, String index, String content, String readCount, String collectCount,String author) {
        this.id = id;
        this.title = title;
        this.logoUrl = logoUrl;
        this.index = index;
        this.content = content;
        this.readCount = readCount;
        this.collectCount = collectCount;
        this.author = author;
    }

    public HomeDetailBean(int id,String title, String logoUrl, String index, String content) {
        this.id = id;
        this.title = title;
        this.logoUrl = logoUrl;
        this.index = index;
        this.content = content;
    }
}
