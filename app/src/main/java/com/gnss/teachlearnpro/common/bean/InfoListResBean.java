package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class InfoListResBean extends BaseResBean {


    /**
     * data : [{"id":43063,"author":"","title":"奶奶的糖罐罐儿","read_number":1000,"collect_number":0,"head_img":"http://zt.baomanyi.net/public/upload/file/20210209/6021ebbdc64a0.jpg","status":3,"excerpt":" \n黑漆黑漆的桐木箱子，是奶奶的百宝箱。里面装着爷爷奶奶，仅有的换洗衣物，叠得整整齐齐。一卷一卷的布丁布头，一团一团的缝衣针线，还有藏在箱底角角的，黑釉发亮的糖罐罐儿。"},{"id":43062,"author":0,"title":"某某","read_number":103,"collect_number":0,"head_img":"http://zt.baomanyi.net/public/static/upload/file/20210126/600fbab87d283.jpg","status":3,"excerpt":"水电费水电费"}]
     * count : 2
     */

    private int count;
    /**
     * id : 43063
     * author :
     * title : 奶奶的糖罐罐儿
     * read_number : 1000
     * collect_number : 0
     * head_img : http://zt.baomanyi.net/public/upload/file/20210209/6021ebbdc64a0.jpg
     * status : 3
     * excerpt :
     黑漆黑漆的桐木箱子，是奶奶的百宝箱。里面装着爷爷奶奶，仅有的换洗衣物，叠得整整齐齐。一卷一卷的布丁布头，一团一团的缝衣针线，还有藏在箱底角角的，黑釉发亮的糖罐罐儿。
     */

    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String author;
        private String title;
        private int read_number;
        private int collect_number;
        private String head_img;
        private int status;
        private String excerpt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getRead_number() {
            return read_number;
        }

        public void setRead_number(int read_number) {
            this.read_number = read_number;
        }

        public int getCollect_number() {
            return collect_number;
        }

        public void setCollect_number(int collect_number) {
            this.collect_number = collect_number;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }
    }
}
