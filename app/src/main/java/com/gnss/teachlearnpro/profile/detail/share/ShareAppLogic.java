package com.gnss.teachlearnpro.profile.detail.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SizeUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.yun.utils.image.ImageUtils;
import com.yun.utils.image.QRCodeUtil;

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
        Bitmap saveQrBitmap = com.blankj.utilcode.util.ImageUtils.compressByScale(qrCodeBitmap, SizeUtils.dp2px(160), SizeUtils.dp2px(160));
        Bitmap bgBitmap = BitmapFactory.decodeResource(mProvider.getResources(), R.drawable.bg_share_app);


        int offsetX = (bgBitmap.getWidth() - saveQrBitmap.getWidth()) / 2;
        int offsetY = ((bgBitmap.getHeight() - saveQrBitmap.getHeight()) / 2 - SizeUtils.dp2px(10));

        mergeBitmap = mergeBitmap(bgBitmap, addLogo(saveQrBitmap, BitmapFactory.decodeResource(mProvider.getResources(), R.mipmap.ic_launcher)), offsetX, offsetY);
        Bitmap mergeLogoBitmap = addLogo(saveQrBitmap, BitmapFactory.decodeResource(mProvider.getResources(), R.mipmap.ic_launcher));
        qrCodeIv.setImageBitmap(mergeLogoBitmap);
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

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    private void saveQRCodeToDidk() {
        View root = mProvider.getMineView();
        RelativeLayout rooLayout = root.findViewById(R.id.root_qr_layout);
        ImageUtils.INSTANCE.saveImageToFile(rooLayout.getContext(), mergeBitmap, Contact.SHARE_APP_PIC);
    }


}
