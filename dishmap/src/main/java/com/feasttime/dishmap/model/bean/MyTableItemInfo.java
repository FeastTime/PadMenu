package com.feasttime.dishmap.model.bean;

import java.io.Serializable;

/**
 * Created by chen on 2018/1/26.
 */

public class MyTableItemInfo implements Serializable{
    String description;
    int isCome;
    long maketableTime;
    int passType;
    int price;
    long recieveTime;
    int storeId;
    String suportSeatNumber;
    String tableId;
    long taketableTime;
    String userIcon;
    String userId;
    String userNickname;
    String userPhone;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsCome() {
        return isCome;
    }

    public void setIsCome(int isCome) {
        this.isCome = isCome;
    }

    public long getMaketableTime() {
        return maketableTime;
    }

    public void setMaketableTime(long maketableTime) {
        this.maketableTime = maketableTime;
    }

    public int getPassType() {
        return passType;
    }

    public void setPassType(int passType) {
        this.passType = passType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getRecieveTime() {
        return recieveTime;
    }

    public void setRecieveTime(long recieveTime) {
        this.recieveTime = recieveTime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getSuportSeatNumber() {
        return suportSeatNumber;
    }

    public void setSuportSeatNumber(String suportSeatNumber) {
        this.suportSeatNumber = suportSeatNumber;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public long getTaketableTime() {
        return taketableTime;
    }

    public void setTaketableTime(long taketableTime) {
        this.taketableTime = taketableTime;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
