package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.feasttime.dishmap.R;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void touchMenu(View view){
        Toast.makeText(this, "点餐功能马上开启！", Toast.LENGTH_SHORT).show();
    }
}
