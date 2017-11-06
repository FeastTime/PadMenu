package com.feasttime.dishmap.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.PriceChangeInfo;
import com.feasttime.dishmap.model.bean.PriceChangeItemInfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.StringUtils;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/10/25.
 */

public class MyDialogs {

    public interface PersonNumListener{
        void overInput(int personNum);
    }

    public static void showEatDishPersonNumDialog(Context context, final PersonNumListener personNumListener) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.eat_dish_dialog_layout,null);
        dialog.setContentView(contentView);

        Button confirm = (Button)contentView.findViewById(R.id.eat_dish_dialog_confirm_btn);
        final EditText personNum = (EditText)contentView.findViewById(R.id.eat_dish_dialog_person_num_et);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HashMap<String,String> requestData = new HashMap<String, String>();
//                requestData.put("",);
//                UtilTools.requestByWebSocket(v.getContext(),);

                if (null != personNumListener){
                    personNumListener.overInput(StringUtils.isEmpty(personNum.getText().toString()) ? 0 : Integer.parseInt(personNum.getText().toString()));
                }
                dialog.dismiss();
            }
        });


        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x610);
//        params.height = (int)context.getResources().getDimension(R.dimen.y810);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }

    //抢座位对话框

    private static long timesCount = 0;
    public static void showBetPriceDialog(Context context, final String storeId,final String bid,final String timeLimit, final String userId) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_bet_price,null);
        dialog.setContentView(contentView);
        dialog.setCancelable(false);

        Button grapBtn = (Button)contentView.findViewById(R.id.dialog_bet_price_grap_btn);
        final EditText numberEt = (EditText)contentView.findViewById(R.id.dialog_bet_price_number_et);
        final TextView timeTv = (TextView)contentView.findViewById(R.id.dialog_bet_price_count_time_tv) ;
        final TextView highPriceTv = (TextView)contentView.findViewById(R.id.dialog_bet_price_count_high_price_tv);

//        final long lastTime = (Long.parseLong(timeLimit) - System.currentTimeMillis()) / 1000 + 2; //多2秒，确保服务器已经结束.

        final int onceTime = 1000;
        timesCount =  Long.parseLong(timeLimit)/onceTime;

        RxBus.getDefault().register(dialog, WebSocketEvent.class, new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {

                if (orderEvent.eventType == WebSocketEvent.PRICE_RANK_CHANGE) {

                    PriceChangeInfo priceChangeInfo = JSON.parseObject(orderEvent.jsonData,PriceChangeInfo.class);
                    highPriceTv.setText(priceChangeInfo.getUserID() + "    " + priceChangeInfo.getHighPrice());

                } else if (orderEvent.eventType == WebSocketEvent.GRAP_TABLE_RESULT_NOTIFICATION) {
                    //当收到竞价结束通知后关闭对话框
                    dialog.dismiss();
                }
            }
        });



        //倒计时部分

        timeTv.setText(timesCount + "");
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {


                if (timesCount > 0) {

                    handler.postDelayed(this, onceTime);
                    timeTv.setText(timesCount + "");

                } else {
                    RxBus.getDefault().unRegister(dialog);
                    dialog.dismiss();
                }

                timesCount--;
            }
        };

        handler.postDelayed(runnable, onceTime);

        grapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> requestData = new HashMap<String, String>();
                requestData.put("storeID",storeId);
                String number = numberEt.getText().toString();
                if (!TextUtils.equals("0",number)) {
                    requestData.put("price",number);
                    requestData.put("type", WebSocketEvent.USER_BET_PRICE + "");
                    requestData.put("bid",bid);
                    requestData.put("userID",userId);
                    UtilTools.requestByWebSocket(v.getContext(),requestData);
                } else {
                    ToastUtil.showToast(v.getContext(),"请输入合法数字", Toast.LENGTH_SHORT);
                }

            }
        });


        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x657);
        params.height = (int)context.getResources().getDimension(R.dimen.y778);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }


    //抢座位结果 胜利者
    public static void showGrapTableWinnerDialog(Context context, String resultStr) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_grap_table_winner,null);
        dialog.setContentView(contentView);

        TextView resultTv = (TextView)contentView.findViewById(R.id.dialog_grap_table_result_tv);
        resultTv.setText(resultStr);

        ImageView confirmBtn = (ImageView)contentView.findViewById(R.id.dialog_grap_table_result_close_iv);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x615);
        params.height = (int)context.getResources().getDimension(R.dimen.y981);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }


    //抢座位结果 失败者
    public static void showGrapTableLoserDialog(Context context, String resultStr) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_grap_table_loser,null);
        dialog.setContentView(contentView);

        TextView resultTv = (TextView)contentView.findViewById(R.id.dialog_grap_table_loser_result_tv);
        resultTv.setText(resultStr);

        ImageView confirmBtn = (ImageView)contentView.findViewById(R.id.dialog_grap_table_loser_close_iv);
        TextView replayTv = (TextView)contentView.findViewById(R.id.dialog_grap_table_loser_replay_tv);

        replayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x615);
        params.height = (int)context.getResources().getDimension(R.dimen.y981);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }


    //抢座位结果
    public static void showGrapTableSeatDialog(Context context,final String storeId,final String bidActivityId) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_grap_seat,null);
        dialog.setContentView(contentView);


        Button confirmBtn = (Button)contentView.findViewById(R.id.dialog_grap_seat_grap_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> requestData = new HashMap<String, String>();
                requestData.put("storeID",storeId);
                requestData.put("name","no name");
                requestData.put("mobileNo", PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO));
                requestData.put("type", WebSocketEvent.USER_GRAP_TABLE + "");
                requestData.put("bidActivityId",bidActivityId);
                UtilTools.requestByWebSocket(v.getContext(),requestData);
                dialog.dismiss();
            }
        });

        dialog.dismiss();

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x610);
        params.height = (int)context.getResources().getDimension(R.dimen.y400);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }

}
