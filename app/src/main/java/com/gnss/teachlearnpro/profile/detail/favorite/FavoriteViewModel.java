package com.gnss.teachlearnpro.profile.detail.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.FavoriteBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class FavoriteViewModel extends BaseViewModel {
    private MutableLiveData<FavoriteBean> mutableFavorite = new MutableLiveData<>();

    public void obtainFavoriteList(FavoriteType type,int pageIndex) {
        EasyHttp.post("Collection/getCollectionList")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("type", getType(type))
                .params("page",String.valueOf(pageIndex))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mutableFavorite.postValue(null);
                        tipError(e, "访问收藏接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        FavoriteBean favorite = GsonUtils.fromJson(s, FavoriteBean.class);
                        if (!favorite.isSuccess()) {
                            ToastUtils.showShort(favorite.getMsg());
                        }
                        mutableFavorite.postValue(favorite);
                    }
                });
    }

    public enum FavoriteType {
        ALL, COURSE, LIVE, GROUP
    }

    private String getType(FavoriteType type) {
        if (type == FavoriteType.ALL) {
            return "0";
        }
        if (type == FavoriteType.COURSE) {
            return "1";
        }
        if (type == FavoriteType.LIVE) {
            return "2";
        }
        if (type == FavoriteType.GROUP) {
            return "3";
        }
        return "";
    }

    public LiveData<FavoriteBean> getFavorite() {
        return mutableFavorite;
    }
}
