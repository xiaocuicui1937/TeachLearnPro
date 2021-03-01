package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class CourseBean extends BaseResBean {


    /**
     * data : [{"id":18,"title":"课程1","logo":"/public/static/upload/file/20210128/6012169cebdce.jpg","desc":"sdfsdfsd","course_count":1,"catelog_title":"11"},{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"/public/static/upload/file/5f2a197fddcc3.png","desc":"母婴门店如何站在微信直播的风口上、做好微信直播的锦囊妙计","course_count":2,"catelog_title":"第2课：当前母婴店内部人事管理中的常见问题"},{"id":16,"title":"门店如何打造主播与定位","logo":"/public/static/upload/file/5f30dc8b6e676.png","desc":"母婴店网红主播培养指南8个技能带你玩转直播间","course_count":0,"catelog_title":""},{"id":15,"title":"第一课","logo":"/public/static/upload/file/5f30dc6a02afc.png","desc":"的发郭德纲","course_count":0,"catelog_title":""},{"id":14,"title":"却围绕区委日","logo":"/public/static/upload/file/5f30dca9d24e2.png","desc":" 犬瘟热","course_count":0,"catelog_title":""}]
     * count : 7
     */

    private int count;
    /**
     * id : 18
     * title : 课程1
     * logo : /public/static/upload/file/20210128/6012169cebdce.jpg
     * desc : sdfsdfsd
     * course_count : 1
     * catelog_title : 11
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
        private String desc;
        private int course_count;
        private String catelog_title;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getCourse_count() {
            return course_count;
        }

        public void setCourse_count(int course_count) {
            this.course_count = course_count;
        }

        public String getCatelog_title() {
            return catelog_title;
        }

        public void setCatelog_title(String catelog_title) {
            this.catelog_title = catelog_title;
        }
    }
}
