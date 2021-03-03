package com.gnss.teachlearnpro.profile.detail.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UiMessageUtils;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.LocationHelper;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.profile.detail.ProfileDetailActivity;
import com.gnss.teachlearnpro.profile.detail.location.next.ProfileNextLocationFragment;

import java.util.ArrayList;

public class ProfileLocationLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private ProfileLocationAdapter mAdapter;
    private ProfileLocationViewModel model;
    public ProfileLocationLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(provider, "选择地区", (AppCompatActivity) provider.getActivity());
        initRecyclerView();
        addCall();
        initLocation();
    }

    private void initLocation() {
        SuperTextView stvLocation = mProvider.getMineView().findViewById(R.id.stv_fragment_location_selected);
        stvLocation.setLeftString("正在定位中...");
        stvLocation.setEnabled(false);
        StringBuilder sb = new StringBuilder();
        new LocationHelper((location, locationCode) -> {
            stvLocation.setEnabled(true);
            sb.append(locationCode);
            stvLocation.setLeftString(location);
        });
        stvLocation.setOnClickListener(view -> {
            UiMessageUtils.getInstance().send(Contact.SELECTED_ADDRESS_LOCATION, stvLocation.getLeftString()+","+sb.toString());
            FragmentUtils.pop(mProvider.getParentFragmentManager());
            sb.delete(0, sb.toString().length());
        });
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_profile_location);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new ProfileLocationAdapter(R.layout.item_location, null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CacheMemoryUtils.getInstance().put(Contact.SELECTED_PROVINCE, mAdapter.getData().get(position));
            CacheMemoryUtils.getInstance().put(Contact.ID, mAdapter.getData().get(position).getField(Contact.ID));
            CacheMemoryUtils.getInstance().put(Contact.PROFILE_DETAIL_TYPE, ProfileLocationViewModel.ELocation.CITY);
            FragmentUtils.replace(FragmentUtils.getTopShow(mProvider.getParentFragmentManager())
                    , ProfileNextLocationFragment.newInstance(), true);
        });

    }

    private void addCall() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(ProfileLocationViewModel.class);
        model.obtainProvince();

        model.getLocation().observe(act, s -> {
            ArrayList<MultipleItemEntity> convert = new ProfileLocationConvert().setJsonData(s).convert();
            mAdapter.setNewInstance(convert);
        });

    }

}
