package com.ipinyou.ads.tools;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ipinyou.ads.bean.ScreenInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.LOCATION_SERVICE;

public class DeviceTool {


    //	获取android ID
    public static String getAndroidId(Context context) {

        String result = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (StringUtils.isNotEmpty(result))

            return result;

        return "";
    }

    // imei 如果缺失运行时权限，
    public static String getIMEI(Context context) {

        String result = "";

        try {

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getDeviceId();

        } catch (Exception ignored) {
        }

        if (StringUtils.isEmpty(result))

            result = getAndroidId(context);

        return result;
    }

    // 获取网络制式 // 获取品牌  产品经理设计的， 跟程序员无关
    public static String getCarrierName(Context context) {

        String mc = "";

        try {

            final TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String subscriberId = telephonyManager.getSubscriberId();

            if (subscriberId != null) {

                if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002")) {
                    //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号 //中国移动
                    mc = "Mobile";
                } else if (subscriberId.startsWith("46001")) {
                    mc = "Unicom";
                } else if (subscriberId.startsWith("46003")) {
                    mc = "Telecom";
                }
            }

        } catch (Exception ignored) {
        }

        if (StringUtils.isEmpty(mc))
            mc = "Wifi";

        return mc;
    }

    // 获取品牌  产品经理设计的， 跟程序员无关
    public static String getDeviceBrand() {

        String brand = Build.BRAND;

        if (StringUtils.isEmpty(brand))
            return "";

        if (brand.toLowerCase().equals("samsung"))
            return "Samsung";

        if (brand.toLowerCase().equals("htc"))
            return "HTC";

        if (brand.toLowerCase().equals("nokia"))
            return "Nokia";

        if (brand.toLowerCase().equals("motorola"))
            return "Motorola";

        if (brand.toLowerCase().equals("sony"))
            return "Sony";

        if (brand.toLowerCase().equals("huawei"))
            return "Huawei";

        if (brand.toLowerCase().equals("lenovo"))
            return "Lenovo";

        if (brand.toLowerCase().equals("meizu"))
            return "Meizu";

        if (brand.toLowerCase().equals("xiaomi"))
            return "Xiaomi";

        if (brand.toLowerCase().equals("acer"))
            return "Acer";

        if (brand.toLowerCase().equals("asus"))
            return "Asus";

        if (brand.toLowerCase().equals("microsoft"))
            return "Microsoft";

        if (brand.toLowerCase().equals("benq"))
            return "BenQ";

        if (brand.toLowerCase().equals("dell"))
            return "Dell";

        if (brand.toLowerCase().equals("hp"))
            return "HP";

        if (brand.toLowerCase().equals("lg"))
            return "LG";

        return "NA";
    }

    // 获取品牌  产品经理设计的， 跟程序员无关
    public static String getDeviceModel() {

        String model = Build.MODEL;

        if (StringUtils.isNotEmpty(model))

            return model;

        return "";
    }

    public static String getAndroidVersion() {


        String version = Build.VERSION.SDK;

        if (StringUtils.isNotEmpty(version))

            return version;

        return "";
    }


    public static ScreenInfo getDeviceScreenInfo(Context context) {

        ScreenInfo screenInfo = new ScreenInfo();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels; // 屏幕宽度（像素）
        int height = displayMetrics.heightPixels; // 屏幕高度（像素）
        float density = displayMetrics.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = displayMetrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）

        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;

        try {

            double x = Math.pow(width / xdpi, 2);
            double y = Math.pow(height / ydpi, 2);
//            double screenInches = Math.sqrt(x + y);

            screenInfo.setWidth(width);
            screenInfo.setHeight(height);
            screenInfo.setResolution(density);
            screenInfo.setDpi(densityDpi);

        } catch (Exception ignored) {
        }

        return screenInfo;
    }

    public static String getDeviceType(Context context) {

        try {

            boolean xlarge = false;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                xlarge = ((context.getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) ==
                        Configuration.SCREENLAYOUT_SIZE_XLARGE);
            }

            // If XLarge, checks if the Generalized Density is at least MDPI
            // (160dpi)
            if (xlarge) {
                DisplayMetrics metrics = new DisplayMetrics();
                Activity activity = (Activity) context;
                activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

                // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
                // DENSITY_TV=213, DENSITY_XHIGH=320

                if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH || metrics.densityDpi == DisplayMetrics.DENSITY_TV || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                    return "tablet";
                }
            }


            // No, this is not a tablet!
            return "phone";
        } catch (Exception ignored) {
            return "mobile";
        }

    }

    public static String getIP(Context context) {

        String IP = "";

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            IP = intToIp(ipAddress);
        }

        if (StringUtils.isEmpty(IP)) {

            IP = getLocalIpAddress();
        }

        return IP;
    }

    private static String getLocalIpAddress() {

        try {

            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements(); ) {

                NetworkInterface networkInterface = enumeration.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress())

                        return inetAddress.getHostAddress().toString();
                }
            }

        } catch (SocketException ignored) {
        }

        return "";
    }

    private static String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    private static LocationManager locationManager;
    private static boolean isStartLocation = false;

    private static Location pyLocation;
    private LocationListener locationListener ;

    public static Location getLocation(){

        return pyLocation;
    }

    public void startLocation(final Context context) {

        locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        &&ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                pyLocation = locationManager.getLastKnownLocation(provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
                pyLocation = null;
            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                pyLocation = location;
            }
        };
        if (!isStartLocation) {
            isStartLocation = true;
            locationManager = (LocationManager) context.getApplicationContext().getSystemService(LOCATION_SERVICE);
            //从GPS_PROVIDER获取最近的定位信息


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    &&ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            pyLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //设置每60秒，每移动十米向LocationProvider获取一次GPS的定位信息
            //当LocationProvider可用，不可用或定位信息改变时，调用updateView,更新显示
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, locationListener);
        }
    }

    private void stopLocation(Context context){

        if (null != locationManager){
            locationManager.removeUpdates(locationListener);
            locationListener = null;
            locationManager = null;
        }
    }

}
