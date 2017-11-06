package com.feasttime.dishmap.model.bean;

/**
 * Created by LiXiaoQing on 2017/11/6.
 */

public class BidResultItem {

    String bidActivityId;
    String bidPrice;
    String userId;
    String bidTime;

    public String getBidActivityId() {
        return bidActivityId;
    }

    public void setBidActivityId(String bidActivityId) {
        this.bidActivityId = bidActivityId;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }


}