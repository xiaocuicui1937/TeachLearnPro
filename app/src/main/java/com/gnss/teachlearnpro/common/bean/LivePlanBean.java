package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class LivePlanBean extends BaseResBean {


    /**
     * data : [{"id":55,"time_start":"09:20:00","time_end":"14:00:00","push_url":"rtmp://93241.livepush.myqcloud.com/live/12021020109101770?txSecret=5522740e00ba7d00dea72ad446ce468d&txTime=6018A679","title":"直播","title_img":"http://picmall.com/public/static/upload/file/20210201/601754e49d65d.jpg","status":0,"make_number":0}]
     * count : 1
     */

    private int count;
    /**
     * id : 55
     * time_start : 09:20:00
     * time_end : 14:00:00
     * push_url : rtmp://93241.livepush.myqcloud.com/live/12021020109101770?txSecret=5522740e00ba7d00dea72ad446ce468d&txTime=6018A679
     * title : 直播
     * title_img : http://picmall.com/public/static/upload/file/20210201/601754e49d65d.jpg
     * status : 0
     * make_number : 0
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
        private String time_start;
        private String time_end;
        private String push_url;
        private String title;
        private String title_img;
        private int status;
        private int make_number;
        private String play_url_flv;
        private String playback_address;

        public String getPlayback_address() {
            return playback_address;
        }

        public String getPlay_url_flv() {
            return play_url_flv;
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
    }
}
