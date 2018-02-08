package com.feasttime.dishmap.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
//import android.util.Log;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.SafeExecutor;


public class NetReceiver extends BroadcastReceiver {

    public static final String TAG = "WifiReceiver";
    public static boolean isNetAvailable = false;

    @Override
    public void onReceive(final Context context, Intent intent) {


//        Log.d(TAG, "网络变化");

        new SafeExecutor(NetReceiver.class.getSimpleName(), 1).asyncExecute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateNetState(context);
            }
        });
    }

    private void updateNetState(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(null == connectivityManager){
            return;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // 判断是否连接网络
        if (null != networkInfo && networkInfo.isAvailable()) {

//            Log.d(TAG, "网络  --  可用");

            // 如果网络由不可用变成可用  连接webSocket
            if (!isNetAvailable){

//                Log.d(TAG, "不可用  -->  可用");
                // 如果已经登录，启动长连接
                String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
                if (!TextUtils.isEmpty(userId)){

                    Intent webSocketIntent = new Intent(context, MyService.class);
                    context.startService(webSocketIntent);
                }
            }

            isNetAvailable = true;

        } else {
//            Log.d(TAG, "网络  --  不可用");

            isNetAvailable = false;
        }

    }
}