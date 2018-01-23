package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.FeedBackActivity;
import com.feasttime.dishmap.activity.HadEatedStoreActivity;
import com.feasttime.dishmap.activity.MessageActivity;
import com.feasttime.dishmap.activity.SetUserInfoActivity;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 2018/1/10.
 */

public class UserMineFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.fragment_user_mine_feedback_tv)
    TextView feedbackTv;

    @Bind(R.id.fragment_user_mine_logout_tv)
    TextView logoutTv;

    @Bind(R.id.fragment_user_mine_feedback_rel)
    View feedBackRel;

    @Bind(R.id.fragment_user_mine_logout_rel)
    View logoutRel;

    @Bind(R.id.fragment_user_mine_my_seat_tv)
    TextView mySeatTv;

    @Bind(R.id.fragment_user_mine_coupon_tv)
    TextView couponTv;

    @Bind(R.id.fragment_user_mine_eated_store_tv)
    TextView eatedStoreTv;

    @Bind(R.id.fragment_user_mine_setting_iv)
    ImageView settingIv;

    @Bind(R.id.fragment_user_mine_msg_iv)
    ImageView msgIv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_mine, container, false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    private void initViews(){
        Resources resources = this.getResources();
        UtilTools.chenageTextDrawableSize(mySeatTv,R.mipmap.mine_seat,(int)resources.getDimension(R.dimen.x47),(int)resources.getDimension(R.dimen.y47),2);
        UtilTools.chenageTextDrawableSize(couponTv,R.mipmap.mine_coupon,(int)resources.getDimension(R.dimen.x52),(int)resources.getDimension(R.dimen.y36),2);
        UtilTools.chenageTextDrawableSize(eatedStoreTv,R.mipmap.mine_house,(int)resources.getDimension(R.dimen.x47),(int)resources.getDimension(R.dimen.y49),2);
        UtilTools.chenageTextDrawableSize(feedbackTv,R.mipmap.feedback,(int)resources.getDimension(R.dimen.x40),(int)resources.getDimension(R.dimen.y40),1);
        UtilTools.chenageTextDrawableSize(logoutTv,R.mipmap.logout,(int)resources.getDimension(R.dimen.x37),(int)resources.getDimension(R.dimen.y39),1);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.fragment_user_mine_feedback_rel,R.id.fragment_user_mine_logout_rel,R.id.fragment_user_mine_my_seat_tv,R.id.fragment_user_mine_coupon_tv,R.id.fragment_user_mine_eated_store_tv
    ,R.id.fragment_user_mine_setting_iv,R.id.fragment_user_mine_msg_iv})
    @Override
    public void onClick(View v) {
        if (v == feedBackRel) {
            startActivity(new Intent(this.getActivity(), FeedBackActivity.class));
        } else if (v == logoutRel) {
            ToastUtil.showToast(this.getActivity(),"退出成功", Toast.LENGTH_SHORT);
        } else if (v == mySeatTv) {

        } else if (v == couponTv) {
//            startActivity(new Intent(this.getActivity(),));
        } else if (v == eatedStoreTv) {
            startActivity(new Intent(this.getActivity(), HadEatedStoreActivity.class));
        } else if (v == msgIv) {
            startActivity(new Intent(this.getActivity(), MessageActivity.class));
        } else if (v == settingIv) {
            startActivity(new Intent(this.getActivity(), SetUserInfoActivity.class));
        }
    }
}
