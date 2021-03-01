package com.gnss.teachlearnpro.profile.detail.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.yun.utils.image.ImageUtils;
import com.yun.utils.image.QRCodeUtil;

import java.util.concurrent.TimeUnit;

public class ShareAppLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private Bitmap mergeBitmap;

    public ShareAppLogic(FragmentProvider provider) {
        mProvider = provider;
        setTitle(provider, "分享", (AppCompatActivity) provider.getActivity());
        createQr();
        provider.getMineView().findViewById(R.id.tv_save_share_app_lengthpic).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                saveQRCodeToDidk();
                return true;
            }
        });
    }

    private void createQr() {
        ImageView qrCodeIv = mProvider.getMineView().findViewById(R.id.iv_share_app_qrcode);
        Bitmap qrCodeBitmap = QRCodeUtil.INSTANCE.createQRCodeBitmap("http://www.baidu.com", SizeUtils.dp2px(200), SizeUtils.dp2px(200));
        qrCodeIv.setImageBitmap(qrCodeBitmap);
        Bitmap saveQrBitmap = com.blankj.utilcode.util.ImageUtils.compressByScale(qrCodeBitmap, SizeUtils.dp2px(120),SizeUtils.dp2px(120));
        Bitmap bgBitmap = BitmapFactory.decodeResource(mProvider.getResources(), R.drawable.bg_share_app);
        int offsetX= (bgBitmap.getWidth()-saveQrBitmap.getWidth())/2;
        int offsetY= ((bgBitmap.getHeight()-saveQrBitmap.getHeight())/2-SizeUtils.dp2px(10));

        mergeBitmap = mergeBitmap(bgBitmap,saveQrBitmap, offsetX, offsetY);
//        view.setImageBitmap(mergeBitmap);
    }

    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, float width, float height) {
        if (firstBitmap == null || secondBitmap == null) return null;
        Bitmap bitmap = Bitmap.createBitmap(
                firstBitmap.getWidth(), firstBitmap.getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, 0f, 0f, null);
        canvas.save();
        canvas.drawBitmap(secondBitmap, width, height, null);
        canvas.restore();
        return bitmap;
    }



    private void saveQRCodeToDidk() {
        View root = mProvider.getMineView();
        RelativeLayout rooLayout = root.findViewById(R.id.root_qr_layout);
        ImageUtils.INSTANCE.saveImageToFile(rooLayout.getContext(),mergeBitmap , Contact.SHARE_APP_PIC);


    }


}
