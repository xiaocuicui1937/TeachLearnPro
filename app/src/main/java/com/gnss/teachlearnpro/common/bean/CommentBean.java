package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class CommentBean extends BaseResBean{

    /**
     * data : [{"nickname":"游客","id":39,"common_id":54,"content":"bucuobucuo","images":"","oppose":"","respond":"","respond_time":"1970-01-01 08:00:00","respond_content":"","status":1,"type":3,"create_time":"2020-08-07 21:09","delete_time":0,"user_id":128,"fraction":0,"like_num":0,"pid":48,"is_top":0}]
     * count : 1
     */

    private int count;
    /**
     * nickname : 游客
     * id : 39
     * common_id : 54
     * content : bucuobucuo
     * images :
     * oppose :
     * respond :
     * respond_time : 1970-01-01 08:00:00
     * respond_content :
     * status : 1
     * type : 3
     * create_time : 2020-08-07 21:09
     * delete_time : 0
     * user_id : 128
     * fraction : 0
     * like_num : 0
     * pid : 48
     * is_top : 0
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
        private String nickname;
        private int id;
        private int common_id;
        private String content;
        private String images;
        private String oppose;
        private String respond;
        private String respond_time;
        private String respond_content;
        private int status;
        private int type;
        private String create_time;
        private int delete_time;
        private int user_id;
        private int fraction;
        private int like_num;
        private int pid;
        private int is_top;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommon_id() {
            return common_id;
        }

        public void setCommon_id(int common_id) {
            this.common_id = common_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getOppose() {
            return oppose;
        }

        public void setOppose(String oppose) {
            this.oppose = oppose;
        }

        public String getRespond() {
            return respond;
        }

        public void setRespond(String respond) {
            this.respond = respond;
        }

        public String getRespond_time() {
            return respond_time;
        }

        public void setRespond_time(String respond_time) {
            this.respond_time = respond_time;
        }

        public String getRespond_content() {
            return respond_content;
        }

        public void setRespond_content(String respond_content) {
            this.respond_content = respond_content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(int delete_time) {
            this.delete_time = delete_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getFraction() {
            return fraction;
        }

        public void setFraction(int fraction) {
            this.fraction = fraction;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }
    }
}
