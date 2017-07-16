package com.ipinyou.ads.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class AdResponse {

    private Ad Ad;
    private String ID;
    private int NoAdReason;
    private List<TraceMsg> TraceMsgs;

    @JSONField(name="Ad")
    public com.ipinyou.ads.bean.Ad getAd() {
        return Ad;
    }

    public void setAd(com.ipinyou.ads.bean.Ad ad) {
        Ad = ad;
    }

    @JSONField(name="ID")
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @JSONField(name="NoAdReason")
    public int getNoAdReason() {
        return NoAdReason;
    }

    public void setNoAdReason(int noAdReason) {
        NoAdReason = noAdReason;
    }

    @JSONField(name="TraceMsgs")
    public List<TraceMsg> getTraceMsgs() {
        return TraceMsgs;
    }

    public void setTraceMsgs(List<TraceMsg> traceMsgs) {
        TraceMsgs = traceMsgs;
    }
}
