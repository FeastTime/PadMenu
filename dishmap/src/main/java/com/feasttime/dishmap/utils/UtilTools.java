package com.feasttime.dishmap.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.config.GlobalConfig;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2017/9/20.
 */

public class UtilTools {
    private static final double EARTH_RADIUS = 6378137.0;

    public static int[] getImageWidthHeight(Context context, int resid){
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),resid,options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth,options.outHeight};
    }


    public static void chenageTextDrawableSize(TextView textView, int drawableId, int width,int height,int direction) {

        Drawable drawable =  textView.getContext().getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, width, height);
        if (direction == 1) {
            textView.setCompoundDrawables(drawable,null, null, null);
        } else if (direction == 2) {
            textView.setCompoundDrawables(null,drawable, null, null);
        } else if (direction == 3) {
            textView.setCompoundDrawables(null,null, drawable, null);
        } else if (direction == 4) {
            textView.setCompoundDrawables(null,null, null, drawable);
        }

    }


    /**
     * 通过websocket 发送消息
     *
     * @param context Context
     * @param originRequestDataMap map
     */
    public static void requestByWebSocket(Context context,Map<String,String> originRequestDataMap) {
        String imei = DeviceTool.getIMEI(context);
        String androidID = DeviceTool.getAndroidId(context);
        String ipv4 = DeviceTool.getIP(context);
        String mac = DeviceTool.getLocalMacAddress(context);

        HashMap<String,String> requestData = new HashMap<String,String>();
        requestData.put("userId", PreferenceUtil.getStringKey(PreferenceUtil.USER_ID));
        requestData.put("deviceId",imei);
        requestData.put("token",PreferenceUtil.getStringKey(PreferenceUtil.TOKEN));

//        requestData.put("mobileNo",PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO));
        requestData.putAll(originRequestDataMap);
        String requestJson = JSON.toJSONString(requestData);

        Log.d("lixiaoqing", "wsRequestUrl +     " + WebSocketConfig.wsRequestUrl);
        Log.d("lixiaoqing", "requestJson +     " + requestJson);

        RxWebSocketUtil rxWebSocketUtil = RxWebSocketUtil.getInstance();
        if (null == rxWebSocketUtil){
            Log.d("lixiaoqing", "----  rxWebSocketUtil is null ----" );
        }
        if (!StringUtils.isEmpty(WebSocketConfig.wsRequestUrl) && !StringUtils.isEmpty(requestJson) && null != rxWebSocketUtil)
            rxWebSocketUtil.asyncSend(WebSocketConfig.wsRequestUrl, requestJson);
    }

    //微信登录
    public static void loginWithWeChat(Context context) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, GlobalConfig.WECHAT_APPID, false);
        api.registerApp(GlobalConfig.WECHAT_APPID);

        if (!api.isWXAppInstalled()) {
            ToastUtil.showToast(context,"您还未安装微信客户端", Toast.LENGTH_SHORT);
            return;
        }

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dishmap_wechat_login";
        api.sendReq(req);
    }

    //检查登录状态如果未登录就去登录
    public static boolean checkLoginStatusAndRelogin(Context context) {
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        if (TextUtils.isEmpty(token)) {
            ToastUtil.showToast(context,"请重新登录", Toast.LENGTH_SHORT);
            loginWithWeChat(context);
            return false;
        } else {
            return true;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // 返回单位是米
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    //根据类型获取券的值
    public static String getCouponStrByType(int type) {
        if (type == 1) {
            return "满减";
        } else if (type == 2) {
            return "菜品券";
        } else {
            return "";
        }
    }

    //计算时间距离现在的天数
    public static String getDaysFromOtherDate(long time) {
        long distance = System.currentTimeMillis() - time;
        return distance/86400000  + "";
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    //格式化日期
    public static String formateDate(long date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy:MM:dd");
        String resultDate = dateFormater.format(date);
        return resultDate;
    }
}
