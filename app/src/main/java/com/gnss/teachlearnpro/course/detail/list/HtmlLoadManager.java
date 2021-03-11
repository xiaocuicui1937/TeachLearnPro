package com.gnss.teachlearnpro.course.detail.list;

import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class HtmlLoadManager {

    private WebView mWebView;

    public HtmlLoadManager() {

    }

    public void initWebView(FrameLayout rootLayout) {
        //创建一个LayoutParams宽高设定为全屏
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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


    public void loadHtmlCode(String detailHtml) {

        //图片宽度改为100%  高度为自适应
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n" +
                "{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
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
