package com.feasttime.model.bean;

import android.view.MenuItem;

import java.util.List;

/**
 * Created by chen on 2017/4/17.
 */

public class MenuInfo {
    int resultCode;
    String tmpId;
    List<MenuItemInfo> menuList;
    String recordCount;

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

    public List<MenuItemInfo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuItemInfo> dishesList) {
        this.menuList = dishesList;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }
}
