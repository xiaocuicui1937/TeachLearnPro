package com.gnss.teachlearnpro.common.bean;

public class BaseResBean {


    /**
     * code : 0
     * msg : 发送成功！
     * data : []
     */

    private int code;
    private String msg;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
