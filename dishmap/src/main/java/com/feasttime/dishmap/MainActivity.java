package com.feasttime.dishmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.feasttime.dishmap.map.LocationCallback;
import com.feasttime.dishmap.map.MyLocation;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    String TAG = "aaa1";
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    PoiSearch mPoiSearch;
    OnGetPoiSearchResultListener poiListener;
    MyLocation myLocation;

    boolean isLocation = false;

    LinearLayout detailDialog;
    TextView textViewPhoneNO;
    TextView textViewName;
    TextView textViewAddress;
    ImageView playButtonImg;
    EditText searchText;

    MyMarkerInfo clickMyMarkerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化基本控件
        initLayout();

        // 初始化地图
        initMap();

        // 定位
        location();
    }

    private void initLayout() {
        detailDialog = (LinearLayout)findViewById(R.id.detail_dialog);
        textViewPhoneNO = (TextView)findViewById(R.id.phoneNO);
        textViewName = (TextView)findViewById(R.id.name);
        textViewAddress = (TextView)findViewById(R.id.address);
        playButtonImg = (ImageView)findViewById(R.id.play_button_img);
        searchText = (EditText) findViewById(R.id.search_text);

        searchText.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // hidden keyboard
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // do search
                    Toast.makeText(MainActivity.this, "稍后开放此功能", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    private void initMap() {




        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);


        mMapView.showZoomControls(true);//设置是否显示缩放控件
        mMapView.getChildAt(2).setPadding(0,0,10,350);//这是控制缩放控件的位置
        mMapView.getChildAt(1).setPadding(10000,10000,10100,10100);//这是控制缩放控件的位置

        //开启交通图
//        mBaiduMap.setTrafficEnabled(true);


        // 设置饭店图标
        addPoint(getMarkerInfos());

        // 添加点击事件响应
        addMarkerListener();

        poiListener = new OnGetPoiSearchResultListener() {

            public void onGetPoiResult(PoiResult result) {

               Log.d(TAG, result.error +"--" );

                //获取POI检索结果

                String json = JSON.toJSONString(result);
                Log.d(TAG, json);

                if (null != result.getAllPoi()){

                    List<MyMarkerInfo> list = getMarkerInfos(result.getAllPoi());
                    addPoint(list);
//
//                    //遍历所有POI，找到类型为公交线路的POI
//                    for (PoiInfo poi : result.getAllPoi()) {
//
//                        Log.d(TAG, "poi.textViewName ----  " + poi.textViewName);
//                        Log.d(TAG, "poi.textViewAddress ----  " + poi.textViewAddress);
//                        Log.d(TAG, "poi.city ----  " + poi.city);
//                        Log.d(TAG, "poi.phoneNum ----  " + poi.phoneNum);
//                        Log.d(TAG, "poi.location ----  " + poi.location.latitude + "   :   " +  poi.location.longitude);
//                        Log.d(TAG, "poi.type ----  " + poi.type);
//
//                    }
                }


                if (null != result.getAllAddr()){

                    //遍历所有POI，找到类型为公交线路的POI
                    for (PoiAddrInfo poi : result.getAllAddr()) {

                        Log.d(TAG, "poi.textViewName ----  " + poi.name);
                        Log.d(TAG, "poi.textViewAddress ----  " + poi.address);
                        Log.d(TAG, "poi.location ----  " + poi.location.latitude + "   :   " +  poi.location.longitude);

                    }
                }

            }

            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

    }

    private void location() {

        Toast.makeText(this, "定位中。。。", Toast.LENGTH_SHORT).show();

        myLocation = new MyLocation();

        // 启动定位
        myLocation.init(this.getApplicationContext(), new LocationCallback() {
            @Override
            public void getLocationSuccess(BDLocation location) {

                if (!isLocation){

                    isLocation = true;

                    Log.d(TAG, location.getLatitude() + "" + location.getLongitude());

                    // 设置当前位置,和缩放级别
                    setLocation(location.getLatitude(), location.getLongitude(), 16);

                    search(location.getLatitude(), location.getLongitude(),"饭店");
                }
            }

            @Override
            public void getLocationFalse(byte errorCode) {

            }
        });
    }

    // 设置中心位置，和缩放级别
    private void setLocation(double latitude, double longitude, int leval) {

        LatLng latlng = new LatLng(latitude, longitude);

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latlng, leval);

        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

    // 添加图标的点击监听
    private void addMarkerListener() {

        //对 marker 添加点击相应事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                clickMyMarkerInfo = (MyMarkerInfo) marker.getExtraInfo().get("MyMarkerInfo");

                showDialog(clickMyMarkerInfo.getName(), clickMyMarkerInfo.getPhoneNO(), clickMyMarkerInfo.getAddress());

                return false;
            }
        });
    }


    private List<MyMarkerInfo> getMarkerInfos() {

        List infos = new ArrayList<MyMarkerInfo>();

        infos.add(new MyMarkerInfo(39.963177, 116.400244, "9998887771", "天津站",R.mipmap.hotile_blue,"天津站，俗称天津东站，隶属北京铁路局管辖", "textViewAddress", "01023432423"));
        infos.add(new MyMarkerInfo(39.973176, 116.402234, "9998887772",  "南开大学",R.mipmap.hotile_green,"正式成立于1919年，是由严修、张伯苓秉承教育救国理念创办的综合性大学。", "textViewAddress", "01023432423"));

        return infos;
    }


    // 转化成地图设置点的格式
    private List<MyMarkerInfo> getMarkerInfos(List<PoiInfo> poiInfos) {


        List<MyMarkerInfo> infos = new ArrayList<>(poiInfos.size());

        int i = 0;

        for (PoiInfo poiInfo : poiInfos) {

            i++;
            String storeID = "unknown";
            int imageId = R.mipmap.hotile_blue;

            if (i%4==0){
                storeID = "festTime";
                imageId = R.mipmap.store_feast_pin;
            }

            infos.add(new MyMarkerInfo(poiInfo.location.latitude, poiInfo.location.longitude, storeID, poiInfo.name,imageId, "unkonwn", poiInfo.address, poiInfo.phoneNum));
        }

        return infos;
    }


    // 添加图标到地图上
    private void addPoint(List<MyMarkerInfo> myMarkerInfoList) {

        // 经纬度
        LatLng latLng;
        // 操作
        OverlayOptions overlayOptions;
        // 图标
        BitmapDescriptor bitmapDescriptor;
        // 书签
        Marker marker;

        int i = 0;

        for (MyMarkerInfo myMarkerInfo : myMarkerInfoList) {


            latLng = new LatLng(myMarkerInfo.getLatitude(),myMarkerInfo.getLongitude());
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(myMarkerInfo.getImgId());

            //构建MarkerOption，用于在地图上添加Marker
            overlayOptions = new MarkerOptions().position(latLng).icon(bitmapDescriptor);

            //在地图上添加Marker，并显示
            marker = (Marker) mBaiduMap.addOverlay(overlayOptions);

            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("MyMarkerInfo", myMarkerInfo);
            marker.setExtraInfo(bundle);
        }

    }


    // 搜索
    private void search(double latitude, double longitude, String key){

        LatLng latlng = new LatLng(latitude, longitude);
        mPoiSearch.searchNearby(new PoiNearbySearchOption().keyword(key).location(latlng).radius(1000).pageCapacity(50));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

        mPoiSearch.destroy();
        poiListener = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();


    }

    // 显示对话框
    private void showDialog(String name, String phoneNO, String address){

        detailDialog.setVisibility(View.VISIBLE);
        textViewName.setText(name);

        textViewPhoneNO.setText(phoneNO.isEmpty()?"电话暂未提供" : phoneNO);
        textViewAddress.setText(address);
    }

    // 关闭对话框
    public void closeDialog(View view){
        detailDialog.setVisibility(View.GONE);
        clickMyMarkerInfo = null;
    }

    // 展示详情
    public void showDetail(View view){

        Log.d(TAG, "jfkaskfl;skdf");

        if ("festTime".equals(clickMyMarkerInfo.getStoreId())){
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("id", clickMyMarkerInfo.getStoreId());
            startActivity(intent);
        } else {
            Toast.makeText(this, "该店铺还没有开启直播功能！", Toast.LENGTH_SHORT).show();
        }
    }

    // 点击菜单按钮
    public void touchMenu(View view){

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    // 点击定位按钮
    public void touchLocation(View view){
        Toast.makeText(this, "定位功能马上开启！", Toast.LENGTH_SHORT).show();
    }

}
