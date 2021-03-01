package com.gnss.teachlearnpro.common.bean;

public class StudentWitnessDetailResBean extends BaseResBean{


    /**
     * id : 55
     * title : ffff
     * img : http://zt.baomanyi.net/public/static/upload/file/20210131/60160457311a5.jpg
     * details : <p>sdfsdf</p>
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String title;
        private String img;
        private String details;

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

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
