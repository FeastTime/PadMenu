package com.feasttime.dishmap.model;


import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.CouponInfo;
import com.feasttime.dishmap.model.bean.DownloadInfo;
import com.feasttime.dishmap.model.bean.LoginInfo;
import com.feasttime.dishmap.model.bean.MyTableInfo;
import com.feasttime.dishmap.model.bean.QueryUserInfo;
import com.feasttime.dishmap.model.bean.ReceivedRedPackageInfo;
import com.feasttime.dishmap.model.bean.RedPackageCountDown;
import com.feasttime.dishmap.model.bean.RedPackageDetailItemInfo;
import com.feasttime.dishmap.model.bean.RegisterInfo;
import com.feasttime.dishmap.model.bean.StoreInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by chen on 2017/4/17.
 */

public interface DishMapApi {

    //保存微信用户信息
    @POST("user/saveWeChatUserInfo")
    Observable<LoginInfo> saveWeChatUserInfo(@Body RequestBody route);

    //检查微信用户是否已经绑定过
    @POST("user/checkWeChatUserBindStatus")
    Observable<BaseResponseBean> checkWeChatUserBindStatus(@Body RequestBody route);

    //保存用户手机号
    @POST("user/saveUserPhone")
    Observable<BaseResponseBean> saveUserPhone(@Body RequestBody route);

    //查询付费桌位详情
    @POST("table/queryPayTableDetail")
    Observable<RegisterInfo> queryPayTableDetail(@Body RequestBody route);

    //查询优惠券列表信息
    @POST("coupon/queryCouponList")
    Observable<CouponInfo> queryCouponList(@Body RequestBody route);

    //查询已得到桌位列表
    @POST("table/queryMyTableList")
    Observable<MyTableInfo> queryMyTableList(@Body RequestBody route);

    //查询商家详细信息
    @POST("store/getStoreInfo")
    Observable<RegisterInfo> getStoreInfo(@Body RequestBody route);

    //设置用户与商家关系（设置免打扰，设置关注&聊天，设置不再关注）
    @POST("user/setRelationshipWithStore")
    Observable<BaseResponseBean> setRelationshipWithStore(@Body RequestBody route);

    //查询用户信息
    @POST("user/queryUserInfo")
    Observable<QueryUserInfo> queryUserInfo(@Body RequestBody route);

    //查询去过的餐厅
    @POST("user/queryHadEatenStore")
    Observable<RegisterInfo> queryHadEatenStore(@Body RequestBody route);

    //意见反馈接口
    @POST("user/feedback")
    Observable<BaseResponseBean> feedback(@Body RequestBody route);

    //用户进店
    @POST("user/userComeInProc")
    Observable<BaseResponseBean> userComeInProc(@Body RequestBody route);

    //修改就餐人数
    @POST("user/setTheNumberOfDiners")
    Observable<BaseResponseBean> setTheNumberOfDiners(@Body RequestBody route);

    //拆红包
    @POST("redPackage/takeRedPackage")
    Observable<ReceivedRedPackageInfo> takeRedPackage(@Body RequestBody route);

    //查询多个店铺信息
    @POST("store/getStoreInfoList")
    Observable<StoreInfo> getStoreInfoList(@Body RequestBody route);

    //保存用户信息
    @POST("user/saveUserInfo")
    Observable<StoreInfo> saveUserInfo(@Body RequestBody route);

    //版本升级
    @POST("version/upgradeReminding")
    Observable<DownloadInfo> upgradeReminding(@Body RequestBody route);

    //查询红包详情
    @POST("redPackage/queryRedPackageDetail")
    Observable<RedPackageDetailItemInfo> queryRedPackageDetail(@Body RequestBody route);

    //查询红包倒计时
    @POST("redPackage/countDown")
    Observable<RedPackageCountDown> queryRedPackageCountDown(@Body RequestBody route);
}
