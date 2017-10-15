package com.feasttime.dishmap.model;


import com.feasttime.dishmap.model.bean.LoginInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chen on 2017/4/17.
 */

public interface DishMapApi {


    //登录
    @POST("user/login/")
    Observable<LoginInfo> login(@Body RequestBody route);

}
