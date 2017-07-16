/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.model.bean;

public class LoginInfo {
    int resultCode;
    String resultMsg;
    String token;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
