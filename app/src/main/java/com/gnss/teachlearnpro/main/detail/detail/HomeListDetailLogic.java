package com.gnss.teachlearnpro.main.detail.detail;

import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.main.detail.detail.info.InfoDetailLogic;
import com.gnss.teachlearnpro.main.detail.detail.studentwitness.StudentWitnessDetailLogic;

public class HomeListDetailLogic extends BaseLogic {
    private ActivityProvider mProvider;
    private HomeListViewModel.HomeListType mType;
    private StudentWitnessDetailLogic mStudentLogic;
    private InfoDetailLogic mInfoDetailLogic;

    public HomeListDetailLogic(ActivityProvider provider, HomeListViewModel.HomeListType type) {
        this.mProvider = provider;
        this.mType = type;
        initView();
    }

    private void initView() {

        if (mType == HomeListViewModel.HomeListType.INFO) {
            mInfoDetailLogic = new InfoDetailLogic(mProvider);
        } else if (mType == HomeListViewModel.HomeListType.STUDENT_WITNESS) {
            mStudentLogic = new StudentWitnessDetailLogic(mProvider);
        }
    }


    public void destroyWebView() {
        if (mStudentLogic != null) {
            mStudentLogic.destroyWebView();
        }
        if (mInfoDetailLogic != null) {
            mInfoDetailLogic.destroyWebView();
        }
    }
}
