package com.gnss.teachlearnpro.common;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.gnss.teachlearnpro.common.bean.LocationResBean;
import com.gnss.teachlearnpro.profile.detail.location.ProfileLocationViewModel;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LocationHelper implements AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private GeocodeSearch geocodeSearch;
    private ILocationCallback iLocationCallback;

    public LocationHelper(ILocationCallback iLocationCallback) {
        this.iLocationCallback = iLocationCallback;
        goLocation();
    }

    private void goLocation() {
        Context context = ActivityUtils.getTopActivity().getApplicationContext();
        if (geocodeSearch == null) {
            geocodeSearch = new GeocodeSearch(context);
        }
        geocodeSearch.setOnGeocodeSearchListener(this);
        //获取位置信息
        if (locationClient == null) {
            locationClient = new AMapLocationClient(context);
        }
        //初始化定位参数
        if (locationOption == null) {
            locationOption = new AMapLocationClientOption();
        }
        //设置定位监听
        locationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(1000);
        locationOption.setOnceLocation(true);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
//            LogUtils.i(aMapLocation.toString());
            double wd = aMapLocation.getLatitude();
            double jd = aMapLocation.getLongitude();
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(wd, jd), 1000, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(query);

        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            LogUtils.e("location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
        }
        locationClient.stopLocation();
        locationClient.onDestroy();
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i != 1000) {
            return;
        }
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        String currentPosition = regeocodeAddress.getProvince() + regeocodeAddress.getCity() + regeocodeAddress.getDistrict();

        int provinceId = parse(ProfileLocationViewModel.ELocation.PROVINCE, regeocodeAddress.getProvince());
        int cityId = parse(ProfileLocationViewModel.ELocation.CITY, regeocodeAddress.getCity());


        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        act.runOnUiThread(() -> {
            ProfileLocationViewModel model = new ViewModelProvider(act).get(ProfileLocationViewModel.class);
            model.obtainNextLocation(ProfileLocationViewModel.ELocation.CITY, String.valueOf(provinceId));
            model.getNextLocation().observe(act, s -> {
                if (s.contains(String.valueOf(provinceId))) {
                    model.obtainNextLocation(ProfileLocationViewModel.ELocation.AREA, String.valueOf(cityId));

                } else if (iLocationCallback != null) {
                    ThreadUtils.runOnUiThreadDelayed(() -> {
                        int areaId = parse(ProfileLocationViewModel.ELocation.AREA, regeocodeAddress.getDistrict());
                        iLocationCallback.onLocation(currentPosition, provinceId + "," + cityId + "," + areaId);
                    }, 1500);
                }
            });
        });


    }

    private int parse(ProfileLocationViewModel.ELocation eLocation, String src) {
        String location = CacheDiskStaticUtils.getString(eLocation.name());
        List<LocationResBean.DataBean> datas = GsonUtils.fromJson(location, new TypeToken<List<LocationResBean.DataBean>>() {
        }.getType());
        if (ObjectUtils.isEmpty(datas)) {
            return -1;
        }
        for (LocationResBean.DataBean param : datas) {
            if (src.contains(param.getName())) {
                return param.getId();
            }
        }
        return -1;
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    public interface ILocationCallback {
        void onLocation(String location, String locationCode);
    }
}
