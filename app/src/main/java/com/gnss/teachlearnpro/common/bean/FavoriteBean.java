package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class FavoriteBean extends BaseResBean {

    /**
     * data : [{"id":59,"title":"直播测试","head_img":"/public/upload/file/20210211/6024a077d6346.jpg","create_time":1614081157,"favorite_id":265,"type":2},{"id":60,"title":"直播23","head_img":"/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614081132,"favorite_id":261,"type":2},{"id":11,"title":"课程4","head_img":"http://zt.baomanyi.net/public/static/upload/file/5f1edf1584af0.png","create_time":1614081098,"favorite_id":264,"type":1},{"id":63,"title":"你好","head_img":"/public/upload/file/20210219/602f6b9db2e90.png","create_time":1614079297,"favorite_id":263,"type":2},{"id":61,"title":"直播测试","head_img":"/public/upload/file/20210219/602f25b7a9894.jpg","create_time":1614052331,"favorite_id":262,"type":2}]
     * count : 0
     */

    private int count;
    /**
     * id : 59
     * title : 直播测试
     * head_img : /public/upload/file/20210211/6024a077d6346.jpg
     * create_time : 1614081157
     * favorite_id : 265
     * type : 2
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
        private String head_img;
        private int create_time;
        private int favorite_id;
        private int type;

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

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getFavorite_id() {
            return favorite_id;
        }

        public void setFavorite_id(int favorite_id) {
            this.favorite_id = favorite_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
