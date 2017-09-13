package com.feasttime.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasttime.menu.R;
import com.feasttime.model.bean.HealthIndexAssessmentInfo;
import com.feasttime.model.bean.PersonalStatisticsInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.statistics.StatisticsContract;
import com.feasttime.presenter.statistics.StatisticsPresenter;
import com.feasttime.tools.PreferenceUtil;
import com.feasttime.widget.chart.LineChart01View;
import com.feasttime.widget.chart.MultiBarChart01View;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by chen on 2017/5/8.
 */

public class PaymentSuccessActivity extends BaseActivity implements StatisticsContract.IStatisticsView ,View.OnClickListener{
    private static final String TAG = "PaymentSuccessActivity";

    @Bind(R.id.end_activity_last_month_eat_chart_ll)
    LinearLayout lastMonthChartLl;

    @Bind(R.id.end_activity_this_month_eat_chart_ll)
    LinearLayout thisMonthChartLl;

    @Bind(R.id.end_activity_logout_tv)
    TextView logoutTv;

    @Bind(R.id.normal_title_bar_back_iv)
    ImageView backIv;

    @Bind(R.id.normal_title_bar_title_tv)
    TextView titleTv;

    @Bind(R.id.end_activity_consume_mbcv)
    MultiBarChart01View consumeMbcv;

    @Bind(R.id.end_activity_last_month_eat_percent_tv)
    TextView lastMonthEatPercentTv;

    @Bind(R.id.end_activity_this_month_eat_percent_tv)
    TextView thisMonthEatPercentTv;

    @Bind(R.id.end_activity_chinese_meal_percent_tv)
    TextView chineseMealPercentTv;

    @Bind(R.id.end_activity_japanese_meal_percent_tv)
    TextView japaneseMealPercentTv;

    @Bind(R.id.end_activity_west_meal_percent_tv)
    TextView westMealPercentTv;



    private StatisticsPresenter statisticsPresenter = new StatisticsPresenter();

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[]{statisticsPresenter};
    }

    @Override
    protected void onInitPresenters() {
        statisticsPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.payment_success_activity;
    }

    @Override
    protected void initViews() {
        titleTv.setText("");
        LayoutInflater inflater = this.getLayoutInflater();
        for (int i = 0 ; i < 6 ; i++) {
            View lastMonth = inflater.inflate(R.layout.rect_chart_item,null);
            View thisMonth = inflater.inflate(R.layout.rect_chart_item,null);

            lastMonthChartLl.addView(lastMonth);
            lastMonthChartLl.setTag(1);
            thisMonthChartLl.addView(thisMonth);
            thisMonthChartLl.setTag(2);
        }



        statisticsPresenter.getStatisticsPersonalInfo("3232326654646464");
        test();
    }


    private void test()  {
        String orderID = PreferenceUtil.getStringKey("orderID");
        statisticsPresenter.getgetHealthIndexAssessment("2017040721001001240261160865");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setChartPercent(LinearLayout view,int percent) {
        int blueColor = getResources().getColor(R.color.holo_blue);
        int redColor = getResources().getColor(R.color.red);
        int count = view.getChildCount();
        int tag = (int)view.getTag();
        percent = (percent * 10);
        int calcPercent = 0;
        boolean isBreak = false;
        for (int i = 0 ; i < count ; i++) {
            LinearLayout childView = (LinearLayout) view.getChildAt(count - i - 1);
            int childSize = childView.getChildCount();
            for (int j = 0 ; j < childSize ; j++) {
                calcPercent = calcPercent + 33;
                if (calcPercent > percent) {
                    isBreak = true;
                    break;
                }

                View smallView = childView.getChildAt(j);
                if (tag == 1) {
                    smallView.setBackgroundColor(blueColor);
                } else {
                    smallView.setBackgroundColor(redColor);
                }

//                LogUtil.d(TAG,"the set index" + j);
            }

            if (isBreak) {
                break;
            }
        }
    }


    @Override
    public void showData(PersonalStatisticsInfo result) {
        consumeMbcv.setChartData(result.getConsumeChart());
        setChartPercent(lastMonthChartLl,Integer.parseInt(result.getLastMonthEatPercent().replace("%","")));
        setChartPercent(thisMonthChartLl,Integer.parseInt(result.getThisMonthEatPercent().replace("%","")));
        thisMonthEatPercentTv.setText(result.getThisMonthEatPercent());
        lastMonthEatPercentTv.setText(result.getLastMonthEatPercent());

        chineseMealPercentTv.setText(result.getEatType().get(0).getPercent());
        japaneseMealPercentTv.setText(result.getEatType().get(1).getPercent());
        westMealPercentTv.setText(result.getEatType().get(2).getPercent());

//        eatCountTv.setText(result.getEatCount());
//        String eatCountStr = this.getString(R.string.end_activity_eat_count);
//        rightEatCountTv.setText(eatCountStr.replace("num",result.getEatCount()));
    }


    @Override
    public void showHealthIndexAssessment(HealthIndexAssessmentInfo healthIndexAssessmentInfo) {

    }

    @OnClick({R.id.end_activity_logout_tv,R.id.normal_title_bar_back_iv})
    @Override
    public void onClick(View v) {
        if (v == logoutTv) {
//            String token = PreferenceUtil.getStringKey("token");
//            String orderID = PreferenceUtil.getStringKey("orderID");
            PreferenceUtil.setStringKey("token","");
            PreferenceUtil.setStringKey("orderID","");
            finish();
        } else if (v == backIv) {
            finish();
        }
    }
}
