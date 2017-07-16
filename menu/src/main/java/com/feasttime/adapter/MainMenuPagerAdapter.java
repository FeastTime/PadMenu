package com.feasttime.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasttime.menu.R;
import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.tools.ScreenTools;
import com.feasttime.view.PlayVideoActivity;
import com.feasttime.view.ShowWebActivity;
import com.feasttime.widget.jazzyviewpager.JazzyViewPager;
import com.feasttime.widget.jazzyviewpager.OutlineContainer;

import java.util.List;

/**
 * Created by chen on 2017/4/20.
 */



public class MainMenuPagerAdapter extends PagerAdapter {
    private final int perPageItem = 3;

    public interface OnItemClick{
        void onDishesPicClicked(String ID);
    }

    private Context context;
    private JazzyViewPager mJazzy;
    private LayoutInflater mLayoutInflater;
    private OnItemClick mOnItemClick;
    private  List<MenuItemInfo> menuItemInfoList;

    public MainMenuPagerAdapter(Context context, JazzyViewPager jazzyViewPager, List<MenuItemInfo> menuItemInfoList) {
        this.context = context;
        this.mJazzy = jazzyViewPager;
        this.menuItemInfoList = menuItemInfoList;
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.mOnItemClick = onItemClick;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        int dataPosition1 = position * perPageItem;
        int dataPosition2 = position * perPageItem + 1;
        int dataPosition3 = position * perPageItem + 2;

        MenuItemInfo menuItemInfo1 = null;
        MenuItemInfo menuItemInfo2 = null;
        MenuItemInfo menuItemInfo3 = null;

        if (dataPosition1 < menuItemInfoList.size())
            menuItemInfo1 = menuItemInfoList.get(dataPosition1);

        if (dataPosition2 < menuItemInfoList.size())
            menuItemInfo2 = menuItemInfoList.get(dataPosition2);

        if (dataPosition3 < menuItemInfoList.size())
            menuItemInfo3 = menuItemInfoList.get(dataPosition3);


        LayoutInflater inflater = LayoutInflater.from(context);



        LinearLayout ll = new LinearLayout(context);
        LinearLayout view1 = (LinearLayout) inflater.inflate(R.layout.menu_item_layout,null);
        setPerItemView(view1,menuItemInfo1,300);
        ll.addView(view1);

        LinearLayout view2 = (LinearLayout) inflater.inflate(R.layout.menu_item_layout,null);
        if (menuItemInfo2 != null) {
            setPerItemView(view2,menuItemInfo2,245);
        } else {
            view2.setVisibility(View.INVISIBLE);
            view2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        }
        ll.addView(view2);

        LinearLayout view3 = (LinearLayout) inflater.inflate(R.layout.menu_item_layout,null);
        if (menuItemInfo3 != null) {
            setPerItemView(view3,menuItemInfo3,135);

        } else {
            view3.setVisibility(View.INVISIBLE);
            view3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        }
        ll.addView(view3);


        ll.setBackgroundColor(Color.TRANSPARENT);
        ll.setGravity(Gravity.CENTER);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        container.addView(ll, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mJazzy.setObjectForPosition(ll, position);

        return ll;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView(mJazzy.findViewFromObject(position));
    }
    @Override
    public int getCount() {
        int count = (int)(menuItemInfoList.size() / perPageItem) + 1;
        return count;
    }
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        if (view instanceof OutlineContainer) {
            return ((OutlineContainer) view).getChildAt(0) == obj;
        } else {
            return view == obj;
        }
    }

    private void setPerItemView(LinearLayout view,final MenuItemInfo menuItemInfo,int imgWidth) {
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        TextView dishesName1 = (TextView)view.findViewById(R.id.menu_item_layout_dishes_name_tv);

        TextView seeDetail1 = (TextView)view.findViewById(R.id.menu_item_layout_dishes_detail_tv);

        dishesName1.setText(menuItemInfo.getDishName());

        view.setGravity(Gravity.CENTER);

        view.setBackgroundColor(Color.TRANSPARENT);
        ImageView adflagIv1 = (ImageView) view.findViewById(R.id.menu_item_layout_ad_flag_iv);
        TextView playVideoTv1 = (TextView)view.findViewById(R.id.menu_item_layout_play_video_tv);


        adflagIv1.setImageResource(R.mipmap.ad_flag_chubang);

        playVideoTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PlayVideoActivity.class));
            }
        });




        seeDetail1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ShowWebActivity.class));
            }
        });


        ImageView dishes1 = (ImageView) view.findViewById(R.id.menu_item_layout_dishes_iv);

        dishes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.onDishesPicClicked(menuItemInfo.getDishID());
            }
        });


        ViewGroup.LayoutParams params1 = dishes1.getLayoutParams();
        params1.width = ScreenTools.dip2px(context,imgWidth);
        dishes1.setLayoutParams(params1);
    }
}