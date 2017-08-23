/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.presenter.user;

import com.feasttime.model.RetrofitService;
import com.feasttime.model.bean.CreateOrderInfo;
import com.feasttime.model.bean.LoginInfo;
import com.feasttime.tools.LogUtil;
import com.feasttime.tools.PreferenceUtil;

import java.util.HashMap;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class UserPresenter implements UserContract.IUserPresenter {
    private UserContract.IUserView iUserView;

    @Override
    public void register(String mobileNO) {

    }

    @Override
    public void login(final String mobileNO) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("mobileNO",mobileNO);
        RetrofitService.login(infoMap).subscribe(new Consumer<LoginInfo>(){
            @Override
            public void accept(LoginInfo loginInfo) throws Exception {
                //1成功，非1失败
                if (loginInfo.getResultCode() == 1) {
                    PreferenceUtil.setStringKey("token",loginInfo.getToken());
                    PreferenceUtil.setStringKey(PreferenceUtil.MOBILE_NO,mobileNO);
                    PreferenceUtil.setStringKey(PreferenceUtil.STORE_ID,loginInfo.getStoreId());
                    iUserView.loginSuccess();
                } else {
                    iUserView.showNetError();

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                LogUtil.d("result","error");
                iUserView.showNetError();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //这里接收onComplete。
                LogUtil.d("result","complete");
            }
        });
    }

    @Override
    public void init(UserContract.IUserView view) {
        this.iUserView = view;
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }
}
