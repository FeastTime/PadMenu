/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feasttime.menu.R;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.user.UserContract;
import com.feasttime.presenter.user.UserPresenter;
import com.feasttime.tools.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity implements View.OnClickListener,UserContract.IUserView{

    UserPresenter userPresenter = new UserPresenter();

    private static final String TAG = "---vc---";

    @Bind(R.id.login_activity_login_btn)
    Button loginBtn;

    @Bind(R.id.login_activity_phone_et)
    EditText phoneEt;

    @Bind(R.id.login_activity_verify_num_et)
    EditText phoneCode;

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[]{userPresenter};
    }

    @Override
    protected void onInitPresenters() {
        userPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initViews() {


        SMSSDK.initSDK(this.getApplicationContext(), "1c86a24bae7d2", "8f972e6dda3098b8fc3ac38f9304fe6c");

        //3.0版本之后的初始化看这里（包括3.0）
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.d(TAG, "/提交验证码成功");
                        final String phone = phoneEt.getText().toString();
                        userPresenter.login(phone);
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        hideLoading();
                        Log.d(TAG, "/获取验证码成功");
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.d(TAG, "/返回支持发送验证码的国家列表");
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    hideLoading();
                    Log.d(TAG, "/((Throwable)data).printStackTrace()");
                    ToastUtil.showToast(LoginActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT);
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @OnClick({R.id.login_activity_login_btn})
    @Override
    public void onClick(View v) {
        if (v == loginBtn) {

            final String phone = phoneEt.getText().toString();
            final String verificationCode = phoneCode.getText().toString();

            if (TextUtils.isEmpty(phone)) {

                ToastUtil.showToast(this,"请输入手机号", Toast.LENGTH_SHORT);
            } else if (TextUtils.isEmpty(verificationCode)) {

                ToastUtil.showToast(this,"请输入验证码", Toast.LENGTH_SHORT);
            } else {

                SMSSDK.submitVerificationCode("86", phone, verificationCode);
            }

        }
    }

    //  获取验证码
    public void getVerificationCode(View view){

        String phone = phoneEt.getText().toString();

        if (TextUtils.isEmpty(phone)) {

            ToastUtil.showToast(this,"请输入手机号", Toast.LENGTH_SHORT);
        }

        showLoading();
        SMSSDK.getVerificationCode("86", phone);
    }

    @Override
    public void loginSuccess() {
        finish();
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
