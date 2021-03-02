package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class RecentStudyBean extends BaseResBean{

    /**
     * data : [{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"http://zt.baomanyi.net/public/static/upload/file/5f2a197fddcc3.png"},{"id":18,"title":"课程1","logo":"http://zt.baomanyi.net/public/static/upload/file/20210128/6012169cebdce.jpg"}]
     * count : 2
     */

    private int count;
    /**
     * id : 17
     * title : 母婴门店如何站在微信直播的风口上逆境重生
     * logo : http://zt.baomanyi.net/public/static/upload/file/5f2a197fddcc3.png
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
        private String logo;

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
