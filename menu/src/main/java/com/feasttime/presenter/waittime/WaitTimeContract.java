package com.feasttime.presenter.waittime;

import com.feasttime.model.bean.WaitTimeAdInfo;
import com.feasttime.model.bean.WaitTimeMenuInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;


public interface WaitTimeContract {

    interface IWaitTimeView extends IBaseView {

        void showWaitTimeAD(WaitTimeAdInfo waitTimeAdInfo);
        void showWaitTimeDishList(WaitTimeMenuInfo waitTimeMenuInfo);
    }

    interface IWaitTimePresenter extends IBasePresenter<IWaitTimeView> {
        void getWaitTimeAD(String token);
        void getWaitTimeMenuList(String token, String orderId);
    }

}
