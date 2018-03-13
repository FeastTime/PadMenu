package com.feasttime.dishmap.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.activity.WeChatLoginActivity;
import com.feasttime.dishmap.config.GlobalConfig;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chen on 2017/9/20.
 */

public class UtilTools {
    private static final double EARTH_RADIUS = 6378137.0;
    private static final String TAG = "UtilTools";

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
     * 通过webSocket 发送消息
     *
     * @param context Context
     * @param originRequestDataMap map
     */
    public static boolean requestByWebSocket(Context context,Map<String,String> originRequestDataMap) {

        HashMap<String, String> requestData = new HashMap<>();

        requestData.put("userId", PreferenceUtil.getStringKey(PreferenceUtil.USER_ID));
        requestData.put("deviceId", DeviceTool.getIMEI(context));
        requestData.put("token", PreferenceUtil.getStringKey(PreferenceUtil.TOKEN));
        requestData.putAll(originRequestDataMap);

        String requestJson = JSON.toJSONString(requestData);

        Log.d(TAG, "wsRequestUrl " + WebSocketConfig.wsRequestUrl + "requestJson : ");
        Log.d(TAG, "requestJson +     " + requestJson);

        RxWebSocketUtil rxWebSocketUtil = RxWebSocketUtil.getInstance();

        if (null == rxWebSocketUtil
            ||StringUtils.isEmpty(WebSocketConfig.wsRequestUrl)
            || StringUtils.isEmpty(requestJson)
            ){
            Log.d(TAG, "----webSocket  发送信息失败 ----" );
            ToastUtil.showToast(context,"发送信息失败", Toast.LENGTH_SHORT);
            return false;
        }

        rxWebSocketUtil.asyncSend(WebSocketConfig.wsRequestUrl, requestJson);

        return true;
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
            //ToastUtil.showToast(context,"请重新登录", Toast.LENGTH_SHORT);
//            loginWithWeChat(context);
            context.startActivity(new Intent(context, WeChatLoginActivity.class));
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
            return "满减券";
        } else if (type == 2) {
            return "单品券";
        } else {
            return "";
        }
    }

    //计算时间距离现在的天数
    public static String getDaysFromOtherDate(long time) {

        long distance = time - System.currentTimeMillis();

        if(distance <= 0L){
            return "0";
        }

        return 1 + distance/86400000  + "";
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    //格式化日期
    public static String formateDate(long date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String resultDate = dateFormater.format(date);
        return resultDate;
    }

    //格式化日期
    public static String formateDateForChat(long date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String resultDate = dateFormater.format(date);
        return resultDate;
    }


    public static String formateDateForChinese(long date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy年MM月dd日");
        String resultDate = dateFormater.format(date);
        return resultDate;
    }


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
