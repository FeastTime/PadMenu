package com.feasttime.dishmap.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.config.GlobalConfig;
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
import com.feasttime.dishmap.utils.DeviceTool;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.TrustAllCerts;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * 整个网络通信服务的启动控制，必须先调用初始化函数才能正常使用网络通信接口
 */
public class RetrofitService {
    private static final String TAG = "RetrofitService";
    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";


    //服务器地址
    private static String BASE_URL = null;


    private static DishMapApi sMenuService;

    // 递增页码
    private static final int INCREASE_PAGE = 20;

    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 20;
    private static final int WRITE_TIMEOUT = 20;

    private RetrofitService() {
        throw new AssertionError();
    }

    private static String deviceID = "";
    private static String ipv4 = "";
    private static String mac = "";
    private static String mobileNO = "";


    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    /**
     * 初始化网络通信服务
     */
    public static void init(Context context) {
        if (GlobalConfig.APP_STATUS == 0) {
            //release
            LogUtil.DEBUG = false;
        } else if (GlobalConfig.APP_STATUS == 1) {
            //test
            LogUtil.DEBUG = true;
            BASE_URL = "https://www.timefeast.com/api/feast-web/";
        } else if (GlobalConfig.APP_STATUS == 2) {
            //development
            LogUtil.DEBUG = true;
            BASE_URL = "http://47.94.16.58:9798/feast-web/";
        }

        deviceID = DeviceTool.getIMEI(context);
        ipv4 = DeviceTool.getIP(context);
        mobileNO = DeviceTool.getPhoneNumber(context);
        mac = DeviceTool.getLocalMacAddress(context);

        //BasicParamsInterceptor aa;

        OkHttpClient.Builder okHttpClient = null;

        if (BASE_URL.startsWith("https")) {
            //构建https 的okhttpclient对象
            okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .addInterceptor(sLoggingInterceptor)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(createSSLSocketFactory())
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
        } else {
            //构建http 的okhttpclient对象
            okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .addInterceptor(sLoggingInterceptor)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        sMenuService = retrofit.create(DishMapApi.class);


    }


    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request().newBuilder()
//                    .addHeader("imei",imei)
//                    .addHeader("androidID",androidID)
//                    .addHeader("mac",mac)
//                    .addHeader("ipv4",ipv4)
                    .addHeader("Content-Type","application/json")
                    .build();

            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                LogUtil.d(TAG, "request.body() == null");
            }
            //打印url信息
            LogUtil.d(TAG,"net info request---> " + request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            LogUtil.d(TAG,"net info response---> " + responseBody.string());
            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    //转换成
    private static void addDeviceInfo(HashMap <String,Object> infoMap) {
        String mobileNO = PreferenceUtil.getStringKey("mobileNO");
        if (!TextUtils.isEmpty(mobileNO)) {
            infoMap.put("mobileNO",mobileNO);
        }
        infoMap.put("deviceId",deviceID);
        infoMap.put("mac",mac);
        infoMap.put("ipv4","");
    }

    private static RequestBody getRequestBody(HashMap<String,Object> infoMap) {
        JSONObject jobj = new JSONObject(infoMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jobj.toString());
        return body;
    }


    public static Observable<LoginInfo> saveWeChatUserInfo(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.saveWeChatUserInfo(getRequestBody(infoMap)));
    }


    public static Observable<BaseResponseBean> checkWeChatUserBindStatus(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.checkWeChatUserBindStatus(getRequestBody(infoMap)));
    }


    public static Observable<BaseResponseBean> saveUserPhone(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.saveUserPhone(getRequestBody(infoMap)));
    }


    public static Observable<RegisterInfo> queryPayTableDetail(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.queryPayTableDetail(getRequestBody(infoMap)));
    }

    public static Observable<CouponInfo> queryCouponList(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.queryCouponList(getRequestBody(infoMap)));
    }


    public static Observable<MyTableInfo> queryMyTableList(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.queryMyTableList(getRequestBody(infoMap)));
    }

    public static Observable<RegisterInfo> getStoreInfo(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.getStoreInfo(getRequestBody(infoMap)));
    }


    public static Observable<BaseResponseBean> setRelationshipWithStore(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.setRelationshipWithStore(getRequestBody(infoMap)));
    }


    public static Observable<QueryUserInfo> queryUserInfo(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.queryUserInfo(getRequestBody(infoMap)));
    }


    public static Observable<RegisterInfo> queryHadEatenStore(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.queryHadEatenStore(getRequestBody(infoMap)));
    }


    public static Observable<BaseResponseBean> feedback(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.feedback(getRequestBody(infoMap)));
    }


    public static Observable<BaseResponseBean> userComeInProc(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.userComeInProc(getRequestBody(infoMap)));
    }

    public static Observable<BaseResponseBean> setTheNumberOfDiners(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.setTheNumberOfDiners(getRequestBody(infoMap)));
    }

    public static Observable<ReceivedRedPackageInfo> takeRedPackage(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.takeRedPackage(getRequestBody(infoMap)));
    }

    public static Observable<StoreInfo> getStoreInfoList(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.getStoreInfoList(getRequestBody(infoMap)));
    }

    public static Observable<StoreInfo> saveUserInfo(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.saveUserInfo(getRequestBody(infoMap)));
    }

    public static Observable<DownloadInfo> upgradeReminding(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.upgradeReminding(getRequestBody(infoMap)));
    }

    public static Observable<BaseResponseBean> countDown(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.countDown(getRequestBody(infoMap)));
    }


    public static Observable<RedPackageDetailItemInfo> queryRedPackageDetail(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.queryRedPackageDetail(getRequestBody(infoMap)));
    }

    public static Observable<RedPackageCountDown> queryRedPackageCountDown(HashMap<String,Object> infoMap){
        addDeviceInfo(infoMap);
        return new ObjectLoader().observe(sMenuService.queryRedPackageCountDown(getRequestBody(infoMap)));
    }

    public static Observable<Long> countdown(long time) {

        if (time < 0) time = 0;

        long countTime = time;

        return Observable.interval(0, 1, TimeUnit.SECONDS)

                .subscribeOn(AndroidSchedulers.mainThread())

                .observeOn(AndroidSchedulers.mainThread())

                .map(new Function<Long, Long>() {

                    @Override
                    public Long apply(Long o) throws Exception {
                        return null;
                    }
                })
                .take(countTime + 1);

    }


}
