package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2017/10/19.
 */

public class RegisterInfo {
    boolean resultCode;
    String resultMsg;
    String token;

    public boolean isResultCode() {
        return resultCode;
    }

    public void setResultCode(boolean resultCode) {
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
