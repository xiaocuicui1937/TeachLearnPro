package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class LeaveMsgBean extends BaseResBean{

    /**
     * data : [{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614045660,"comment_id":117,"type":2,"content":"盛世嫡妃水电费水电费水电费","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614045720,"comment_id":118,"type":2,"content":"测试直播12321","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047280,"comment_id":119,"type":2,"content":"哈哈哈","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047280,"comment_id":120,"type":2,"content":"嘿嘿","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047460,"comment_id":121,"type":2,"content":"测试","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047760,"comment_id":122,"type":2,"content":"ww","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047820,"comment_id":123,"type":2,"content":"长久","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047820,"comment_id":124,"type":2,"content":"哦吼吼","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047820,"comment_id":125,"type":2,"content":"牛逼","like_num":0},{"id":60,"title":"直播23","head_img":"http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg","create_time":1614047880,"comment_id":126,"type":2,"content":"我的心情","like_num":0}]
     * count : 13
     * team_count : 13
     * course_count : 7
     */

    private int count;
    private int team_count;
    private int course_count;
    /**
     * id : 60
     * title : 直播23
     * head_img : http://zt.baomanyi.net/public/upload/file/20210211/6024dc1d4c3ec.jpg
     * create_time : 1614045660
     * comment_id : 117
     * type : 2
     * content : 盛世嫡妃水电费水电费水电费
     * like_num : 0
     */

    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTeam_count() {
        return team_count;
    }

    public void setTeam_count(int team_count) {
        this.team_count = team_count;
    }

    public int getCourse_count() {
        return course_count;
    }

    public void setCourse_count(int course_count) {
        this.course_count = course_count;
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
        private int comment_id;
        private int type;
        private String content;
        private int like_num;

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

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }
    }
}
