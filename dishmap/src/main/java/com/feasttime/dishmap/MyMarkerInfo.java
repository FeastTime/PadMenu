package com.feasttime.dishmap;

import java.io.Serializable;

public class MyMarkerInfo implements Serializable {

    private static final long serialVersionUID = 8633299996744734593L;

    // 店铺Id
    private String storeId;

    // 纬度
    private double latitude;

    // 经度
    private double longitude;

    // 名字
    private String name;

    // 图片
    private int imgId;

    // 描述
    private String description;

    // 地址
    private String address;

    // 电话
    private String phoneNO;

    //构造方法
    public MyMarkerInfo() {
    }

    public MyMarkerInfo(double latitude, double longitude, String storeId, String name, int imgId, String description, String address, String phoneNO) {
        super();
        this.storeId = storeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.imgId = imgId;
        this.description = description;
        this.address = address;
        this.phoneNO = phoneNO;
    }

    @Override
    public String toString() {
        return "MyMarkerInfo{" +
                "storeId='" + storeId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", imgId=" + imgId +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phoneNO='" + phoneNO + '\'' +
                '}';
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }
}
