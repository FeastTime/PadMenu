package com.ipinyou.ads.bean;


import com.alibaba.fastjson.annotation.JSONField;

public class AdRequest {

    private Long AdSlotID;
    private Long SdkID;
    private boolean Secure;
    private boolean Test;
    private boolean Trace;
    private boolean AdRequired;
    private App app;
    private Device device;
    private Geo geo;
    private int AdHeight;
    private int AdWith;


    @JSONField(name = "AdSlotID")
    public Long getAdSlotID() {
        return AdSlotID;
    }

    public void setAdSlotID(Long adSlotID) {
        AdSlotID = adSlotID;
    }

    @JSONField(name = "SdkID")
    public Long getSdkID() {
        return SdkID;
    }

    public void setSdkID(Long sdkID) {
        SdkID = sdkID;
    }

    @JSONField(name = "Secure")
    public boolean isSecure() {
        return Secure;
    }

    public void setSecure(boolean secure) {
        Secure = secure;
    }

    @JSONField(name = "Test")
    public boolean isTest() {
        return Test;
    }

    public void setTest(boolean test) {
        Test = test;
    }

    @JSONField(name = "Trace")
    public boolean isTrace() {
        return Trace;
    }

    public void setTrace(boolean trace) {
        Trace = trace;
    }

    @JSONField(name = "AdRequired")
    public boolean isAdRequired() {
        return AdRequired;
    }

    public void setAdRequired(boolean adRequired) {
        AdRequired = adRequired;
    }

    @JSONField(name = "App")
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @JSONField(name = "Device")
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @JSONField(name = "Geo")
    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    @JSONField(name = "AdHeight")
    public int getAdHeight() {
        return AdHeight;
    }

    public void setAdHeight(int adHeight) {
        AdHeight = adHeight;
    }

    @JSONField(name = "AdWith")
    public int getAdWith() {
        return AdWith;
    }

    public void setAdWith(int adWith) {
        AdWith = adWith;
    }
}
