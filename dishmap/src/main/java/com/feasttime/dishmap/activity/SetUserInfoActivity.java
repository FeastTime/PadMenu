package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.QueryUserDetailInfo;
import com.feasttime.dishmap.model.bean.QueryUserInfo;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.SoftHideKeyBoardUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by chen on 2018/1/15.
 */

public class SetUserInfoActivity extends BaseActivity implements View.OnClickListener,MyDialogs.GenderListener {
    private static final String TAG = "SetUserInfoActivity";

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

    @Bind(R.id.activity_set_user_info_nick_sex_tv)
    TextView genderTv;

    @Bind(R.id.activity_set_user_info_nick_birthday_tv)
    TextView birthdayTv;

    @Bind(R.id.activity_set_user_info_nick_phone_et)
    TextView phoneEt;

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

                    long birthday = queryUserDetailInfo.getBirthday();
                    if (birthday != 0) {
                        birthdayTv.setText(UtilTools.formateDateForChinese(birthday));
                        birthdayTv.setTag(birthday);
                    }

                    String gender = queryUserDetailInfo.getSex();
                    if (!TextUtils.isEmpty(gender)) {
                        genderTv.setTag(gender);
                        if (TextUtils.equals(gender,"1")) {
                            genderTv.setText("男");
                        } else if (TextUtils.equals(gender,"2")) {
                            genderTv.setText("女");
                        }
                    }

                    String area = queryUserDetailInfo.getArea();
                    if (!TextUtils.isEmpty(area)) {
                        regionEt.setText(area);
                    }

                    String introduce = queryUserDetailInfo.getPersonalExplanation();
                    if (!TextUtils.isEmpty(introduce)) {
                        introduceEt.setText(introduce);
                    }

                    if (!TextUtils.isEmpty(queryUserDetailInfo.getMobileNo())){
                        phoneEt.setText(queryUserDetailInfo.getMobileNo());
                    }

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



    @OnClick({R.id.activity_set_user_info_nick_save_btn, R.id.activity_set_user_info_nick_birthday_tv,R.id.activity_set_user_info_nick_sex_tv, R.id.activity_set_user_info_nick_phone_et})
    @Override
    public void onClick(View v) {

        Log.d(TAG, "click");

        if (v == saveBtn) {
            String nickName = nickNameEt.getText().toString();
            Object sex = genderTv.getTag();  // 1:男  2：女
            Object birthday = birthdayTv.getTag();
            String phone = phoneEt.getText().toString();
            String region = regionEt.getText().toString();
            String introduce = introduceEt.getText().toString();

            if (sex == null) {
                ToastUtil.showToast(this,"请输入性别", Toast.LENGTH_SHORT);
                return;
            }

            if (birthday == null) {
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
            infoMap.put("userId",userId);
            infoMap.put("area",region);
            infoMap.put("sex",sex.toString());
            infoMap.put("birthday",birthday.toString());
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
        } else if (v == birthdayTv) {
            DatePickDialog dialog = new DatePickDialog(this);
            //设置上下年分限制
            dialog.setYearLimt(100);
            //设置标题
            dialog.setTitle("选择出生日期");
            //设置类型
            dialog.setType(DateType.TYPE_YMD);
            //设置消息体的显示格式，日期格式
            dialog.setMessageFormat("yyyy-MM-dd HH:mm");
            //设置选择回调
            dialog.setOnChangeLisener(null);
            //设置点击确定按钮回调
            dialog.setOnSureLisener(new OnSureLisener() {
                @Override
                public void onSure(Date date) {
                    LogUtil.d(TAG,"the selected date:" + date.getTime());
                    long birghday = date.getTime();
                    birthdayTv.setTag(String.valueOf(birghday));
                    birthdayTv.setText(UtilTools.formateDateForChinese(birghday));
                }
            });
            dialog.show();
        } else if (v == genderTv) {
            MyDialogs.showGenderDialog(this,this);

        } else if (v == phoneEt){// 修改手机号弹窗

            SetUserInfoActivity.this.startActivity(new Intent(SetUserInfoActivity.this, UpdateMobileNOActivity.class));
        }
    }

    @Override
    public void overInput(int gender) {
        if (gender == 1) {
            //男
            genderTv.setText("男");
            genderTv.setTag("1");
        } else if (gender == 2) {
            //女
            genderTv.setText("女");
            genderTv.setTag("2");
        }
    }
}
