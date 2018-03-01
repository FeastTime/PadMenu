package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/3/1.
 */

public class UserConversationFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        ButterKnife.bind(this,view);
        initViews();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViews() {
        titleBarBackIv.setVisibility(View.GONE);
        titleBarRightIv.setVisibility(View.GONE);
        titleCenterTv.setText("呼啦圈");
    }


    @Override
    public void onClick(View v) {

    }
}
