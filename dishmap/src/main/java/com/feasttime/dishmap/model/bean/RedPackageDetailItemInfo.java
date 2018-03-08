package com.feasttime.dishmap.model.bean;

import java.util.HashMap;

/**
 *
 * Created by li on 2018/1/19.
 */

public class RedPackageDetailItemInfo  extends BaseResponseBean {

    HashMap<String,RedPackageDetail> redPackageDetailMap;

    public HashMap<String, RedPackageDetail> getRedPackageDetailMap() {
        return redPackageDetailMap;
    }

    public void setRedPackageDetailMap(HashMap<String, RedPackageDetail> redPackageDetailMap) {
        this.redPackageDetailMap = redPackageDetailMap;
    }
}
