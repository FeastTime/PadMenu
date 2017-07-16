package com.feasttime.model.bean;

import android.view.MenuItem;

import java.util.List;

/**
 * Created by chen on 2017/4/17.
 */

public class MenuInfo {
    int resultCode;
    String tmpId;
    List<MenuItemInfo> dishesList;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getTmpId() {
        return tmpId;
    }

    public void setTmpId(String tmpId) {
        this.tmpId = tmpId;
    }

    public List<MenuItemInfo> getDishesList() {
        return dishesList;
    }

    public void setDishesList(List<MenuItemInfo> dishesList) {
        this.dishesList = dishesList;
    }
}
