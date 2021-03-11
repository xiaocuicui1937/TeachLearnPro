package com.gnss.teachlearnpro.common.bean;

import java.util.List;

public class CourseDetailBean extends BaseResBean{


    /**
     * course : {"id":18,"logo":"http://zt.baomanyi.net/public/static/upload/file/20210128/6012169cebdce.jpg","title":"课程1","desc":"sdfsdfsd","lecturer_name":"","lecturer_head":"","lecturer_quality":"","lecturer_desc":"","intro":"<p>水电费水电费水电费是否<\/p>","total":100,"collection_status":1,"new_total":1,"total_user":2}
     * catalog_list : [{"id":13,"course_id":18,"title":11,"type":2,"url":"https://app-1301194689.cos.ap-nanjing.myqcloud.com/96971202101310859496205.mov","time":"","create_time":"2021-01-31 08:57"}]
     * user : [{"id":262,"avatar":"http://zt.baomanyi.net/public/upload/file/20210303/603eea49db478.jpg"},{"id":263,"avatar":""}]
     * user_count : 2
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 18
         * logo : http://zt.baomanyi.net/public/static/upload/file/20210128/6012169cebdce.jpg
         * title : 课程1
         * desc : sdfsdfsd
         * lecturer_name :
         * lecturer_head :
         * lecturer_quality :
         * lecturer_desc :
         * intro : <p>水电费水电费水电费是否</p>
         * total : 100
         * collection_status : 1
         * new_total : 1
         * total_user : 2
         */

        private CourseBean course;
        private int user_count;
        /**
         * id : 13
         * course_id : 18
         * title : 11
         * type : 2
         * url : https://app-1301194689.cos.ap-nanjing.myqcloud.com/96971202101310859496205.mov
         * time :
         * create_time : 2021-01-31 08:57
         */

        private List<CatalogListBean> catalog_list;
        /**
         * id : 262
         * avatar : http://zt.baomanyi.net/public/upload/file/20210303/603eea49db478.jpg
         */

        private List<UserBean> user;

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public int getUser_count() {
            return user_count;
        }

        public void setUser_count(int user_count) {
            this.user_count = user_count;
        }

        public List<CatalogListBean> getCatalog_list() {
            return catalog_list;
        }

        public void setCatalog_list(List<CatalogListBean> catalog_list) {
            this.catalog_list = catalog_list;
        }

        public List<UserBean> getUser() {
            return user;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }

        public static class CourseBean {
            private int id;
            private String logo;
            private String title;
            private String desc;
            private String lecturer_name;
            private String lecturer_head;
            private String lecturer_quality;
            private String lecturer_desc;
            private String intro;
            private int total;
            private int collection_status;
            private int new_total;
            private int total_user;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLecturer_name() {
                return lecturer_name;
            }

            public void setLecturer_name(String lecturer_name) {
                this.lecturer_name = lecturer_name;
            }

            public String getLecturer_head() {
                return lecturer_head;
            }

            public void setLecturer_head(String lecturer_head) {
                this.lecturer_head = lecturer_head;
            }

            public String getLecturer_quality() {
                return lecturer_quality;
            }

            public void setLecturer_quality(String lecturer_quality) {
                this.lecturer_quality = lecturer_quality;
            }

            public String getLecturer_desc() {
                return lecturer_desc;
            }

            public void setLecturer_desc(String lecturer_desc) {
                this.lecturer_desc = lecturer_desc;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public boolean isCollect() {
                return collection_status==2;
            }

            public void setCollection_status(int collection_status) {
                this.collection_status = collection_status;
            }

            public int getNew_total() {
                return new_total;
            }

            public void setNew_total(int new_total) {
                this.new_total = new_total;
            }

            public int getTotal_user() {
                return total_user;
            }

            public void setTotal_user(int total_user) {
                this.total_user = total_user;
            }
        }

        public static class CatalogListBean {
            private int id;
            private int course_id;
            private String title;
            private int type;
            private String url;
            private String time;
            private String create_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }

        public static class UserBean {
            private int id;
            private String avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
