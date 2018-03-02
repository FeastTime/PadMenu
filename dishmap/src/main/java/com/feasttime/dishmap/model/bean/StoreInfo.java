package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2018/3/2.
 */

public class StoreInfo extends BaseResponseBean{
    List<StoreItemInfo> stores;

    public List<StoreItemInfo> getStores() {
        return stores;
    }

    public void setStores(List<StoreItemInfo> stores) {
        this.stores = stores;
    }
}
