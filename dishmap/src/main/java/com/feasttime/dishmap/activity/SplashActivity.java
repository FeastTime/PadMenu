package com.feasttime.dishmap.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.permission.Permission;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.feasttime.dishmap.permission.Permission.allPermissions;
import static com.feasttime.dishmap.permission.Permission.getPermission;

/**
 * Created by chen on 2017/9/24.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        requestPermission();


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] != 0){

            showPermissionDialog();
        } else {
            requestPermission();
        }

    }



    private void showPermissionDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限拒绝");
        builder.setMessage("是否退出app？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermission();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean requestPermission(){


        if (! EasyPermissions.hasPermissions(this, allPermissions)) {

            List<Permission> permissionList = getPermission();

            for (Permission permission : permissionList) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ! EasyPermissions.hasPermissions(this, permission.getName())){

                    ActivityCompat.requestPermissions(this, new String[]{permission.getName()}, permission.getId());
                    break;
                }
            }

            return false;

        } else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            },2000);

            return true;

        }


    }
}
