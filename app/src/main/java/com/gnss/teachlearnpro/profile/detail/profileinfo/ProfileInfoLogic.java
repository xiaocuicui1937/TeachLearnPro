package com.gnss.teachlearnpro.profile.detail.profileinfo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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
import com.yalantis.ucrop.UCrop;

import java.io.File;
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
                model.updatePerson(mProfileInfo.getNickname(),avatarResBean.getData(), String.valueOf(mProfileInfo.getProvince_id())
                        , String.valueOf(mProfileInfo.getCity_id()), String.valueOf(mProfileInfo.getArea_id()),
                        String.valueOf(mProfileInfo.getSex()));
            }
        });
        UiMessageUtils.getInstance().addListener(Contact.AVATAR_CORP_PATH, localMessage -> {
            String path = (String) localMessage.getObject();
            setAvator(path);
            //上传图片
            model.updateAvator(path);
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
            if (ObjectUtils.isNotEmpty(mProfileInfo.getProvince_name()) &&
                    ObjectUtils.isNotEmpty(mProfileInfo.getCity_name())) {
                stvLocation.setRightString(mProfileInfo.getProvince_name() + mProfileInfo.getCity_name()
                        + mProfileInfo.getArea_name());
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
            int id = localMessage.getId();
            if (id == Contact.AVATAR_CORP_PATH) {
                return;
            }
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
                        starUCrop(photos.get(0).uri);

                    }
                });
    }

    private void starUCrop(Uri sourceUri) {
        File file = new File(Environment.getExternalStorageDirectory() + "/crop");
        if (!file.exists()) {
            file.mkdirs();
        }
        UCrop.Options options = new UCrop.Options();
        //裁剪后图片保存在文件夹中
        Uri destinationUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/crop", "uCrop.jpg"));
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);//第一个参数是裁剪前的uri,第二个参数是裁剪后的uri
        uCrop.withAspectRatio(1, 1);//设置裁剪框的宽高比例
        //下面参数分别是缩放,旋转,裁剪框的比例
        options.setAllowedGestures(com.yalantis.ucrop.UCropActivity.ALL, com.yalantis.ucrop.UCropActivity.NONE, com.yalantis.ucrop.UCropActivity.ALL);
        options.setToolbarTitle("裁剪头像");//设置标题栏文字
        options.setCropGridStrokeWidth(2);//设置裁剪网格线的宽度(我这网格设置不显示，所以没效果)
        //options.setCropFrameStrokeWidth(1);//设置裁剪框的宽度
        options.setMaxScaleMultiplier(3);//设置最大缩放比例
        //options.setHideBottomControls(true);//隐藏下边控制栏
        options.setShowCropGrid(true);  //设置是否显示裁剪网格
        //options.setOvalDimmedLayer(true);//设置是否为圆形裁剪框
        options.setShowCropFrame(true); //设置是否显示裁剪边框(true为方形边框)
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        options.setCropGridColor(Color.parseColor("#ffffff"));//设置裁剪网格的颜色
        options.setCropFrameColor(Color.parseColor("#ffffff"));//设置裁剪框的颜色
        options.withMaxResultSize(200, 200);
        uCrop.withOptions(options);
        /*//裁剪后保存到文件中
        Uri destinationUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/myxmpp/" + "test1.jpg"));
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.orange2));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.orange2));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        options.setCropGridColor(Color.parseColor("#ffffff"));//设置裁剪网格的颜色
        options.setCropFrameColor(Color.parseColor("#ffffff"));//设置裁剪框的颜色
        //options.setShowCropFrame(false); //设置是否显示裁剪边框(true为方形边框)
        uCrop.withOptions(options);*/
        uCrop.start(mProvider.getActivity());
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
