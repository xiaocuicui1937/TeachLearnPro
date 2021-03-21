package com.gnss.teachlearnpro.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CrashUtils;
import com.ecommerce.common.ui.compent.MeBaseApplication;
import com.ecommerce.melibrary.log.MeConsolePrinter;
import com.ecommerce.melibrary.log.MeLogConfig;
import com.ecommerce.melibrary.log.MeLogManager;
import com.gnss.teachlearnpro.common.Contact;
import com.google.gson.Gson;
//import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.rtmp.TXLiveBase;
import com.zhouyou.http.EasyHttp;

public class MeApplication extends MeBaseApplication {
    private boolean isDebugArouter = true;

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashReport.initCrashReport(this,"2cf01c910a",true);
        initLog();
        initArouter();
        initHttp();
        CrashUtils.init();
        initLiveTecent();
    }

    private void initLiveTecent() {
        String key = "199893d918cbc4dc70866550bdf8c80b";
        String url = "http://license.vod2.myqcloud.com/license/v1/1b3f159e8f1dc4ec3c14b1ef3a5e8b67/TXLiveSDK.licence";
        TXLiveBase.getInstance().setLicence(this, key, url);
    }

    private void initHttp(){
        EasyHttp.init(this);
        EasyHttp.getInstance().setBaseUrl(Contact.BASE_URL).setRetryCount(0);
    }
    private void initLog() {
        MeLogManager.init(new MeLogConfig() {
            @Override
            public JsonParse injectJsonParse() {
                return src -> new Gson().toJson(src);
            }

            @Override
            public String getGlobalTag() {
                return "E-M-Pro";
            }

            @Override
            public boolean enable() {
                return true;
            }

            @Override
            public boolean includeThread() {
                return false;
            }

            @Override
            public int stackTraceLength() {
                return 0;
            }
        }, new MeConsolePrinter());
    }

    private void initArouter() {
        if (isDebugArouter) {
            ARouter.openLog();
            //线上必须关闭，否则有危险
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
