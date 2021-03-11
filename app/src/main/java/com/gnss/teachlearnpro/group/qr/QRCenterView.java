package com.gnss.teachlearnpro.group.qr;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Tools;
import com.lxj.xpopup.core.CenterPopupView;
import com.yun.utils.image.ImageUtils;

public class QRCenterView extends CenterPopupView {
    private String mWechatImgUrl;
    private String mTitle;

    public QRCenterView(@NonNull Context context, String title, String wechatImgUrl) {
        super(context);
        this.mTitle = title;
        this.mWechatImgUrl = wechatImgUrl;
    }

    @Override
    protected int getImplLayoutId() {

        return R.layout.layout_qrcode;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        TextView tvTitle  = findViewById(R.id.tv_layout_qrcode_title);
        tvTitle.setText(mTitle);
        findViewById(R.id.iv_layout_qrcode_close).setOnClickListener(view -> {
            dismiss();
        });
        ImageView ivQRCode = findViewById(R.id.iv_layout_qrcode);
        Glide.with(getContext()).load(mWechatImgUrl).into(ivQRCode);
//        TextView tvSaveQRCode = findViewById(R.id.tv_layout_qrcode_save_qr);
        ivQRCode.setOnLongClickListener(view -> {
            saveQRCode(Tools.DrawableToBitmap(ivQRCode.getDrawable()));
            return true;
        });
    }


    private void saveQRCode(Bitmap qrBitmap) {
        ImageUtils.INSTANCE.saveImageToFile(getContext(), qrBitmap, TimeUtils.getNowString());
    }
}
