package com.gnss.teachlearnpro.common.bean;

public class ProfileBean extends BaseResBean{

    /**
     * id : 128
     * nickname : 陌王
     * avatar : https://wx.qlogo.cn/mmopen/vi_32/EOfoXy5Hib5C3rse3wfEG0Mucnh3lmlbTUlVb9sWEfzRZY8G42QpxPhTP0MicajAoibrnQpbwewKP391sx821b36g/132
     * city_id : 120100
     * area_id : 0
     * province_id : 120000
     * sex : 1
     * province_name : 天津
     * city_name : 天津市
     * course_count : 2
     * live_count : 31
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
        private String nickname;
        private String avatar;
        private int city_id;
        private int area_id;
        private int province_id;

        private int sex;
        private String province_name;
        private String city_name;
        private String area_name;
        private int course_count;
        private int live_count;

        public String getArea_name() {
            return area_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getCourse_count() {
            return course_count;
        }

        public void setCourse_count(int course_count) {
            this.course_count = course_count;
        }

        public int getLive_count() {
            return live_count;
        }

        public void setLive_count(int live_count) {
            this.live_count = live_count;
        }
    }
}
