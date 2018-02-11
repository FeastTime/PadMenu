package com.feasttime.dishmap.model.bean;



/**
 * Created by chen on 2017/11/5.
 */

public class GrobResultInfo extends BaseResponseBean{

    String userID;
    String bid;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
