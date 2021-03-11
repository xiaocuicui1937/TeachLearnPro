package com.gnss.teachlearnpro.group.entry;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.BaseResBean;
import com.gnss.teachlearnpro.common.bean.GroupStudyBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.group.GroupStudyFragment;
import com.gnss.teachlearnpro.group.GroupStudyViewModel;

public class GroupStudyEntryLogic extends BaseLogic implements View.OnClickListener {
    private FragmentProvider mProvider;
    private EditText mEtWhat, mEtHow;
    private GroupStudyEntryAdapter adapter;
    private GroupStudyViewModel model;

    public GroupStudyEntryLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(provider, "小组学习", (AppCompatActivity) provider.getActivity());
        initView();
        initRecyclerView();
        addRequestListener();
    }

    @Override
    protected boolean showBack() {
        return false;
    }

    private void addRequestListener() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(GroupStudyViewModel.class);
        showLoading("加载报名小组列表...");
        model.obtainGroupStudyList(1);
        model.getGroupStudy().observe(act, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                GroupStudyBean item = GsonUtils.fromJson(s, GroupStudyBean.class);
                if (item.isSuccess()) {
                    adapter.setNewInstance(item.getData());
                } else {
                    ToastUtils.showShort(item.getMsg());
                }
            }
        });
        model.getGroupStudyEntry().observe(act, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                FragmentUtils.replace(FragmentUtils.getTopShow(mProvider.getParentFragmentManager())
                        , new GroupStudyFragment(),true);
            }
        });

    }


    private void initView() {
        View mineView = mProvider.getMineView();
        //star
        mEtWhat = mineView.findViewById(R.id.et_fragment_group_study_what);
        mEtHow = mineView.findViewById(R.id.et_fragment_group_study_how);
        mineView.findViewById(R.id.tv_fragment_group_entry_submit).setOnClickListener(this);
        LinearLayout rootLayout = mineView.findViewById(R.id.root_group_entry_layout);
        fixContent(rootLayout, mProvider.getResources());

    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_group_courses);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        adapter = new GroupStudyEntryAdapter(R.layout.item_group_entry, null);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {

        if (check()){
            String studyWhat = mEtWhat.getText().toString().trim();
            String studyHow = mEtWhat.getText().toString().trim();
            model.submitGroupStudyEntry(studyWhat,studyHow,CacheMemoryUtils.getInstance().get(Contact.GROUP_SELECTED_PARAM));
        }

    }

    private boolean check() {
        String studyWhat = mEtWhat.getText().toString().trim();
        if (ObjectUtils.isEmpty(studyWhat)) {
            ToastUtils.showShort("请在学什么栏目填写您要学的内容!");
            return false;
        }
        String checkResult = CacheMemoryUtils.getInstance().get(Contact.GROUP_SELECTED_PARAM);
        if (ObjectUtils.isEmpty(checkResult)) {
            ToastUtils.showShort("请您选择要报名的小组!");
            return false;
        }
        return true;
    }
}
