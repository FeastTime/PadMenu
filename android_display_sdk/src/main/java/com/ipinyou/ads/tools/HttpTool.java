package com.ipinyou.ads.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ipinyou.ads.cache.DiskLruCacheHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ipinyou.ads.properties.HttpProperties.URL_CACHE_NAME;


public class HttpTool {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client;
    private static DiskLruCacheHelper diskLruCacheHelper;


    public static String post(Context context, String url, String inputString){

        initDiskLruCacheHelper(context);



        String result = null;

        RequestBody body = RequestBody.create(JSON, inputString);

        if (null == client)

            client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .cacheControl( new CacheControl.Builder().maxAge(1000, TimeUnit.SECONDS).build())
                .url(url)
                .post(body)
                .build();

        try {

            Response response = client.newCall(request).execute();

            if (response.code() == 200){

                result = response.body().string();

                if (null != diskLruCacheHelper)

                    diskLruCacheHelper.put(url, result);

            }

            response.body().close();
            response.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        if (null == result && null != diskLruCacheHelper)

            result = diskLruCacheHelper.getAsString(url);

        return result;
    }

    private static void initDiskLruCacheHelper(Context context){

        if (null == diskLruCacheHelper)

            try {
                diskLruCacheHelper = new DiskLruCacheHelper(context, URL_CACHE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if (info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED)

                // 当前所连接的网络可用
                return true;

        }

        return false;
    }
}
