package com.feasttime.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.feasttime.adapter.WaitTimeAdAdapter;
import com.feasttime.adapter.WaitTimeMenuAdapter;
import com.feasttime.menu.R;
import com.feasttime.model.bean.WaitTimeAdInfo;
import com.feasttime.model.bean.WaitTimeMenuInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.waittime.WaitTimeContract;
import com.feasttime.presenter.waittime.WaitTimePresenter;
import com.feasttime.tools.PreferenceUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;


public class WaitingTimeActivity extends BaseActivity implements WaitTimeContract.IWaitTimeView {

    public static final String TAG = "menuTest";

    Timer timer;
    int timerTimes = 0;
    WaitTimeAdAdapter waitTimeAdAdapter;
    WaitTimeMenuAdapter waitTimeMenuAdapter;
    int waitTimeListViewIndex = 0;

    private WaitTimePresenter waitTimePresenter = new WaitTimePresenter();

    @Bind(R.id.waittime_gridView)
    protected GridView gridview;

    @Bind(R.id.waittime_gridViewParent)
    protected FrameLayout gridviewParent;

//    @Bind(R.id.lottery_parent)
//    protected RelativeLayout lotteryParent;
//
//    @Bind(R.id.lottery)
//    protected ImageView lottery;

    @Bind(R.id.waittime_listview)
    protected ListView waitTimeListView;

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[0];
    }

    @Override
    protected void onInitPresenters() {

        waitTimePresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_waiting_time;
    }

    @Override
    protected void initViews() {


    }

    public void goBack(View view){

        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
//        this.getSupportActionBar().hide();// 隐藏ActionBar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar  即全屏

    }

    @Override
    protected void onResume() {
        super.onResume();

        waitTimeListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                waitTimeListViewIndex = i;
            }
        });

        // 刷新上菜进度的定时器
        timer = new Timer(true);

        TimerTask task = new TimerTask() {
            public void run() {

                if (timerTimes%10==0){

                    String token = PreferenceUtil.getStringKey("token");
                    String orderID = PreferenceUtil.getStringKey("orderID");

                    token = "ljiqsdgf54sdfweq6565f7wes51635sad4f65f";
                    orderID = "3675570486095607038197889018";

                    //每次需要执行的代码放到这里面。
                    waitTimePresenter.getWaitTimeMenuList(token,orderID);
                } else {

                    WaitingTimeActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            updateList(waitTimeListViewIndex, waitTimeListView);
                        }
                    });

                }


                timerTimes ++;
            }
        };

        timer.schedule(task, 0, 1000);

        // 获取右边的广告
        Log.d(TAG, gridview.getWidth()/3 + "--" + getItemHeight() + "");
        waitTimePresenter.getWaitTimeAD(PreferenceUtil.getStringKey("token"), gridview.getWidth()/3, getItemHeight(), 9);
//        waitTimePresenter.getWaitTimeAD(PreferenceUtil.getStringKey("token"), 243, 146, 9);

    }

    @Override
    protected void onPause() {
        timer.cancel();
        super.onPause();
    }

    public void adLottery(View view){

//        lotteryParent.setVisibility(View.GONE);

        startADLottery();
    }

    private void startADLottery(){

    }

    // 显示右边的广告
    @Override
    public void showWaitTimeAD(WaitTimeAdInfo waitTimeAdInfo) {


        int childHeight = getItemHeight();

        waitTimeAdAdapter = new WaitTimeAdAdapter(this, waitTimeAdInfo.getData(),childHeight);

        //添加Item到网格中
        gridview.setAdapter(waitTimeAdAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                System.out.println("click index:" + arg2);

            }
        });

        Log.d(TAG, gridview.getWidth()/3 + "--" + getItemHeight() + "");

    }

    @Override
    public void showWaitTimeDishList(WaitTimeMenuInfo waitTimeMenuInfo){

        Log.d(TAG, "showWaitTimeDishList ");
        if (null == waitTimeMenuAdapter){

            waitTimeMenuAdapter = new WaitTimeMenuAdapter(this, waitTimeMenuInfo.getList());
            waitTimeListView.setAdapter(waitTimeMenuAdapter);
        } else {
            waitTimeMenuAdapter.updateData(waitTimeMenuInfo.getList());
        }
    }

    public void updateList(int index,ListView listview){
        Log.d(TAG, "updateList");
        //得到第一个可见item项的位置
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        int lastVisiblePosition = listview.getLastVisiblePosition();

        for (int i= firstVisiblePosition; i<=lastVisiblePosition; i++){

            View view = listview.getChildAt(i - index);
            listview.getAdapter() .getView(i, view, listview);

        }

    }

    private int getItemHeight() {

        int parentHeight = gridviewParent.getHeight();

        int verticalSpacing = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)

            verticalSpacing = gridview.getVerticalSpacing();

        verticalSpacing *= 5;

        return (parentHeight - verticalSpacing) / 6;

    }


    public void goToHeathPage(View view){
        Intent intent = new Intent(this, EndActivity.class);
        this.startActivity(intent);

    }
}
