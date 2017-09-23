package com.feasttime.dishmap.third.onekeyshare;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.feasttime.dishmap.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by chen on 2017/9/23.
 */

public class ShareMain {
    public static void share(final Context context) {
        OnekeyShare oks = new OnekeyShare();
        oks.setAddress("beijing");
        oks.setFilePath("");
        oks.setTitle("shengyan");
        oks.setTitleUrl("feasttime.com");
        oks.setUrl("feasttime.com");
        oks.setText("盛宴时代 ，nice");
        oks.setComment("wonderful");
        oks.setSite("shengyan");
        oks.setSiteUrl("shengyan.com");
        oks.setVenueName("shengyan");
        oks.setVenueDescription("shengyan is a nice company");
        oks.setSilent(true);
        oks.setLatitude(23.169f);
        oks.setLongitude(112.908f);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(context,"share complete",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(context,"share error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(context,"share canceled",Toast.LENGTH_SHORT).show();
            }
        });
//		Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//		String label = context.getString(R.string.app_name);
//		View.OnClickListener listener = new View.OnClickListener() {
//			public void onClick(View v) {
//				String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
//				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//			}
//		};
//		oks.setCustomerLogo(logo, label, listener);
        oks.show(context);
    }
}
