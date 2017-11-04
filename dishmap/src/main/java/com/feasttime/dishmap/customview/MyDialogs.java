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

    private static int timeCount = 0;
    public static void showBetPriceDialog(Context context, final String storeId,final String bid) {
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

        RxBus.getDefault().register(dialog, WebSocketEvent.class, new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {
                if (orderEvent.eventType == WebSocketEvent.PRICE_RANK_CHANGE) {
                    PriceChangeInfo priceChangeInfo = JSON.parseObject(orderEvent.jsonData,PriceChangeInfo.class);
                    if (priceChangeInfo.getDetail().size() > 0) {
                        PriceChangeItemInfo priceChangeItemInfo = priceChangeInfo.getDetail().get(0);
                        highPriceTv.setText(priceChangeItemInfo.getName() + "  " + priceChangeItemInfo.getPrice());
                    }
                }
            }
        });

        //倒计时部分
        timeCount = 0;
        timeTv.setText(timeCount + "");
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                timeCount++;
                if (timeCount <= 10) {
                    handler.postDelayed(this, 1000);
                    timeTv.setText(timeCount + "");
                } else {
                    //10秒计时结束
                    RxBus.getDefault().unRegister(dialog);
                    dialog.dismiss();
                }
            }
        };

        handler.postDelayed(runnable, 1000);

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
                    UtilTools.requestByWebSocket(v.getContext(),requestData);
                } else {
                    ToastUtil.showToast(v.getContext(),"请输入合法数字", Toast.LENGTH_SHORT);
                }

            }
        });





        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x610);
//        params.height = (int)context.getResources().getDimension(R.dimen.y810);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }


    //抢座位结果
    public static void showGrapTableResultDialog(Context context,String resultStr) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_grap_table_result,null);
        dialog.setContentView(contentView);

        TextView resultTv = (TextView)contentView.findViewById(R.id.dialog_grap_table_result_tv);
        resultTv.setText(resultStr);

        Button confirmBtn = (Button)contentView.findViewById(R.id.dialog_grap_table_confirm_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x610);
        params.height = (int)context.getResources().getDimension(R.dimen.y400);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }


    //抢座位结果
    public static void showGrapTableSeatDialog(Context context,final String storeId) {
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
