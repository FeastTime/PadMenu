package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2017/11/1.
 */

public class PriceChangeInfo {

    String type;
    String userID;
    String highPrice;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }
}
