package com.feasttime.dishmap.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.BaseActivity;
import com.feasttime.dishmap.activity.CouponDetailActivity;
import com.feasttime.dishmap.activity.MySeatDetailActivity;
import com.feasttime.dishmap.activity.OpenedRedPackageActivity;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.CouponChildListItemInfo;
import com.feasttime.dishmap.model.bean.MyTableItemInfo;
import com.feasttime.dishmap.model.bean.PriceChangeInfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.ActivityCollector;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.StringUtils;
import com.feasttime.dishmap.utils.ToastUtil;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by chen on 2017/10/25.
 */

public class MyDialogs {

    public interface PersonNumListener{
        void overInput(int personNum);
    }

    public interface GenderListener{
        void overInput(int gender); //1:女 2：男
    }


    /**
     * 性别选择
     * @param context Context
     * @param genderListener genderListener
     *
     */
    public static void showGenderDialog(Context context, final GenderListener genderListener) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_gender,null);
        dialog.setContentView(contentView);

        TextView manTv = (TextView)contentView.findViewById(R.id.dialog_gender_man_tv);
        TextView womanTv = (TextView)contentView.findViewById(R.id.dialog_gender_woman_tv);

        manTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //男
                genderListener.overInput(1);
                dialog.dismiss();
            }
        });

        womanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //女
                genderListener.overInput(2);
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

                    if (priceChangeInfo.getUserID().length()>=11){

                        String userID = priceChangeInfo.getUserID().substring(0,3) + "****" + priceChangeInfo.getUserID().substring(7,11);
                        highPriceTv.setText(userID + "    " + priceChangeInfo.getHighPrice());
                    }


                } else if (orderEvent.eventType == WebSocketEvent.BID_TABLE_RESULT_NOTIFICATION) {
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
                    //UtilTools.requestByWebSocket(v.getContext(),requestData);
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

    /**
     * 展示红包结果
     * @param context Context
     * @param title 标题
     * @param detail 内容
     * @param description 描述
     */
    public static void showGrapTableWinnerDialog(final Context context, String title, String detail, String description, final MyTableItemInfo myTableItemInfo, final CouponChildListItemInfo couponChildListItemInfo) {

        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_grap_table_winner,null);
        dialog.setContentView(contentView);

        TextView titleTv = (TextView)contentView.findViewById(R.id.dialog_grap_table_result_title);
        titleTv.setText(title);

        TextView detailTv = (TextView)contentView.findViewById(R.id.dialog_grap_table_result_detail);
        detailTv.setText(detail);

        TextView describeTv = (TextView)contentView.findViewById(R.id.dialog_grap_table_describe);
        describeTv.setText(description);

        ImageView confirmBtn = (ImageView)contentView.findViewById(R.id.dialog_grap_table_result_close_iv);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        ImageView useNowBtn = (ImageView)contentView.findViewById(R.id.dialog_grap_table_result_use_now_iv);
        useNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null != couponChildListItemInfo){

                    Intent intent = new Intent(context, CouponDetailActivity.class);

                    intent.putExtra("couponData",couponChildListItemInfo);
                    context.startActivity(intent);

                } else if (null != myTableItemInfo){

                    Intent intent = new Intent(context, MySeatDetailActivity.class);
                    intent.putExtra("tablesData", myTableItemInfo);
                    context.startActivity(intent);
                }

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

    /**
     * 红包被抢光提示
     * @param context Context
     * @param redPackageId 红包Id
     */
    public static void showEmptyRedPackage(final Context context, final String redPackageId, String userIconStr) {

        final Dialog dialog = new Dialog(context,R.style.DialogTheme);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_empty_red_package,null);
        dialog.setContentView(contentView);

        ImageView userIcon = (ImageView) contentView.findViewById(R.id.user_icon);

        if (!StringUtils.isEmpty(userIconStr)){

            Picasso.with(context).load(userIconStr).transform(new CircleImageTransformation()).into(userIcon);
        }

        TextView textView = (TextView) contentView.findViewById(R.id.show_others_luck);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                Intent intent = new Intent(context, OpenedRedPackageActivity.class);
                intent.putExtra("redPackageId", redPackageId);
                context.startActivity(intent);
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x550);
        params.height = (int)context.getResources().getDimension(R.dimen.y600);
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    private static String beforeDinerCount;

    /**
     * 修改就餐人数
     * @param context Context
     * @param storeId 店铺Id
     */
    public static void modifyEatPersonNumber(final Context context, final String storeId) {



        beforeDinerCount = PreferenceUtil.getStringKey(PreferenceUtil.DINER_COUNT + storeId);

        if (StringUtils.isEmpty(beforeDinerCount)){
            beforeDinerCount = "0";
        }

        final Dialog dialog = new Dialog(context,R.style.DialogTheme);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_modify_eat_person_number,null);
        dialog.setContentView(contentView);

        final Switch onlyGetCoupon = (Switch) contentView.findViewById(R.id.only_get_coupon_Sw);

        final EditText dinerCount = (EditText) contentView.findViewById(R.id.dialog_modify_eat_person_number_diner_count);

        dinerCount.setText(PreferenceUtil.getStringKey(PreferenceUtil.PERSON_NO));
        dinerCount.setSelection(dinerCount.getText().length());

        Button confirmBtn = (Button)contentView.findViewById(R.id.dialog_modify_eat_person_number_confirm_btn);

        beforeDinerCount = "0";
        onlyGetCoupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    beforeDinerCount = dinerCount.getText().toString();

                    if (!TextUtils.equals("0", dinerCount.getText().toString())){

                        dinerCount.setText("0");
                        dinerCount.setSelection(dinerCount.getText().length());
                    }

                } else {

                    if (!TextUtils.equals(beforeDinerCount, "0")){

                        dinerCount.setText(beforeDinerCount);
                        dinerCount.setSelection(dinerCount.getText().length());
                    }
                }
            }
        });

        dinerCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String nowText = dinerCount.getText().toString();

//                if (nowText.length() == 0){
////                    beforeDinerCount = "0";
//                }

                if (nowText.length() > 0){

                    if (TextUtils.equals(nowText, "0")){

                        onlyGetCoupon.setChecked(true);

                    } else {

                        beforeDinerCount = nowText;
                        onlyGetCoupon.setChecked(false);

                    }
                }
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dinerCountStr = dinerCount.getText().toString();

                if (TextUtils.isEmpty(dinerCountStr)){
                    dinerCountStr = "0";
                }

                String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
                String userID = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);

                // 修改 用餐人数
                HashMap<String,Object> infoMap = new HashMap<>();

                infoMap.put("storeId", storeId);
                infoMap.put("dinnerCount", dinerCountStr);
                infoMap.put("type", WebSocketEvent.SET_NUMBER_OF_USER+"");
                infoMap.put("token",token);
                infoMap.put("userId",userID);


                ((BaseActivity)context).showLoading(null);
                final String finalDinerCountStr = dinerCountStr;

                RetrofitService.setTheNumberOfDiners(infoMap).subscribe(new Consumer<BaseResponseBean>(){
                    @Override
                    public void accept(BaseResponseBean baseResponseBean) throws Exception {

                        if (baseResponseBean.getResultCode() == 0) {

                            PreferenceUtil.setStringKey(PreferenceUtil.DINER_COUNT + storeId, finalDinerCountStr);
                            PreferenceUtil.setLongKey(PreferenceUtil.DINER_COUNT_TIME + storeId, System.currentTimeMillis());

                            String dinerStr = dinerCount.getText().toString();
                            PreferenceUtil.setStringKey(PreferenceUtil.PERSON_NO, dinerStr.equals("0") ? "" : dinerStr);
                            dialog.dismiss();

                        } else {
                            ToastUtil.showToast(context,baseResponseBean.getResultMsg(),Toast.LENGTH_SHORT);
                        }

                        ((BaseActivity)context).hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ((BaseActivity)context).hideLoading();
                        ToastUtil.showToast(context, context.getString(R.string.internet_disconnected_toast),Toast.LENGTH_SHORT);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x711);
        params.height = (int)context.getResources().getDimension(R.dimen.y454);
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    //抢红包结果对话框
    public static void showGrabRedPacketResult(Context context) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_grab_red_packet_result,null);
        dialog.setContentView(contentView);

        TextView backChatTv = (TextView)contentView.findViewById(R.id.dialog_grab_red_packet_result_back_to_chat_tv);

        backChatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x650);
        params.height = (int)context.getResources().getDimension(R.dimen.y331);
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    /**
     * 补充手机号
     *
     * @param activity Context
     */
    public static void showCheckMobileNODialog(final Activity activity) {

        // 获取手机号
        String mobileNO = PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO);

        // 如果手机号不为空，不用补充
        if (!TextUtils.isEmpty(mobileNO)){
            return ;
        }

        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {

                if (i == KeyEvent.KEYCODE_BACK
                        && keyEvent.getRepeatCount() == 0) {
                    //Log.d("")
                    activity.finish();
                    return true;
                }

                return false;
            }
        });

        dialog.setCancelable(false);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(activity);
        View contentView = inflater.inflate(R.layout.dialog_check_mobile_number,null);
        dialog.setContentView(contentView);

        final EditText mobileNo = (EditText)contentView.findViewById(R.id.mobileNumber);
        final EditText verificationCode = (EditText)contentView.findViewById(R.id.verificationCode);
        final TextView countdownStr = (TextView)contentView.findViewById(R.id.countdown);
        final TextView sendVerificationCode  = (TextView)contentView.findViewById(R.id.sendVerificationCode);

        //3.0版本之后的初始化看这里（包括3.0）
        EventHandler eh = new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 回调完成

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {


                        // 验证码验证成功
                        Log.d("补充手机号", "1验证码验证成功");

                        PreferenceUtil.setStringKey(PreferenceUtil.MOBILE_NO, mobileNo.getText().toString());

                        //保存用户手机号
                        HashMap<String,Object> infoMap = new HashMap<String,Object>();
                        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
                        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
                        String openId = PreferenceUtil.getStringKey(PreferenceUtil.WE_CHAT_OPENID);

                        infoMap.put("token",token);
                        infoMap.put("userId",userId);
                        infoMap.put("openId",openId);
                        infoMap.put("mobileNo",mobileNo.getText().toString());

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ((BaseActivity)activity).showLoading(null);
                            }
                        });

                        //请求网络
                        RetrofitService.queryUserInfo(infoMap).subscribe(new Consumer<BaseResponseBean>(){
                            @Override
                            public void accept(BaseResponseBean BaseResponseBean) throws Exception {
                                if (BaseResponseBean.getResultCode() == 0) {
                                    dialog.dismiss();
                                }

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        ((BaseActivity)activity).hideLoading();
                                    }
                                });

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                ToastUtil.showToast(activity,"网络通讯异常，请稍后再试",Toast.LENGTH_SHORT);
                                ((BaseActivity)activity).hideLoading();
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){

                        // 获取验证码成功
                        Log.d("补充手机号", "2获取验证码成功");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                countdownStr.setVisibility(View.VISIBLE);
                                sendVerificationCode.setVisibility(View.GONE);
                                sendVerificationCode.setClickable(true);
                            }
                        });

                        countdownStart(activity, sendVerificationCode , countdownStr, 120);

                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                        Log.d("补充手机号", "3返回支持发送验证码的国家列表");
                    }

                } else { // 失败回调

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 验证码 验证失败
                        Log.d("补充手机号", "4验证码验证失败");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        // 验证码 获取失败
                        Log.d("补充手机号", "5获取验证码失败");

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                                // 点击后锁住，回调中放开
                                sendVerificationCode.setClickable(true);
                            }
                        });


                    }

                }
            }
        };

        SMSSDK.registerEventHandler(eh); //注册短信回调



        // 对话框取消
        contentView.findViewById(R.id.dialog_check_mobile_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                dialog.dismiss();
            }
        });

        // 内部对话框
        contentView.findViewById(R.id.dialog_check_mobile_inner_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });


        // 获取验证码按钮
        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 验证手机号
                String mobileNo_str = mobileNo.getText().toString();

                if (TextUtils.isEmpty(mobileNo_str)
                        || mobileNo_str.length() != 11
                        || !TextUtils.isDigitsOnly(mobileNo_str) ){
                    Toast.makeText(activity, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 点击后锁住，回调中放开
                sendVerificationCode.setClickable(false);
                Log.d("发送验证码",mobileNo_str);


                SMSSDK.getVerificationCode("86", mobileNo_str);

            }
        });

        // 确定按钮
        contentView.findViewById(R.id.dialog_check_mobile_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobileNo_str = mobileNo.getText().toString();
                String verificationCode_str = verificationCode.getText().toString();

                if (TextUtils.isEmpty(mobileNo_str)
                        || mobileNo_str.length() != 11
                        || !TextUtils.isDigitsOnly(mobileNo_str)
                        || TextUtils.isEmpty(verificationCode_str)
                        || verificationCode_str.length() < 4){
                    Toast.makeText(activity, "请输入正确的手机号和验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                verificationCode.setText("");
                SMSSDK.submitVerificationCode("86", mobileNo_str, verificationCode_str);
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)activity.getResources().getDimension(R.dimen.x615);
        params.height = (int)activity.getResources().getDimension(R.dimen.y981);
        dialog.getWindow().setAttributes(params);
        dialog.show();

    }

    private static int remainTime;
    static Timer remainTimer;
    private static void countdownStart(final Activity activity, final TextView sendVerificationCode, final TextView countdownStr, int seconds){

        remainTime = seconds;

        remainTimer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                remainTime--;

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        countdownStr.setText("剩余 " + remainTime +" 秒");

                        if (0 == remainTime){

                            countdownStr.setVisibility(View.GONE);
                            sendVerificationCode.setVisibility(View.VISIBLE);

                            remainTimer.cancel();
                        }
                    }
                });


            }
        };

        remainTimer.schedule(timerTask,0,1000);

    }


    //显示发现新版本对话框
    public static void showDownloadDialog(final Context context, final String url) {
        final Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_download,null);
        dialog.setContentView(contentView);

        TextView dialogOkTv = (TextView)contentView.findViewById(R.id.dialog_download_ok_tv);
        TextView dialogCancelTv = (TextView)contentView.findViewById(R.id.dialog_download_cancel_tv);

        dialogOkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyService.class);
                intent.putExtra("url",url);
                context.startService(intent);

                //退出应用
                ActivityCollector.finishAllActivity();
            }
        });

        dialogCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int)context.getResources().getDimension(R.dimen.x650);
        params.height = (int)context.getResources().getDimension(R.dimen.y331);
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }
}
