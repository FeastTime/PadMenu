package com.feasttime.dishmap.im;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by chen on 2018/2/23.
 */

public class ImUtils {
    private static final String TAG = "ImUtils";

    public static void connectImServer(final Context context) {
        String imToken = PreferenceUtil.getStringKey(PreferenceUtil.IM_TOKEN);
        if (TextUtils.isEmpty(imToken)) {
            ToastUtil.showToast(context,"获得的im的token为空", Toast.LENGTH_SHORT);
            return;
        }

        RongIMClient.connect(imToken, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                LogUtil.d(TAG, "Token 错误---onTokenIncorrect---" + '\n');
            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
                LogUtil.d(TAG, "连接融云成功---onSuccess---用户ID:" + userid + '\n');
//                List<Conversation> myList = RongIMClient.getInstance().getConversationList();
//                LogUtil.d(TAG, "=====================");
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.d(TAG, "连接融云失败, 错误码: " + errorCode + '\n');
            }
        });
    }
}
