package com.gnss.teachlearnpro.common;

public class Contact {
    //访问域名
    public static final String BASE_URL = "http://zt.baomanyi.net/Api/";

//    public static final String BASE_PIC_URL = "http://zt.baomanyi.net";

    /**
     * token 对应的HEADER
     */
    public static final String HEADER_TOKEN = "token";

    /**
     * 手机号登录
     */
    public static final int LOGIN_WITH_PHONE = 1;
    /**
     * 短信登录
     */
    public static final int LOGIN_WITH_SMS = 2;

    /**
     * 注册发送验证码
     */
    public static final String VERITY_SMS_TYPE = "reg";

    /**
     * 登录发送验证码
     */
    public static final String VERITY_LOGIN_TYPE = "login";

    /**
     * 登录的TOEKN进行存储
     */
    public static final String TOEKN = "token";

    /**
     * 个人信息
     */
    public static final String PROFILE = "profile";


    /**
     * 我的账号中使用跳转到ProfileDetailActivity区分不同的type
     */
    public static final String PROFILE_DETAIL_TYPE = "profile_detail_type";
    public static final int PROFILE_INFO_TYPE = 1;
    public static final int PROFILE_LOCATION_TYPE = 2;
    public static final int PROFILE_LIVE_RECORD_TYPE = 3;
    public static final int PROFILE_COURSE_RECORD_TYPE = 4;
    public static final int PROFILE_SETTING_TYPE = 5;
    public static final int PROFILE_SHARE_APP_TYPE = 6;
    public static final int PROFILE_ABOUT_ME_TYPE = 7;
    public static final int PROFILE_LEAVE_MSG_TYPE = 8;
    public static final int PROFILE_FAVORITE_TYPE = 9;

    /**
     * 打开记录是直播还是课程
     */
    public static final String RECORD_TYPE = "record_type";
    public static final int RECORD_LIVE = 0;
    public static final int RECORD_COURSE = 1;
    /**
     * 收藏状态
     */
    public static final int COLLECTED = 2;
    public static final int COLLECT_NONE = 1;

    public static final String SHARE_APP_PIC = "share_app.jpg";
    public static final String SWITCH = "switch";
    public static final String COUNT_LIST = "count_list";


    /**
     * 0-------------------------------------首页--------------------------------------------------
     */
    public static final String BANNER_URL = "banner_url";
    public static final String LOGO_URL = "logo_url";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String INDEX = "index";
    public static final String SUB_TITLE = "sub_title";
    public static final String RIGHT_TITLE = "right_title";
    public static final String CONTENT_TITLE = "content_title";
    public static final String CONTENT_SUB_TITLE = "content_sub_title";
    public static final String RIGHT_TOP_DRAWABLE = "right_top_drawable";
    public static final String ARRAY = "array";
    public static final String DESC = "desc";
    /**
     * ----------------------------------直播计划--------------------------------------------------------
     */
    public static final String PlAN_DATE = "plan_date";
    public static final String PlAN_TIME = "plan_time";
    public static final String PlAN_LOCATION = "plan_location";
    public static final String PlAN_GROUP = "plan_group";
    public static final String PlAN_URL = "plan_url";
    public static final String PlAY_URL = "play_url";

    public static final String BLUR = "blur";

    /**
     * 首页详情类型
     */
    public static final String HOME_DETAIL_TYPE = "home_detail_type";

    /**
     * 小组学习
     */
    public static final String GROUP_SELECTED_PARAM = "group_selected_param";

    /**
     * 留言列表类型
     */
    public static final String LEAVE_MSG_TYPE = "leave_msg_type";
    public static final String LEAVE_MSG_DETAIL = "leave_msg_detail";
    /**
     * 选中地址
     * <p>
     * province
     * city
     * area
     */
    public static final int SELECTED_ADDRESS = 0x100;
    public static final int SELECTED_ADDRESS_LOCATION = 0x101;
    public static final String SELECTED_ADDRESS_S = "selected_address";
    public static final String SELECTED_PROVINCE = "selected_province";
    public static final String SELECTED_CITY = "selected_city";
    public static final String SELECTED_AREA = "selected_area";

    /**
     * 头像裁剪地址
     */
    public static final int AVATAR_CORP_PATH = 0x102;

    /**
     * 课程列表包含的容器是否是activity
     */
    public static final String IS_ACTIVITY = "is_activity";
    /**
     * 点赞id
     */
    public static final String ID_RECORDING = "id_recording";
    /**
     * 是否收藏
     */
    public static final String IS_COLLECT = "is_collect";

    public static final int HEAD_TITLE = 0x201;

    public static final int BLUR_VISIBLE = 0x202;

    public static final String PWD = "pwd";
}
