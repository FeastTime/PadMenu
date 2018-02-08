package com.feasttime.dishmap.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.PreferenceUtil;


public class NetReceiver extends BroadcastReceiver {

    public static final String TAG = "WifiReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {

        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if(null == connectivityManager){
                return;
            }

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();



            // 判断是否连接网络
            if (null != networkInfo && networkInfo.isAvailable()) {

                // 如果网络可用  连接webSocket
                // 如果已经登录，启动长连接
                String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);

                if (!TextUtils.isEmpty(userId)){

                    Intent webSocketIntent = new Intent(context, MyService.class);
                    context.startService(webSocketIntent);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}