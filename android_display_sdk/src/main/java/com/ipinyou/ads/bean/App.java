package com.ipinyou.ads.bean;


import com.alibaba.fastjson.annotation.JSONField;

public class App {

    private String Bundle;
    private double ID;
    private String Version;

    @JSONField(name = "Bundle")
    public String getBundle() {
        return Bundle;
    }

    public void setBundle(String bundle) {
        Bundle = bundle;
    }

    @JSONField(name = "ID")
    public double getID() {
        return ID;
    }

    public void setID(double ID) {
        this.ID = ID;
    }

    @JSONField(name = "Version")
    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
