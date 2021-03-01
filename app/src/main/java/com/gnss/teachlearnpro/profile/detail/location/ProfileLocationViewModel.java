package com.gnss.teachlearnpro.profile.detail.location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LocationResBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class ProfileLocationViewModel extends BaseViewModel {
    private MutableLiveData<String> mMutableLocation = new MutableLiveData<>();
    private MutableLiveData<String> mMutableNextLocation = new MutableLiveData<>();

    public void obtainProvince() {
        EasyHttp.post("Area/getProvinceList")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableLocation.postValue(null);
                        tipError(e, "访问获取省份接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        MeLog.e("province:" + s);
                        saveLocationInfo(ELocation.PROVINCE,s);
                        mMutableLocation.postValue(s);
                    }
                });
    }

    public LiveData<String> getLocation() {
        return mMutableLocation;
    }

    public enum ELocation {
        PROVINCE, CITY, AREA
    }

    /**
     * 获取地址
     *
     * @param location ILocation
     * @param parentId 上一级地址id
     */
    public void obtainNextLocation(ELocation location, String parentId) {
        EasyHttp.post(getUrl(location))
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("parent_id", parentId)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableNextLocation.postValue(null);
                        tipError(e, getTip(location));
                    }

                    @Override
                    public void onSuccess(String s) {
                        MeLog.e("province:" + s);
                        saveLocationInfo(location,s);
                        mMutableNextLocation.postValue(s);
                    }
                });
    }

    private void saveLocationInfo(ELocation location, String res) {
        LocationResBean locationRes = GsonUtils.fromJson(res, LocationResBean.class);
        if (locationRes.isSuccess()){
            CacheDiskStaticUtils.put(location.name(),GsonUtils.toJson(locationRes.getData()));
        }
    }

    private String getTip(ELocation eLocation) {
        if (eLocation == ELocation.CITY) {
            return "访问获取城市接口失败";
        }
        if (eLocation == ELocation.AREA) {
            return "访问获取区县接口失败";
        }
        return "";
    }

    private String getUrl(ELocation eLocation) {
        if (eLocation == ELocation.CITY) {
            return "Area/getCityList";
        }
        if (eLocation == ELocation.AREA) {
            return "Area/getAreaList";
        }
        return "";
    }

    public LiveData<String> getNextLocation() {
        return mMutableNextLocation;
    }
}
