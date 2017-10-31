package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.map.LocationCallback;
import com.feasttime.dishmap.map.MyLocation;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity{

    private static final int FOOD_TYPE_HOT_POT = 1;
    private static final int FOOD_TYPE_CHINA = 2;
    private static final int FOOD_TYPE_WESTERN = 3;

    String TAG = "aaa1";
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;

    PoiSearch hotpotPoiSearch;
    PoiSearch chinaFoodPoiSearch;
    PoiSearch westernPoiSearch;
    // 火锅监听
    OnGetPoiSearchResultListener hotpotPoiListener;
    // 中餐监听
    OnGetPoiSearchResultListener chinaFoodPoiListener;
    // 火锅监听
    OnGetPoiSearchResultListener westernFoodPoiListener;
    MyLocation myLocation;
    BDLocation nowLocation = null;


    LinearLayout detailDialog;
    TextView textViewPhoneNO;
    TextView textViewName;
    TextView textViewAddress;
    ImageView playButtonImg;
//    EditText searchText;

    MyMarkerInfo clickMyMarkerInfo;

    private RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();

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

    // 初始化UI
    private void initLayout() {

        detailDialog = (LinearLayout)findViewById(R.id.detail_dialog);
        textViewPhoneNO = (TextView)findViewById(R.id.phoneNO);
        textViewName = (TextView)findViewById(R.id.name);
        textViewAddress = (TextView)findViewById(R.id.address);
        playButtonImg = (ImageView)findViewById(R.id.play_button_img);
//        searchText = (EditText) findViewById(R.id.search_text);

//        searchText.setOnKeyListener(new View.OnKeyListener() {
//
//            @Override
//
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    // hidden keyboard
//                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
//                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    // do search
//                    Toast.makeText(MainActivity.this, "稍后开放此功能", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });

    }

    // 初始化地图信息
    private void initMap() {

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        mMapView.showZoomControls(true);//设置是否显示缩放控件
        mMapView.getChildAt(2).setPadding(0,0,10,400);//这是控制缩放控件的位置
        mMapView.getChildAt(1).setPadding(10000,10000,10100,10100);//这是控制缩放控件的位置

        //开启交通图
//        mBaiduMap.setTrafficEnabled(true);


//        // 设置饭店图标
//        addPoint(getMarkerInfos());

        // 添加点击事件响应
        addMarkerListener();

        // 地图监听器初始化
        initMapListener();


        hotpotPoiSearch = PoiSearch.newInstance();
        chinaFoodPoiSearch = PoiSearch.newInstance();
        westernPoiSearch = PoiSearch.newInstance();


    }

    private void initMapListener() {

        // 火锅搜索结果
        hotpotPoiListener = new OnGetPoiSearchResultListener() {

            public void onGetPoiResult(PoiResult result) {


                //获取POI检索结果
                String json = JSON.toJSONString(result);
                Log.d(TAG, json);

                if (null != result.getAllPoi()){

                    List<MyMarkerInfo> list = getMarkerInfos(result.getAllPoi(), FOOD_TYPE_HOT_POT);
                    addPoint(list);
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

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result) {}

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {}
        };

        // 中国菜搜索结果
        chinaFoodPoiListener = new OnGetPoiSearchResultListener() {

            public void onGetPoiResult(PoiResult result) {

                if (null != result.getAllPoi()){

                    List<MyMarkerInfo> list = getMarkerInfos(result.getAllPoi(), FOOD_TYPE_CHINA);
                    addPoint(list);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result) {}

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {}
        };

        // 西餐搜索结果
        westernFoodPoiListener = new OnGetPoiSearchResultListener() {

            public void onGetPoiResult(PoiResult result) {

                if (null != result.getAllPoi()){

                    List<MyMarkerInfo> list = getMarkerInfos(result.getAllPoi(), FOOD_TYPE_WESTERN);
                    addPoint(list);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result) {}

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {}
        };

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {}

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {}

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {}

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

                boundSearch();
            }
        });

        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {



                // 规划路线结果

                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(MainActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    Toast.makeText(MainActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {

                    if(drivingRouteResult.getRouteLines()!=null) {

                        DrivingRouteLine drivingRouteLine = drivingRouteResult.getRouteLines().get(0);

                        int distance = drivingRouteLine.getDistance();

                        Log.d("lixiaoqing", "distance : " + distance);

                        List allStep = drivingRouteLine.getAllStep();

                        for (int i = 0; i < allStep.size(); i++) {

                        }
                    }
                }

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });
    }

    // 定位
    private void location() {

        showLoading("开足马力为您定位，请稍等！");

        if(null == myLocation){

            myLocation = new MyLocation();

            // 启动定位
            myLocation.init(this.getApplicationContext(), new LocationCallback() {
                @Override
                public void getLocationSuccess(BDLocation location) {

                    nowLocation = location;
                    myLocation.stopLocation();
                    Log.d(TAG, "getLocationSuccess");

                    // 设置当前位置,和缩放级别
                    setLocation(location.getLatitude(), location.getLongitude(), 16);

                    showSelfLocation(location);
//                    search(location.getLatitude(), location.getLongitude(),"饭店");

                    // 基于当前地域进行搜索
                    boundSearch();
                    hideLoading();
                }

                @Override
                public void getLocationFalse(byte errorCode) {

                    hideLoading();

                    Log.d(TAG, "getLocationSuccess");

                    Toast.makeText(MainActivity.this, "定位失败，请打开手机定位功能！", Toast.LENGTH_SHORT).show();
                    myLocation.stopLocation();
                }
            });
        }

        myLocation.startLocation();


    }

    // 设置中心位置，和缩放级别
    private void setLocation(double latitude, double longitude, int leval) {

        Log.d(TAG, "setLocation" + "-----" + latitude + "----" + longitude + "-----: " + leval);
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

                showDialog(clickMyMarkerInfo.getName(), clickMyMarkerInfo.getPhoneNO(), clickMyMarkerInfo.getAddress(), clickMyMarkerInfo.getLatitude(), clickMyMarkerInfo.getLongitude());

                return false;
            }
        });
    }

    // 显示个人位置图标
    private void showSelfLocation(BDLocation location){

        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        mBaiduMap.setMyLocationData(data);
    }


//    private List<MyMarkerInfo> getMarkerInfos() {
//
//        List infos = new ArrayList<MyMarkerInfo>();
//
//        infos.add(new MyMarkerInfo(39.963177, 116.400244, "9998887771", "天津站",R.mipmap.hotpot_red,"天津站，俗称天津东站，隶属北京铁路局管辖", "textViewAddress", "01023432423"));
//        infos.add(new MyMarkerInfo(39.973176, 116.402234, "9998887772",  "南开大学",R.mipmap.hotpot_red,"正式成立于1919年，是由严修、张伯苓秉承教育救国理念创办的综合性大学。", "textViewAddress", "01023432423"));
//
//        return infos;
//    }


    // 转化成地图设置点的格式
    private List<MyMarkerInfo> getMarkerInfos(List<PoiInfo> poiInfos, int foodType) {


        List<MyMarkerInfo> infos = new ArrayList<>(poiInfos.size());

        int i = 0;
        int imageId = R.mipmap.hotpot_red;
        String storeID;

        for (PoiInfo poiInfo : poiInfos) {

            i++;
            storeID = "unknown";

            if (i%4==0){
                storeID = "festTime";
            }

            int mod2 = i%2;

            if (foodType == FOOD_TYPE_HOT_POT){

                if (mod2 == 0)
                    imageId = R.mipmap.hotpot_red;
                else if (mod2 == 1)
                    imageId = R.mipmap.hotpot_yellow;

            } else if (foodType == FOOD_TYPE_CHINA){

                if (mod2 == 0)
                    imageId = R.mipmap.chinese_red;
                else if (mod2 == 1)
                    imageId = R.mipmap.chinese_yellow;

            } else if (foodType == FOOD_TYPE_WESTERN){

                if (mod2 == 0)
                    imageId = R.mipmap.western_red;
                else if (mod2 == 1)
                    imageId = R.mipmap.western_yellow;
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

        hotpotPoiSearch.destroy();
        chinaFoodPoiSearch.destroy();
        westernPoiSearch.destroy();
        stopService(new Intent(this,MyService.class));
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
    private void showDialog(String name, String phoneNO, String address, double latitude, double longitude){

        detailDialog.setVisibility(View.VISIBLE);
        textViewName.setText(name);

        textViewPhoneNO.setText(phoneNO.isEmpty()?"电话暂未提供" : phoneNO);
        textViewAddress.setText(address);

        searchLine(nowLocation, latitude, longitude);//调用路径规划

//        Intent intent = new Intent(this, StoreDetailActivity.class);
//        this.startActivity(intent);

    }

    // 搜索导航路线
    private void searchLine(BDLocation start, double endLatitude, double endLongitude) {


        //重置浏览节点的路线数据
        //route = null;
        mBaiduMap.clear();

        //设置起终点、途经点信息，对于tranist search 来说，城市名无意义
        PlanNode stNode = PlanNode.withLocation(new LatLng(start.getLatitude(), start.getLongitude()));
        PlanNode enNode = PlanNode.withLocation(new LatLng(endLatitude, endLongitude));


        // 实际使用中请对起点终点城市进行正确的设定
        routePlanSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)//起点
                .to(enNode));//终点


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

        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        Intent intent;

        if (TextUtils.isEmpty(token)){
            intent = new Intent(this, LoginActivity.class);

        } else{

            intent = new Intent(this, ScanActivity.class);
        }

        startActivity(intent);

    }

    // 点击定位按钮
    public void touchLocation(View view){
//        Toast.makeText(this, "定位功能马上开启！", Toast.LENGTH_SHORT).show();

        location();
    }

    // 进入我的或者登录
    public void login(View view){
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        Intent intent;

        if (TextUtils.isEmpty(token)){
            intent = new Intent(this, LoginActivity.class);

        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }

    /**
     * 范围检索,范围搜索需要制定坐标.以矩形的方式进行范围搜索.
     */
    private void boundSearch() {

        PoiBoundSearchOption boundSearchOption = new PoiBoundSearchOption();

        LatLng northeast = mBaiduMap.getMapStatus().bound.northeast;
        LatLng southwest = mBaiduMap.getMapStatus().bound.southwest;

        // 搜索火锅
        LatLngBounds bounds = new LatLngBounds.Builder().include(southwest).include(northeast).build();// 得到一个地理范围对象
        boundSearchOption.bound(bounds);// 设置poi检索范围
        boundSearchOption.pageNum(1);
        boundSearchOption.pageCapacity(6);
        boundSearchOption.keyword("火锅");// 检索关键字
        hotpotPoiSearch.setOnGetPoiSearchResultListener(hotpotPoiListener);
        hotpotPoiSearch.searchInBound(boundSearchOption);// 发起poi范围检索请求

        // 搜索中餐
        bounds = new LatLngBounds.Builder().include(southwest).include(northeast).build();// 得到一个地理范围对象
        boundSearchOption.bound(bounds);// 设置poi检索范围
        boundSearchOption.pageNum(1);
        boundSearchOption.pageCapacity(6);
        boundSearchOption.keyword("中餐");// 检索关键字
        chinaFoodPoiSearch.setOnGetPoiSearchResultListener(chinaFoodPoiListener);
        chinaFoodPoiSearch.searchInBound(boundSearchOption);// 发起poi范围检索请求

        // 搜索西餐
        bounds = new LatLngBounds.Builder().include(southwest).include(northeast).build();// 得到一个地理范围对象
        boundSearchOption.bound(bounds);// 设置poi检索范围
        boundSearchOption.pageNum(1);
        boundSearchOption.pageCapacity(6);
        boundSearchOption.keyword("西餐");// 检索关键字
        westernPoiSearch.setOnGetPoiSearchResultListener(westernFoodPoiListener);
        westernPoiSearch.searchInBound(boundSearchOption);// 发起poi范围检索请求

    }





}
