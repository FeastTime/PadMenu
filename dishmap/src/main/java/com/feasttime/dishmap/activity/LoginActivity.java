package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.LoginInfo;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;

import org.reactivestreams.Subscription;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;

/**
 * Created by chen on 2017/10/15.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private ImageView backIv;
    private EditText phoneEt;
    private EditText passwordEt;
    private Button submitBtn;
    private TextView registerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        this.findViewById(R.id.title_bar_share_iv).setVisibility(View.GONE);
        this.findViewById(R.id.title_text_tv).setVisibility(View.INVISIBLE);
        TextView titleTv = (TextView)this.findViewById(R.id.title_center_text_tv);
        phoneEt = (EditText)this.findViewById(R.id.activity_login_phone_et);
        passwordEt = (EditText)this.findViewById(R.id.activity_login_password_et);
        submitBtn = (Button)this.findViewById(R.id.activity_login_submit_btn);
        registerTv = (TextView)this.findViewById(R.id.activity_login_register_tv);

        titleTv.setText("登录");
        backIv = (ImageView)this.findViewById(R.id.title_back_iv);
        backIv.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();

        } else if (v == submitBtn) {
            String phone = phoneEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast(this,"手机号不能为空", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(password)) {
                ToastUtil.showToast(this,"密码不能为空", Toast.LENGTH_SHORT);
                return;
            }

            HashMap<String,Object> infoMap = new HashMap<String,Object>();
            infoMap.put("mobileNO",phone);
            PreferenceUtil.setStringKey(PreferenceUtil.MOBILE_NO,phone);
            infoMap.put("pwd",password);
            RetrofitService.login(infoMap).subscribe(new Consumer<LoginInfo>(){
                @Override
                public void accept(LoginInfo loginInfo) throws Exception {
                    //1成功，非1失败
                    if (loginInfo.getResultCode() == 0) {
                        PreferenceUtil.setStringKey(PreferenceUtil.TOKEN,loginInfo.getToken());
                        PreferenceUtil.setStringKey(PreferenceUtil.STORE_ID,loginInfo.getStoreId());
                        PreferenceUtil.setStringKey(PreferenceUtil.USER_TYPE,loginInfo.getUserType());
                        ToastUtil.showToast(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT);
                        finish();
                    } else {
                        ToastUtil.showToast(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    //这里接收onError
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                    //这里接收onComplete。
                }
            });
        } else if (v == registerTv) {
            startActivity(new Intent(this,RegisterActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Disposable subscription = RxWebSocketUtil.getInstance().getWebSocketString("ws://sdfs").subscribe();
        //注销
        if(subscription!=null&&!subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
