package com.ipinyou.ads.bean;


import com.alibaba.fastjson.annotation.JSONField;

public class Device {

    private String Brand;
    private String Carrier;
    private String ID;
    private String Model;
    private String Os;
    private String OsVersion;
    private float PxRatio;
    private int ScreenHeight;
    private int ScreenWidth;
    private String Type;

    @JSONField(name = "Brand")
    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    @JSONField(name = "Carrier")
    public String getCarrier() {
        return Carrier;
    }

    public void setCarrier(String carrier) {
        Carrier = carrier;
    }

    @JSONField(name = "ID")
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @JSONField(name = "Model")
    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    @JSONField(name = "Os")
    public String getOs() {
        return Os;
    }

    public void setOs(String os) {
        Os = os;
    }

    @JSONField(name = "OsVersion")
    public String getOsVersion() {
        return OsVersion;
    }

    public void setOsVersion(String osVersion) {
        OsVersion = osVersion;
    }

    @JSONField(name = "PxRatio")
    public float getPxRatio() {
        return PxRatio;
    }

    public void setPxRatio(float pxRatio) {
        PxRatio = pxRatio;
    }

    @JSONField(name = "ScreenHeight")
    public int getScreenHeight() {
        return ScreenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        ScreenHeight = screenHeight;
    }

    @JSONField(name = "ScreenWidth")
    public int getScreenWidth() {
        return ScreenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        ScreenWidth = screenWidth;
    }

    @JSONField(name = "Type")
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
