package com.feasttime.model;

import com.feasttime.model.bean.CreateOrderInfo;
import com.feasttime.model.bean.DishesCategoryInfo;
import com.feasttime.model.bean.HealthIndexAssessmentInfo;
import com.feasttime.model.bean.IngredientsMenuInfo;
import com.feasttime.model.bean.LoginInfo;
import com.feasttime.model.bean.MenuInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.PayOrderInfo;
import com.feasttime.model.bean.PersonalStatisticsInfo;
import com.feasttime.model.bean.PlaceOrderInfo;
import com.feasttime.model.bean.SilentAd;
import com.feasttime.model.bean.WaitTimeAdInfo;
import com.feasttime.model.bean.WaitTimeMenuInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chen on 2017/4/17.
 */

public interface MenusApi {


      //创建购物车
      @POST("order/createOrder/")
      Observable<CreateOrderInfo> createOrder(@Body RequestBody route);


      //{'categoryId':'1001','storeId':'1000000000','pageNo':'0','pageNum':'3'}
      //获取菜单
      @POST("menu/getMenuList/")
      Observable<MenuInfo> getMenuList(@Body RequestBody route);

      //{'storeId':'1000000000'}
      //获取购物车详单
      @POST("order/getShoppingCartList/")
      Observable<OrderInfo> getShoppingCartList(@Body RequestBody route);


      //获取用户统计数据
      @FormUrlEncoded
      @POST("PersonalStatistics/getPersonalStatisticsDetail/")
      Observable<PersonalStatisticsInfo> getPersonalStatistics(@Field("token") String token);


      //获取菜品类型列表
      @POST("menu/getDishesCategoryList/")
      Observable<DishesCategoryInfo> getDishesCategoryList(@Body RequestBody route);

      //登录
      @POST("user/login/")
      Observable<LoginInfo> login(@Body RequestBody route);


      //添加购物车
      @POST("order/addShoppingCart/")
      Observable<OrderInfo> addShoppingCart(@Body RequestBody route);


      //去除购物车
      @POST("order/removeShoppingCart/")
      Observable<OrderInfo> removeShoppingCart(@Body RequestBody route);

      //下单
      @POST("order/placeOrder/")
      Observable<PlaceOrderInfo> placeOrder(@Body RequestBody route);

      //支付
      @POST("order/payOrder/")
      Observable<PayOrderInfo> payOrder(@Body RequestBody route);


      //获取等待页面广告列表

      @POST("ad/adArray/")
      Observable<WaitTimeAdInfo> getWaitTimeADList(@Body RequestBody requestBody);

      //获取等待页面广告列表

      @POST("--------1")
      Observable<WaitTimeMenuInfo> getWaitTimeMenuList(@Body RequestBody requestBody);

      @POST("menu/getHealthIndexAssessment/")
      Observable<HealthIndexAssessmentInfo> getHealthIndexAssessment(@Body RequestBody requestBody);

      // 获取开屏静默广告
      @POST("ad/silentads/")
      Observable<SilentAd> getSilentAD(@Body RequestBody requestBody);

      // 获取开屏静默广告
      @POST("menu/getIngredientsList/")
      Observable<IngredientsMenuInfo> getIngredientsList(@Body RequestBody requestBody);
}
