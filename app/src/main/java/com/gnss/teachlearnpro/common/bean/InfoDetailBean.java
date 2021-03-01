package com.gnss.teachlearnpro.common.bean;

public class InfoDetailBean extends BaseResBean {

    /**
     * id : 43062
     * author : 0
     * content : <p>胜多负少的说法司法所发生</p>
     * title : 某某
     * delete_time : 0
     * create_time : 2021-01-26 14:48
     * user_id : 1
     * merchant_id : 0
     * head_img : /public/static/upload/file/20210126/600fbab87d283.jpg
     * status : 3
     * is_recommend : 1
     * sort : 0
     * read_number : 102
     * collect_number : 0
     * is_top : 1
     * excerpt : 水电费水电费
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
        private String author;
        private String content;
        private String title;
        private int delete_time;
        private String create_time;
        private int user_id;
        private int merchant_id;
        private String head_img;
        private int status;
        private int is_recommend;
        private int sort;
        private int read_number;
        private int collect_number;
        private int is_top;
        private String excerpt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(int delete_time) {
            this.delete_time = delete_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(int is_recommend) {
            this.is_recommend = is_recommend;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getRead_number() {
            return read_number;
        }

        public void setRead_number(int read_number) {
            this.read_number = read_number;
        }

        public int getCollect_number() {
            return collect_number;
        }

        public void setCollect_number(int collect_number) {
            this.collect_number = collect_number;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }
    }
}
