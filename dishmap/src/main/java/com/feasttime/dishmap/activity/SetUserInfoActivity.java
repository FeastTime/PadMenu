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
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.QueryUserDetailInfo;
import com.feasttime.dishmap.model.bean.QueryUserInfo;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.SoftHideKeyBoardUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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

    @Bind(R.id.activity_set_user_info_avatar_iv)
    ImageView avatarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_info);
        ButterKnife.bind(this);
        SoftHideKeyBoardUtil.assistActivity(this);
        initViews();
    }


    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("设置");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);


        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);

        showLoading(null);
        RetrofitService.queryUserInfo(infoMap).subscribe(new Consumer<QueryUserInfo>(){
            @Override
            public void accept(QueryUserInfo queryUserInfo) throws Exception {
                if (queryUserInfo.getResultCode() == 0) {
                    QueryUserDetailInfo queryUserDetailInfo = queryUserInfo.getUser();
                    Picasso.with(SetUserInfoActivity.this).load(queryUserDetailInfo.getUserIcon()).transform(new CircleImageTransformation()).into(avatarIv);
                    nickNameEt.setText(queryUserDetailInfo.getNickName());
                    phoneEt.setText(queryUserDetailInfo.getMobileNo());
                } else {

                }
                hideLoading();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                hideLoading();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }

    @OnClick({R.id.activity_set_user_info_nick_save_btn})
    @Override
    public void onClick(View v) {
        if (v == saveBtn) {
            String nickName = nickNameEt.getText().toString();
            //Object sex = sexEt.getTag();  // 1:男  2：女
            Object sex = "1";  // 1:男  2：女
            String birthday = birthdayEt.getText().toString();
            String phone = phoneEt.getText().toString();
            String region = regionEt.getText().toString();
            String introduce = introduceEt.getText().toString();

            if (sex == null) {
                ToastUtil.showToast(this,"请输入性别", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(birthday)) {
                ToastUtil.showToast(this,"请输入出生日期", Toast.LENGTH_SHORT);
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

            showLoading(null);
            HashMap<String,Object> infoMap = new HashMap<String,Object>();
            String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
            String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
            infoMap.put("token",token);
            infoMap.put("userID",userId);
            infoMap.put("area",region);
            infoMap.put("sex",sex.toString());
            infoMap.put("birthday",birthday);
            infoMap.put("personalExplanation",introduce);

            RetrofitService.saveUserInfo(infoMap).subscribe(new Consumer<BaseResponseBean>(){
                @Override
                public void accept(BaseResponseBean baseResponseBean) throws Exception {
                    hideLoading();
                    if (baseResponseBean.getResultCode() == 0) {
                        ToastUtil.showToast(SetUserInfoActivity.this,"保存成功",Toast.LENGTH_SHORT);
                        finish();
                    } else {
                        ToastUtil.showToast(SetUserInfoActivity.this,"保存失败",Toast.LENGTH_SHORT);
                        finish();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ToastUtil.showToast(SetUserInfoActivity.this,"保存失败",Toast.LENGTH_SHORT);
                    hideLoading();
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                }
            });
        }
    }

}
