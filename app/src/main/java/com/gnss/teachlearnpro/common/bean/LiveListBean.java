package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class LiveListBean extends BaseResBean{

    /**
     * data : [{"id":56,"time_start":"00:00:00","time_end":"00:00:00","push_url":"rtmp://93241.livepush.myqcloud.com/live/12021021010495073?txSecret=9a1dc872d3fcb0182142fea87a992dce&txTime=60249B4E","title":"直播1","title_img":"http://zt.baomanyi.net/public/upload/file/20210210/602349bd6d7c0.jpg","status":0,"make_number":0},{"id":57,"time_start":"00:00:00","time_end":"00:00:00","push_url":"rtmp://93241.livepush.myqcloud.com/live/12021021010502858?txSecret=0029a4d1b0f2d61596aaefe1253d8487&txTime=60249B74","title":"直播2","title_img":"http://zt.baomanyi.net/public/upload/file/20210210/602349e5a3a57.jpg","status":0,"make_number":0}]
     * count : 2
     */

    private int count;
    /**
     * id : 56
     * time_start : 00:00:00
     * time_end : 00:00:00
     * push_url : rtmp://93241.livepush.myqcloud.com/live/12021021010495073?txSecret=9a1dc872d3fcb0182142fea87a992dce&txTime=60249B4E
     * title : 直播1
     * title_img : http://zt.baomanyi.net/public/upload/file/20210210/602349bd6d7c0.jpg
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
