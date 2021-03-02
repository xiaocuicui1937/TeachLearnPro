package com.gnss.teachlearnpro.profile.detail.profileinfo;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UiMessageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.GlideEngine;
import com.gnss.teachlearnpro.common.bean.AvatarResBean;
import com.gnss.teachlearnpro.common.bean.ProfileBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.profile.ProfileViewModel;
import com.gnss.teachlearnpro.profile.detail.location.ProfileLocationFragment;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;

public class ProfileInfoLogic extends BaseLogic implements View.OnClickListener {
    private FragmentProvider mProvider;
    private ProfileViewModel model;
    private ProfileBean.DataBean mProfileInfo;
    private SuperTextView stvNick;
    private RoundedCorners roundedCorners = new RoundedCorners(50);
    private AppCompatTextView mRightTextAvator;

    public ProfileInfoLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(mProvider, "个人信息", (AppCompatActivity) provider.getActivity());
        initView();
        addRes();
    }

    private void addRes() {
        AppCompatActivity act = (AppCompatActivity) mProvider.getActivity();
        model = new ViewModelProvider(act).get(ProfileViewModel.class);
        model.getUpdateAvator().observe(act, new Observer<AvatarResBean>() {
            @Override
            public void onChanged(AvatarResBean avatarResBean) {
                model.updatePerson(mProfileInfo.getNickname(), Contact.BASE_PIC_URL + avatarResBean.getData(), String.valueOf(mProfileInfo.getProvince_id())
                        , String.valueOf(mProfileInfo.getCity_id()), String.valueOf(mProfileInfo.getArea_id()),
                        String.valueOf(mProfileInfo.getSex()));
            }
        });
    }


    private void initView() {
        String profile = CacheDiskStaticUtils.getString(Contact.PROFILE);
        mProfileInfo = GsonUtils.fromJson(profile, ProfileBean.DataBean.class);
        SuperTextView stvAvator = mProvider.getMineView().findViewById(R.id.stv_fragment_profile_avator);
        stvNick = mProvider.getMineView().findViewById(R.id.stv_fragment_profile_nick);
        SuperTextView stvLocation = mProvider.getMineView().findViewById(R.id.stv_fragment_profile_location);
        mRightTextAvator = stvAvator.getRightTextView();


        if (mProfileInfo != null) {

            setAvator(mProfileInfo.getAvatar());
            stvNick.setRightString(mProfileInfo.getNickname());
            if (ObjectUtils.isNotEmpty(mProfileInfo.getProvince_name()) && ObjectUtils.isNotEmpty(mProfileInfo.getCity_name())) {
                stvLocation.setRightString(mProfileInfo.getProvince_name() + mProfileInfo.getCity_name() + mProfileInfo.getArea_name());
            } else {
                stvLocation.setRightString("");
            }
        }

        stvLocation.setOnClickListener(this);
        stvNick.setOnClickListener(this);
        stvAvator.setOnClickListener(this);
        updateAddress(stvLocation);
    }

    private void setAvator(String url) {
        int imageWidth = SizeUtils.dp2px(70);
        Glide.with(mRightTextAvator.getContext())
                .load(url).apply(RequestOptions.bitmapTransform(roundedCorners).diskCacheStrategy(DiskCacheStrategy.NONE).override(imageWidth, imageWidth)).into(new ViewTarget<TextView, Drawable>(mRightTextAvator) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                view.setCompoundDrawablesWithIntrinsicBounds(resource, null, ContextCompat.getDrawable(mRightTextAvator.getContext(), R.drawable.ic_right_arraw), null);
            }
        });
    }

    /**
     * 更新地址
     *
     * @param stvLocation SuperTextView
     */
    private void updateAddress(SuperTextView stvLocation) {
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        UiMessageUtils.getInstance().addListener(localMessage -> {
            if (localMessage.getId() == Contact.SELECTED_ADDRESS_LOCATION) {
                parseLocationWithGd(stvLocation, localMessage);
            } else {
                parseLocationWithSelected(stvLocation, instance);
            }

        });
    }

    private void parseLocationWithSelected(SuperTextView stvLocation, CacheMemoryUtils instance) {
        MultipleItemEntity provinceEntity = instance.get(Contact.SELECTED_PROVINCE);
        MultipleItemEntity cityEntity = instance.get(Contact.SELECTED_CITY);
        MultipleItemEntity areaEntity = instance.get(Contact.SELECTED_AREA);
        String provinceStr = provinceEntity.getField(Contact.TITLE);
        String cityStr = cityEntity.getField(Contact.TITLE);
        String areaStr = areaEntity.getField(Contact.TITLE);
        stvLocation.setRightString(provinceStr + cityStr + areaStr);
        String mProvinceId = provinceEntity.getField(Contact.ID);
        String mCityId = cityEntity.getField(Contact.ID);
        String mAreaId = areaEntity.getField(Contact.ID);

        model.updatePerson(mProfileInfo.getNickname(), mProfileInfo.getAvatar(), mProvinceId, mCityId, mAreaId, String.valueOf(mProfileInfo.getSex()));
    }

    private void parseLocationWithGd(SuperTextView stvLocation, UiMessageUtils.UiMessage localMessage) {
        String location = (String) localMessage.getObject();
        String[] addresses = location.split(",");
        stvLocation.setRightString(addresses[0]);
        String mProvinceId = addresses[1];
        String mCityId = addresses[2];
        String mAreaId = addresses[3];
        model.updatePerson(mProfileInfo.getNickname(), mProfileInfo.getAvatar(), mProvinceId, mCityId, mAreaId, String.valueOf(mProfileInfo.getSex()));

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_fragment_profile_location:
                createLocation();
                break;
            case R.id.stv_fragment_profile_nick:
                createNick();
                break;
            case R.id.stv_fragment_profile_avator:
                createAvator();
                break;
            default:
                break;
        }
    }

    private void createAvator() {

        EasyPhotos.createAlbum(mProvider.getActivity(), true, GlideEngine.getInstance())
                .setFileProviderAuthority(AppUtils.getAppPackageName() + ".fileprovider")
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        String path = photos.get(0).path;
                        setAvator(path);
                        //上传图片
                        model.updateAvator(path);
                    }
                });

    }

    private void createNick() {
        new XPopup.Builder(mProvider.getActivity())
                .asInputConfirm("修改昵称", "", "请输入昵称", text -> {
                    if (ObjectUtils.isNotEmpty(text)) {
                        stvNick.setRightString(text);
                        model.updatePerson(text, mProfileInfo.getAvatar(), String.valueOf(mProfileInfo.getProvince_id())
                                , String.valueOf(mProfileInfo.getCity_id()), String.valueOf(mProfileInfo.getArea_id()),
                                String.valueOf(mProfileInfo.getSex()));
                    } else {
                        ToastUtils.showShort("请输入昵称!");
                    }

                }).show();
    }

    private void createLocation() {
        FragmentUtils.replace(FragmentUtils.getTopShow(mProvider.getParentFragmentManager())
                , ProfileLocationFragment.newInstance(), true);
    }

}
