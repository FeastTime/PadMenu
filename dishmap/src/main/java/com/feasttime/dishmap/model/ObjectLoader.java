package com.feasttime.dishmap.model;

/**
 * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 * Created by chen on 2018/2/7.
 */


import android.widget.Toast;

import com.feasttime.dishmap.application.MyApplication;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 进行网络请求相关的统一处理
 */

public class ObjectLoader {
    private static final String TAG = "ObjectLoader";
    private static final int TOKEN_INVALID = 201;  //token失效返回码
    /**
     *
     * @param observable
     * @param <T>
     * @return
     */
    protected  <T> Observable<T> observe(Observable<T> observable){
        LogUtil.d(TAG,"start request");
        return observable
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Function<T, T>() {
                @Override
                public T apply(T t) throws Exception {
                    if (t instanceof BaseResponseBean) {
                        BaseResponseBean baseResponseBean = (BaseResponseBean)t;
                        if (baseResponseBean.getResultCode() == TOKEN_INVALID) {
                            //token失效的时候去微信登录
                            //UtilTools.loginWithWeChat(MyApplication.getInstance());
                            ToastUtil.showToastOnUIThread(MyApplication.getInstance(),"用户登录已失效，请重新登录", Toast.LENGTH_SHORT);
                        }
                        LogUtil.d(TAG,"end request" + baseResponseBean.getResultCode());
                    }
                    return t;
                }
            });
    }
}

