package com.feasttime.dishmap.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.config.GlobalConfig;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.TrustAllCerts;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public static final String TAG = "WXEntryActivity";
     //https://api.weixin.qq.com/sns/userinfo?access_token=5_4wJeL4gzb2Rocfb1q33SGeNETOJb9H5G0-3aA11nskmj-hwo2tyUD0b05TNbS5vwcNBeygsfHj6daUyDr2WNz538SFtTLjRGe-84UkfX4Bc&openid=oBpDX0atA8qMDbRN28i-fGlqR0cc&lang=zh_CN
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, GlobalConfig.WECHAT_APPID, false);

        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(final BaseResp resp) {
        String result = "";

        Toast.makeText(this, "baseresp.getType = " + resp.getType(), Toast.LENGTH_SHORT).show();

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //请求微信token数据
                        String code = ((SendAuth.Resp) resp).code;
                        String requestTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + GlobalConfig.WECHAT_APPID + "&secret=" + GlobalConfig.WECHAT_APPSECRET + "&code=" + code + "&grant_type=authorization_code";
                        String responseStr = requestNet(requestTokenUrl);
                        LogUtil.d(TAG,"the response" + responseStr);

                        //请求微信用户数据
                        JSONObject tokenJsonObj = JSON.parseObject(responseStr);
                        String openid = tokenJsonObj.getString("openid");
                        String access_token = tokenJsonObj.getString("access_token");
                        String requestWeChatUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
                        responseStr = requestNet(requestWeChatUserInfoUrl);
                        JSONObject userInfoJsonObj = JSON.parseObject(responseStr);

                        String nickName = userInfoJsonObj.getString("nickname");
                        String headimgurl = userInfoJsonObj.getString("headimgurl");
                        String city = userInfoJsonObj.getString("city");
                        LogUtil.d(TAG,"geted the wechat userinfo:" + nickName + "-" + city + "-" + headimgurl);

                        //上传微信用户数据

                    }
                }).start();

                result = "成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "取消";
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = "不支持";
                break;
            default:
                result = "未知";
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    private void goToGetMsg() {
//		Intent intent = new Intent(this, GetFromWXActivity.class);
//		intent.putExtras(getIntent());
//		startActivity(intent);
        finish();
    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;

        StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);
        finish();
    }


    private String requestNet(String url) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30l, TimeUnit.SECONDS);
        builder.sslSocketFactory(createSSLSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        LogUtil.d(TAG,"requestUrl:" + url);
        try {
            OkHttpClient client = builder.build();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }
}

