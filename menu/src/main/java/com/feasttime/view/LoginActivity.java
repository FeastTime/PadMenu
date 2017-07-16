/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.view;

import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends BaseActivity implements View.OnClickListener,UserContract.IUserView{

    UserPresenter userPresenter = new UserPresenter();

    @Bind(R.id.login_activity_login_btn)
    Button loginBtn;

    @Bind(R.id.login_activity_phone_et)
    EditText phoneEt;

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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.login_activity_login_btn})
    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            String phone = phoneEt.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast(this,"请输入手机号", Toast.LENGTH_SHORT);
            } else {
                userPresenter.login(phone);
            }

        }
    }

    @Override
    public void loginSuccess() {
        finish();
    }

    @Override
    public void registerSuccess() {

    }
}
