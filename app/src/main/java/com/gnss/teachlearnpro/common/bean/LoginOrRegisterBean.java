package com.gnss.teachlearnpro.common.bean;

public class LoginOrRegisterBean extends BaseResBean{


    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6InN4cy00ZjFnMjNhMTJhYSJ9.eyJpc3MiOiJodHRwOlwvXC96dC5iYW9tYW55aS5uZXQiLCJhdWQiOiJodHRwOlwvXC96dC5iYW9tYW55aS5uZXQiLCJqdGkiOiJzeHMtNGYxZzIzYTEyYWEiLCJpYXQiOjE2MTI2NjUyMjIsImV4cCI6MTYxMjc1MTYyMiwidWlkIjoyNjJ9.5mCfEK2EoOVYMtNjw8TZqEV3mqsm8IMfc93nT0rpCzA
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
