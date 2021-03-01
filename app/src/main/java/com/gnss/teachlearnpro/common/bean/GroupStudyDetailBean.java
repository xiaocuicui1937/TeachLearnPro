package com.gnss.teachlearnpro.common.bean;

public class GroupStudyDetailBean extends BaseResBean{

    /**
     * id : 54
     * user_id : null
     * time_start : 1611763200
     * time_end : 1611763200
     * room_id : 123123123
     * password : 123456
     * title : 小組
     * img : http://zt.baomanyi.net/public/static/upload/file/20210128/60127c1d88e03.png
     * time : 大幅度
     * status : 2
     * create_time : 2021-01-28 16:56
     * update_time : 2021-01-28 17:14
     * delete_time : 0
     * verify : 1
     * teacher : 261
     * details : <p>士大夫士大夫勝多負少</p>
     * collect_number : 1
     * time_start_ming : 00:00
     * time_end_ming : 00:00
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
        private Object user_id;
        private int time_start;
        private int time_end;
        private int room_id;
        private int password;
        private String title;
        private String img;
        private String time;
        private int status;
        private String create_time;
        private String update_time;
        private int delete_time;
        private int verify;
        private int teacher;
        private String details;
        private int collect_number;
        private String time_start_ming;
        private String time_end_ming;
        private String teacher_name;

        public String getTeacher_name() {
            return teacher_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(int delete_time) {
            this.delete_time = delete_time;
        }

        public int getVerify() {
            return verify;
        }

        public void setVerify(int verify) {
            this.verify = verify;
        }

        public int getTeacher() {
            return teacher;
        }

        public void setTeacher(int teacher) {
            this.teacher = teacher;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getCollect_number() {
            return collect_number;
        }

        public void setCollect_number(int collect_number) {
            this.collect_number = collect_number;
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
