package com.gnss.teachlearnpro.profile.detail.about;

import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.bean.HtmlResBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class AboutLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private WebView mWebView;

    public AboutLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(provider,"关于我们", (AppCompatActivity) provider.getActivity());
        initWebView();
        obtainAboutHtml();
    }

    private void initWebView() {
        LinearLayout rootLayout = mProvider.getMineView().findViewById(R.id.root_about);
        //创建一个LayoutParams宽高设定为全屏
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //创建WebView
        mWebView = new WebView(rootLayout.getContext().getApplicationContext());
        //设置WebView的宽高
        mWebView.setLayoutParams(layoutParams);
        //把webView添加到容器中
        rootLayout.addView(mWebView);

        //在内置浏览器打开页面
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }


    private void obtainAboutHtml() {
        showLoading("正在加载..");
        EasyHttp.post("Personal/getProcess")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        hideLoading();

                    }

                    @Override
                    public void onSuccess(String s) {
                        hideLoading();
                        HtmlResBean html = GsonUtils.fromJson(s, HtmlResBean.class);
                        if (html.isSuccess()) {
                            if (html.getData() != null) {
                                loadHtmlCode(html.getData().getProcess());
                            }
                        } else {
                            ToastUtils.showShort(html.getMsg());
                        }
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
