package com.gnss.teachlearnpro.main.detail.detail.studentwitness;

import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.HomeDetailBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.main.detail.detail.HomeListViewModel;

public class StudentWitnessDetailLogic extends BaseLogic {
    private ActivityProvider mProvider;
    private WebView mWebView;
    private TextView mTvTitle;
    private ImageView mIv;

    public StudentWitnessDetailLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle(provider.getIntent().getStringExtra(Contact.TITLE));
        initView();
        addRequestListener();
    }

    private void addRequestListener() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        HomeListViewModel model = new ViewModelProvider(act).get(HomeListViewModel.class);
        int id = mProvider.getIntent().getIntExtra(Contact.ID,-1);
        showLoading("获取学员见证详情");
        model.obtainDetail(HomeListViewModel.HomeListType.STUDENT_WITNESS, String.valueOf(id));
        model.getList().observe(act, homeDetailBean -> {
            hideLoading();
            mTvTitle.setText(homeDetailBean.title);
            Glide.with(mIv.getContext()).load(Contact.BASE_PIC_URL+homeDetailBean.logoUrl).into(mIv);
           loadHtmlCode(homeDetailBean.content);
        });
    }

    private void initView() {
        mTvTitle = mProvider.findViewById(R.id.tv_list_student_witness_title);
        FrameLayout flContent = mProvider.findViewById(R.id.fl_home_list_content);
        mIv = mProvider.findViewById(R.id.iv_activity_list_student_witness);
        initWebView(flContent);
    }

    private void initWebView(FrameLayout flContent) {
        //创建一个LayoutParams宽高设定为全屏
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //创建WebView
        mWebView = new WebView(flContent.getContext().getApplicationContext());
        //设置WebView的宽高
        mWebView.setLayoutParams(layoutParams);
        //把webView添加到容器中
        flContent.addView(mWebView);

        //在内置浏览器打开页面
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void loadHtmlCode(String detailHtml) {

        //图片宽度改为100%  高度为自适应
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";

        mWebView.loadData(varjs + detailHtml, "text/html", "UTF-8");
    }

    /**
     * 销毁WebView防止内存泄漏
     */
    public void destroyWebView() {
        if (mWebView != null) {
            //加载null内容
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //清除历史记录
            mWebView.clearHistory();
            //移除WebView
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            //销毁VebView
            mWebView.destroy();
            //WebView置为null
            mWebView = null;
        }
    }
}
