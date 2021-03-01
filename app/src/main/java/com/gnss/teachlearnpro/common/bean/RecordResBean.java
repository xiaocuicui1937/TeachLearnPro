package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class RecordResBean extends BaseResBean {

    /**
     * data : [{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"/public/static/upload/file/5f2a197fddcc3.png","create_time":"2020-07-10 01:08"}]
     * count : 2
     */

    private int count;
    /**
     * id : 17
     * title : 母婴门店如何站在微信直播的风口上逆境重生
     * logo : /public/static/upload/file/5f2a197fddcc3.png
     * create_time : 2020-07-10 01:08
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
        private String create_time;

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
