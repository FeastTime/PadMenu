package com.feasttime.model.bean;


import com.ipinyou.ads.bean.Ad;

import java.util.List;

public class SilentAd {

    String ID;
    List<Ad> Ads;
    boolean success;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Ad> getAds() {
        return Ads;
    }

    public void setAds(List<Ad> ads) {
        Ads = ads;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
