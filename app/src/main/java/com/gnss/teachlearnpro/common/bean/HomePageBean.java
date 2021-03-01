package com.gnss.teachlearnpro.common.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomePageBean extends BaseResBean{

    /**
     * information : [{"type":2,"id":20,"banner":"http://zt.baomanyi.net/public/static/upload/file/5f7b00b176800.jpg"},{"type":2,"id":19,"banner":"http://zt.baomanyi.net/public/static/upload/file/5f7b00bd90dd3.jpg"}]
     * class : [{"id":1,"name":"营销"},{"id":4,"name":"管理"},{"id":5,"name":"销售"},{"id":7,"name":"挑货"},{"id":8,"name":"沟通"},{"id":9,"name":"直播"}]
     * live : {"time_start":"08:00:00"}
     * study : [{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"http://zt.baomanyi.net/public/static/upload/file/5f2a197fddcc3.png"},{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"http://zt.baomanyi.net/public/static/upload/file/5f2a197fddcc3.png"}]
     * course : [{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"/public/static/upload/file/5f2a197fddcc3.png"}]
     * course_new : {"id":18,"title":"课程1","logo":"/public/static/upload/file/20210128/6012169cebdce.jpg"}
     * info : [{"id":43062,"title":"某某"}]
     * student : [{"id":55,"title":"ffff","img":"/public/static/upload/file/20210131/60160457311a5.jpg"}]
     */

    private DataBean data;
    /**
     * data : {"information":[{"type":2,"id":20,"banner":"http://zt.baomanyi.net/public/static/upload/file/5f7b00b176800.jpg"},{"type":2,"id":19,"banner":"http://zt.baomanyi.net/public/static/upload/file/5f7b00bd90dd3.jpg"}],"class":[{"id":1,"name":"营销"},{"id":4,"name":"管理"},{"id":5,"name":"销售"},{"id":7,"name":"挑货"},{"id":8,"name":"沟通"},{"id":9,"name":"直播"}],"live":{"time_start":"08:00:00"},"study":[{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"http://zt.baomanyi.net/public/static/upload/file/5f2a197fddcc3.png"},{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"http://zt.baomanyi.net/public/static/upload/file/5f2a197fddcc3.png"}],"course":[{"id":17,"title":"母婴门店如何站在微信直播的风口上逆境重生","logo":"/public/static/upload/file/5f2a197fddcc3.png"}],"course_new":{"id":18,"title":"课程1","logo":"/public/static/upload/file/20210128/6012169cebdce.jpg"},"info":[{"id":43062,"title":"某某"}],"student":[{"id":55,"title":"ffff","img":"/public/static/upload/file/20210131/60160457311a5.jpg"}]}
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
        /**
         * time_start : 08:00:00
         */

        private LiveBean live;
        /**
         * id : 18
         * title : 课程1
         * logo : /public/static/upload/file/20210128/6012169cebdce.jpg
         */

        private CourseNewBean course_new;
        /**
         * type : 2
         * id : 20
         * banner : http://zt.baomanyi.net/public/static/upload/file/5f7b00b176800.jpg
         */

        private List<InformationBean> information;
        /**
         * id : 1
         * name : 营销
         */

        @SerializedName("class")
        private List<ClassBean> classX;

        /**
         * id : 17
         * title : 母婴门店如何站在微信直播的风口上逆境重生
         * logo : http://zt.baomanyi.net/public/static/upload/file/5f2a197fddcc3.png
         */

        private List<StudyBean> study;
        /**
         * id : 17
         * title : 母婴门店如何站在微信直播的风口上逆境重生
         * logo : /public/static/upload/file/5f2a197fddcc3.png
         */

        private List<CourseBean> course;
        /**
         * id : 43062
         * title : 某某
         */

        private List<InfoBean> info;
        /**
         * id : 55
         * title : ffff
         * img : /public/static/upload/file/20210131/60160457311a5.jpg
         */

        private List<StudentBean> student;

        private CourseBean made_new;

        public CourseBean getMade_new() {
            return made_new;
        }

        public LiveBean getLive() {
            return live;
        }

        public void setLive(LiveBean live) {
            this.live = live;
        }

        public CourseNewBean getCourse_new() {
            return course_new;
        }

        public void setCourse_new(CourseNewBean course_new) {
            this.course_new = course_new;
        }

        public List<InformationBean> getInformation() {
            return information;
        }

        public void setInformation(List<InformationBean> information) {
            this.information = information;
        }

        public List<ClassBean> getClassX() {
            return classX;
        }

        public void setClassX(List<ClassBean> classX) {
            this.classX = classX;
        }

        public List<StudyBean> getStudy() {
            return study;
        }

        public void setStudy(List<StudyBean> study) {
            this.study = study;
        }

        public List<CourseBean> getCourse() {
            return course;
        }

        public void setCourse(List<CourseBean> course) {
            this.course = course;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public List<StudentBean> getStudent() {
            return student;
        }

        public void setStudent(List<StudentBean> student) {
            this.student = student;
        }

        public static class LiveBean {
            private String time_start;

            public String getTime_start() {
                return time_start;
            }

            public void setTime_start(String time_start) {
                this.time_start = time_start;
            }
        }

        public static class CourseNewBean {
            private int id;
            private String title;
            private String logo;

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
        }

        public static class InformationBean {
            private int type;
            private int id;
            private String banner;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }
        }

        public static class ClassBean {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class StudyBean {
            private int id;
            private String title;
            private String logo;

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
        }

        public static class CourseBean {
            private int id;
            private String title;
            private String logo;
            private String platform;

            public String getPlatform() {
                return platform;
            }

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
        }

        public static class InfoBean {
            private int id;
            private String title;

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
        }

        public static class StudentBean {
            private int id;
            private String title;
            private String img;

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
        }
    }
}
