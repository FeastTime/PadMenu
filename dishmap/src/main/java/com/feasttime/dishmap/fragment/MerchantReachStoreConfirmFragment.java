package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.MerchantReachStoreConfirmAdapter;
import com.feasttime.dishmap.model.bean.ReachStoreConfirmItemInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2017/10/28.
 */

public class MerchantReachStoreConfirmFragment extends Fragment{

    @Bind(R.id.fragment_merchant_reach_store_confirm_content_lv)
    ListView contentLv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_reach_store_confirm, container,
                false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }


    private void initView() {
        ArrayList<ReachStoreConfirmItemInfo> dataList = new ArrayList<ReachStoreConfirmItemInfo>();
        ReachStoreConfirmItemInfo reachStoreConfirmItemInfo1 = new ReachStoreConfirmItemInfo();
        reachStoreConfirmItemInfo1.setName("小明");
        reachStoreConfirmItemInfo1.setType("5-7");
        reachStoreConfirmItemInfo1.setNumber("665");
        dataList.add(reachStoreConfirmItemInfo1);

        ReachStoreConfirmItemInfo reachStoreConfirmItemInfo2 = new ReachStoreConfirmItemInfo();
        reachStoreConfirmItemInfo2.setName("小红");
        reachStoreConfirmItemInfo2.setType("5-9");
        reachStoreConfirmItemInfo2.setNumber("665");
        dataList.add(reachStoreConfirmItemInfo2);

        ReachStoreConfirmItemInfo reachStoreConfirmItemInfo3 = new ReachStoreConfirmItemInfo();
        reachStoreConfirmItemInfo3.setName("猴塞雷");
        reachStoreConfirmItemInfo3.setType("8-9");
        reachStoreConfirmItemInfo3.setNumber("225");
        dataList.add(reachStoreConfirmItemInfo3);

        MerchantReachStoreConfirmAdapter merchantReachStoreConfirmAdapter = new MerchantReachStoreConfirmAdapter(this.getActivity(),dataList);
        contentLv.setAdapter(merchantReachStoreConfirmAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
