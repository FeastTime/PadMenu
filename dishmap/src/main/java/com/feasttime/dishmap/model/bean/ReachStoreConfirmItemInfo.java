package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2017/10/29.
 */

public class ReachStoreConfirmItemInfo extends BaseResponseBean{
    String name;
    String number;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
