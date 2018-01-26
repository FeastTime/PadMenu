package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2018/1/26.
 */

public class MyTableInfo {
    int resultCode;
    String resultMsg;

    List<MyTableItemInfo> tablesList;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<MyTableItemInfo> getTablesList() {
        return tablesList;
    }

    public void setTablesList(List<MyTableItemInfo> tablesList) {
        this.tablesList = tablesList;
    }
}
