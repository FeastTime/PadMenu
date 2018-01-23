package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 2018/1/15.
 */

public class SetUserInfoActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.activity_set_user_info_nick_name_et)
    EditText nickNameEt;

    @Bind(R.id.activity_set_user_info_nick_sex_et)
    EditText sexEt;

    @Bind(R.id.activity_set_user_info_nick_birthday_et)
    EditText birthdayEt;

    @Bind(R.id.activity_set_user_info_nick_phone_et)
    EditText phoneEt;

    @Bind(R.id.activity_set_user_info_nick_region_et)
    EditText regionEt;

    @Bind(R.id.activity_set_user_info_nick_introduce_et)
    EditText introduceEt;

    @Bind(R.id.activity_set_user_info_nick_save_btn)
    Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_info);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("设置");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
    }

    @OnClick({R.id.activity_set_user_info_nick_save_btn})
    @Override
    public void onClick(View v) {


        if (v == saveBtn) {
            String nickName = nickNameEt.getText().toString();
            String sex = sexEt.getText().toString();
            String birthday = birthdayEt.getText().toString();
            String phone = phoneEt.getText().toString();
            String region = regionEt.getText().toString();
            String introduce = introduceEt.getText().toString();

            if (TextUtils.isEmpty(nickName)) {
                ToastUtil.showToast(this,"请输入昵称", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(sex)) {
                ToastUtil.showToast(this,"请输入性别", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(birthday)) {
                ToastUtil.showToast(this,"请输入出生日期", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast(this,"请输入手机", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(region)) {
                ToastUtil.showToast(this,"请输入地区", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(introduce)) {
                ToastUtil.showToast(this,"请输入个人简介", Toast.LENGTH_SHORT);
                return;
            }


        }
    }

}
