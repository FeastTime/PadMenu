package com.ipinyou.ads.bean;


import com.alibaba.fastjson.annotation.JSONField;

public class Geo {
    private boolean GPSEnable;
    private String IP;
    private double Latitude;
    private double Longitude;

    @JSONField(name = "GPSEnable")
    public boolean isGPSEnable() {
        return GPSEnable;
    }

    public void setGPSEnable(boolean GPSEnable) {
        this.GPSEnable = GPSEnable;
    }

    @JSONField(name = "IP")
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    @JSONField(name = "Latitude")
    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    @JSONField(name = "Longitude")
    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
