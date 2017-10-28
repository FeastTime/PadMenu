package com.feasttime.dishmap.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.utils.UtilTools;

/**
 * Created by chen on 2017/10/25.
 */

public class MyDialogs {
    public static void showEatDishPersonNumDialog(Context context) {
        Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.eat_dish_dialog_layout,null);
        dialog.setContentView(contentView);

        Button confirm = (Button)contentView.findViewById(R.id.eat_dish_dialog_confirm_btn);
        EditText personNum = (EditText)contentView.findViewById(R.id.eat_dish_dialog_person_num_et);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public static void showRobSeatDialog(Context context) {
//        Dialog dialog = new Dialog(context,R.style.DialogTheme);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View contentView = inflater.inflate(R.layout.eat_dish_dialog_layout,null);
//        dialog.setContentView(contentView);
//
//        Button confirm = (Button)contentView.findViewById(R.id.eat_dish_dialog_confirm_btn);
//        EditText personNum = (EditText)contentView.findViewById(R.id.eat_dish_dialog_person_num_et);
//
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.width = (int)context.getResources().getDimension(R.dimen.x610);
////        params.height = (int)context.getResources().getDimension(R.dimen.y810);
//        dialog.getWindow().setAttributes(params);
//        dialog.show();

    }


    //抢座位结果
    public static void showRobSeatReasultDialog(Context context) {
//        Dialog dialog = new Dialog(context,R.style.DialogTheme);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View contentView = inflater.inflate(R.layout.eat_dish_dialog_layout,null);
//        dialog.setContentView(contentView);
//
//        Button confirm = (Button)contentView.findViewById(R.id.eat_dish_dialog_confirm_btn);
//        EditText personNum = (EditText)contentView.findViewById(R.id.eat_dish_dialog_person_num_et);
//
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.width = (int)context.getResources().getDimension(R.dimen.x610);
////        params.height = (int)context.getResources().getDimension(R.dimen.y810);
//        dialog.getWindow().setAttributes(params);
//        dialog.show();

    }
}
