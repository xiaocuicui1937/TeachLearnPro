package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class GroupStudyBean extends BaseResBean{


    /**
     * data : [{"teacher_name":"教师","id":54,"time_start":1611763200,"time_end":1611763200,"room_id":123123123,"password":123456,"title":"小組","img":"http://zt.baomanyi.net/public/static/upload/file/20210128/60127c1d88e03.png","time":"大幅度","teacher":261,"time_start_ming":"00:00","time_end_ming":"00:00"},{"teacher_name":"教师","id":53,"time_start":2021,"time_end":2021,"room_id":101010,"password":101010,"title":"订单","img":"http://zt.baomanyi.net/public/static/upload/file/20210128/60127aa15e1cc.jpg","time":"","teacher":261,"time_start_ming":"08:33","time_end_ming":"08:33"}]
     * count : 2
     */

    private int count;
    /**
     * teacher_name : 教师
     * id : 54
     * time_start : 1611763200
     * time_end : 1611763200
     * room_id : 123123123
     * password : 123456
     * title : 小組
     * img : http://zt.baomanyi.net/public/static/upload/file/20210128/60127c1d88e03.png
     * time : 大幅度
     * teacher : 261
     * time_start_ming : 00:00
     * time_end_ming : 00:00
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
        private String teacher_name;
        private int id;
        private int time_start;
        private int time_end;
        private int room_id;
        private int password;
        private String title;
        private String img;
        private String time;
        private int teacher;
        private String time_start_ming;
        private String time_end_ming;
        private int status;
        private String wechat_img;

        public String getWechat_img() {
            return wechat_img;
        }

        public int getStatus() {
            return status;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTime_start() {
            return time_start;
        }

        public void setTime_start(int time_start) {
            this.time_start = time_start;
        }

        public int getTime_end() {
            return time_end;
        }

        public void setTime_end(int time_end) {
            this.time_end = time_end;
        }

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public int getPassword() {
            return password;
        }

        public void setPassword(int password) {
            this.password = password;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTeacher() {
            return teacher;
        }

        public void setTeacher(int teacher) {
            this.teacher = teacher;
        }

        public String getTime_start_ming() {
            return time_start_ming;
        }

        public void setTime_start_ming(String time_start_ming) {
            this.time_start_ming = time_start_ming;
        }

        public String getTime_end_ming() {
            return time_end_ming;
        }

        public void setTime_end_ming(String time_end_ming) {
            this.time_end_ming = time_end_ming;
        }
    }
}
