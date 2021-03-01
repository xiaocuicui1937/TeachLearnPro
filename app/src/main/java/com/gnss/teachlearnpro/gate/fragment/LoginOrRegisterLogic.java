package com.gnss.teachlearnpro.gate.fragment;

import android.annotation.SuppressLint;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LoginOrRegisterParamBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.gate.GateActivity;
import com.gnss.teachlearnpro.gate.model.LoginOrRegisterModel;
import com.gnss.teachlearnpro.main.MainActivity;

import java.util.concurrent.TimeUnit;

public class LoginOrRegisterLogic extends BaseLogic implements View.OnClickListener {
    private FragmentProvider fragmentProvider;
    private EditText mEtNick, mEtLoginId, mEtPwd, mEtSurePwd, mEtVerityCode;
    private TextView mTvVeritySendCode, mTvSwitch;
    //是否是注册界面，默认不是
    private boolean isRegister = false;
    private LoginOrRegisterModel mModel;
    private int count = 60;//60s倒计时
    private TextView mTvSubmit;

    public LoginOrRegisterLogic(FragmentProvider fragmentProvider) {
        this.fragmentProvider = fragmentProvider;
        GateActivity act = (GateActivity) fragmentProvider.getMineView().getContext();
        mModel = new ViewModelProvider(act).get(LoginOrRegisterModel.class);
        initView();
        onRes();
    }

    private void initView() {
        mEtNick = fragmentProvider.getMineView().findViewById(R.id.et_fragment_login_register_nick);
        mEtLoginId = fragmentProvider.getMineView().findViewById(R.id.et_fragment_login_register_loginid);
        mEtLoginId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        mEtPwd = fragmentProvider.getMineView().findViewById(R.id.et_fragment_login_register_pwd);
        mEtSurePwd = fragmentProvider.getMineView().findViewById(R.id.et_fragment_login_register_pwd_sure);
        mEtVerityCode = fragmentProvider.getMineView().findViewById(R.id.et_fragment_login_register_veritycode);
        mTvVeritySendCode = fragmentProvider.getMineView().findViewById(R.id.tv_fragment_login_register_vertifycode);
        mTvSwitch = fragmentProvider.getMineView().findViewById(R.id.tv_fragment_login_register_switch);

        mTvVeritySendCode.setOnClickListener(this);
        mTvSwitch.setOnClickListener(this);
        mTvSubmit = fragmentProvider.getMineView().findViewById(R.id.tv_fragment_login_register_submit);
        mTvSubmit.setOnClickListener(this);

    }

    private void onRes() {
        mModel.getVerityCodeLiveData().observe(fragmentProvider.getViewLifecycleOwner(), verityCodeBean -> {
            hideLoading();
            if (ObjectUtils.isEmpty(verityCodeBean)||!isRegister) {
                return;
            }

            if (verityCodeBean.isSuccess()) {
                mTvVeritySendCode.setEnabled(false);
                count60sVerityCode();
            }else{
                ToastUtils.showShort(verityCodeBean.getMsg());
            }
        });

        mModel.getLoginOrRegister().observe(fragmentProvider.getViewLifecycleOwner(), loginOrRegisterBean -> {
            hideLoading();
            if (ObjectUtils.isEmpty(loginOrRegisterBean)) {
                return;
            }
            MeLog.d("登录:"+isRegister+"\n"+loginOrRegisterBean.isSuccess());

            if (loginOrRegisterBean.isSuccess()) {

                if (isRegister) {
                    switchUIWithLoginOrRegister(false);
                    initAllUIData();
                } else {
                    SPUtils.getInstance().put(Contact.TOEKN, loginOrRegisterBean.getData().getToken());
                    //跳转到首页模块
                    ActivityUtils.startActivity(MainActivity.class);
                    //关闭登录注册模块
                    ActivityUtils.finishActivity(GateActivity.class);
                }
            }else{
                ToastUtils.showShort(loginOrRegisterBean.getMsg());
            }
        });
    }

    /**
     * 倒计时
     */
    private void count60sVerityCode() {
        ThreadUtils.executeBySingleAtFixRate(new ThreadUtils.SimpleTask<String>() {
            @Override
            public String doInBackground() throws Throwable {
                count--;
                mTvVeritySendCode.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvVeritySendCode.setText("请等待" + count + "秒");
                    }
                });
                if (count == 0) {
                    mTvVeritySendCode.post(new Runnable() {
                        @Override
                        public void run() {
                            mTvVeritySendCode.setText(StringUtils.getString(R.string.send_verity_code));
                            mTvVeritySendCode.setEnabled(true);
                        }
                    });
                    count = 60;
                    cancel();
                }
                return null;
            }

            @Override
            public void onSuccess(String result) {

            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.tv_fragment_login_register_vertifycode:
                sendVerityCode();
                break;
            case R.id.tv_fragment_login_register_submit:
                submit();
                break;
            case R.id.tv_fragment_login_register_switch:
                switchLoginOrRegister();
                break;

            default:
                break;
        }
    }

    private void submit() {
        showLoading(isRegister ? "注册中..." : "登录中...");
        LoginOrRegisterParamBean param = new LoginOrRegisterParamBean();
        param.phone = getInput(mEtLoginId);
        param.password = getInput(mEtPwd);

        if (isRegister) {
            String mEtCode = getInput(mEtVerityCode);
            if (ObjectUtils.isNotEmpty(mEtCode)) {
                param.code = Integer.parseInt(mEtCode);
            } else {
                param.code = 0;
            }
            param.surePassword = mEtSurePwd.getText().toString().trim();
            param.nickname = getInput(mEtNick);
        } else {
            param.type = Contact.LOGIN_WITH_PHONE;
        }
        mModel.submitLoginOrRegister(isRegister, param);
    }

    private String getInput(EditText et) {
        return et.getText().toString().trim();
    }

    /**
     * 切换登录或者是注册功能
     */
    private void switchLoginOrRegister() {
        String tvSwitch = mTvSwitch.getText().toString();
        MeLog.d(tvSwitch);
        if (ObjectUtils.equals(tvSwitch, StringUtils.getString(R.string.not_login_to_register))) {
            switchUIWithLoginOrRegister(true);
        } else if (ObjectUtils.equals(tvSwitch, StringUtils.getString(R.string.not_register_to_login))) {
            switchUIWithLoginOrRegister(false);
        }
    }

    private void switchUIWithLoginOrRegister(boolean isRegister) {
        this.isRegister = isRegister;
        int visible = isRegister ? View.VISIBLE : View.GONE;
        mEtNick.setVisibility(visible);
        mEtVerityCode.setVisibility(visible);
        mTvVeritySendCode.setVisibility(visible);
        mEtSurePwd.setVisibility(visible);
        mTvSubmit.setText(isRegister ? R.string.register : R.string.login);
        mTvSwitch.setText(isRegister ? R.string.not_register_to_login : R.string.not_login_to_register);
        String tvSwitch = mTvSwitch.getText().toString();
        MeLog.d(tvSwitch + isRegister);
    }

    /**
     * 注册成功将页面数据初始化
     */
    private void initAllUIData() {
        mEtNick.getText().clear();
        mEtVerityCode.getText().clear();
        mEtSurePwd.getText().clear();
        mEtPwd.getText().clear();
        mEtLoginId.getText().clear();

    }

    /**
     * 发送手机验证码
     */
    private void sendVerityCode() {
        showLoading("发送验证码中...");
        mModel.obtainVerityCode(getInput(mEtLoginId), isRegister ? Contact.VERITY_SMS_TYPE : Contact.VERITY_LOGIN_TYPE);
    }
}
