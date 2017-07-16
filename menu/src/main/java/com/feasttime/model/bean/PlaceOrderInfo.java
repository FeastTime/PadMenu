/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.model.bean;

public class PlaceOrderInfo {
    /**
     * imei : 231479832743294
     * androidID : 231479832743294
     * ipv4 : 231479832743294
     * mac : 231479832743294
     * orderID : 2017040721001001240261160865
     */

    private String imei;
    private String androidID;
    private String ipv4;
    private String mac;
    private String orderID;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAndroidID() {
        return androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
