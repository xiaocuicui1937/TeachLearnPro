package com.gnss.teachlearnpro.common.bean;

import com.gnss.teachlearnpro.common.Contact;

public class LiveDetailResBean extends BaseResBean {


    /**
     * id : 55
     * time_start : 09:20:00
     * time_end : 14:00:00
     * push_url : rtmp://93241.livepush.myqcloud.com/live/12021020109101770?txSecret=5522740e00ba7d00dea72ad446ce468d&txTime=6018A679
     * title : 直播
     * title_img : http://zt.baomanyi.net/public/static/upload/file/20210201/601754e49d65d.jpg
     * status : 0
     * make_number : 0
     * details : 胜多负少收到发
     */

    private DataBean data;
    /**
     * data : {"id":55,"time_start":"09:20:00","time_end":"14:00:00","push_url":"rtmp://93241.livepush.myqcloud.com/live/12021020109101770?txSecret=5522740e00ba7d00dea72ad446ce468d&txTime=6018A679","title":"直播","title_img":"http://zt.baomanyi.net/public/static/upload/file/20210201/601754e49d65d.jpg","status":0,"make_number":0,"details":"胜多负少收到发"}
     * count : 0
     */

    private int count;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class DataBean {
        private int id;
        private String time_start;
        private String time_end;
        private String push_url;
        private String title;
        private String title_img;
        private int status;
        private int make_number;
        private String details;
        private String play_url_hls;
        private String play_url_flv;
        private int collection_status;

        public boolean isCollect() {
            return collection_status == Contact.COLLECTED;
        }

        public String getPlay_url_flv() {
            return play_url_flv;
        }

        public String getPlay_url_hls() {
            return play_url_hls;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime_start() {
            return time_start;
        }

        public void setTime_start(String time_start) {
            this.time_start = time_start;
        }

        public String getTime_end() {
            return time_end;
        }

        public void setTime_end(String time_end) {
            this.time_end = time_end;
        }

        public String getPush_url() {
            return push_url;
        }

        public void setPush_url(String push_url) {
            this.push_url = push_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_img() {
            return title_img;
        }

        public void setTitle_img(String title_img) {
            this.title_img = title_img;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getMake_number() {
            return make_number;
        }

        public void setMake_number(int make_number) {
            this.make_number = make_number;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
