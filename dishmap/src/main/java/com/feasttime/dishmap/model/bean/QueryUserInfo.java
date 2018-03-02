package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2018/1/23.
 */

public class QueryUserInfo extends BaseResponseBean{

    QueryUserDetailInfo user;

    public QueryUserDetailInfo getUser() {
        return user;
    }

    public void setUser(QueryUserDetailInfo user) {
        this.user = user;
    }

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


}
