package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class StudentWitnessResBean extends BaseResBean{

    /**
     * data : [{"id":55,"title":"ffff","img":"http://zt.baomanyi.net/public/static/upload/file/20210131/60160457311a5.jpg"}]
     * count : 1
     */

    private int count;
    /**
     * id : 55
     * title : ffff
     * img : http://zt.baomanyi.net/public/static/upload/file/20210131/60160457311a5.jpg
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
        private String title;
        private String img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
