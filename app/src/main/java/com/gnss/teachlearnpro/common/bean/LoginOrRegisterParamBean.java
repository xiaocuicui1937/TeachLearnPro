package com.gnss.teachlearnpro.common.bean;

/**
 * 登录或者注册时候上传的参数
 */
public class LoginOrRegisterParamBean {
    public String phone;
    public String password;
    public String surePassword;
    public int code;//验证码
    public String nickname;
    public int type;//1手机号密码登录2短信登录


    @Override
    public String toString() {
        return "LoginOrRegisterParamBean{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", surePassword='" + surePassword + '\'' +
                ", code=" + code +
                ", nickname='" + nickname + '\'' +
                ", type=" + type +
                '}';
    }
}
