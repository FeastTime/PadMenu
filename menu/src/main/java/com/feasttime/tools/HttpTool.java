package com.feasttime.tools;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpTool {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String post(String url, String inputString){

        RequestBody body = RequestBody.create(JSON, inputString);
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();

        Request request = new Request.Builder().url(url).post(body).build();

        Call call = client.newCall(request);

        try {

            Response response = call.execute();

            if (response.code() == 200){

                return response.body().string();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
