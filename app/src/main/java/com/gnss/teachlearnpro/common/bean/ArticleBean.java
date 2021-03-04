package com.gnss.teachlearnpro.common.bean;

public class ArticleBean {
    public String name;
    public String index;
    public int id;
    public ArticleBean(int id, String index,String name) {
        this.id = id;
        this.name = name;
        this.index = index;
    }
}
