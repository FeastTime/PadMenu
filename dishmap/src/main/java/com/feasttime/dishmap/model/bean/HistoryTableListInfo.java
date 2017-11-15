package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2017/11/15.
 */

public class HistoryTableListInfo {
    int resultCode;
    List<HistoryTableListItemInfo> deskList;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<HistoryTableListItemInfo> getDeskList() {
        return deskList;
    }

    public void setDeskList(List<HistoryTableListItemInfo> deskList) {
        this.deskList = deskList;
    }
}
