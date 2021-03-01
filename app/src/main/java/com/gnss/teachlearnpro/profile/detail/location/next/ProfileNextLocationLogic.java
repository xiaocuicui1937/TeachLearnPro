package com.gnss.teachlearnpro.profile.detail.location.next;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.UiMessageUtils;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.profile.detail.ProfileDetailActivity;
import com.gnss.teachlearnpro.profile.detail.location.ProfileLocationAdapter;
import com.gnss.teachlearnpro.profile.detail.location.ProfileLocationConvert;
import com.gnss.teachlearnpro.profile.detail.location.ProfileLocationFragment;
import com.gnss.teachlearnpro.profile.detail.location.ProfileLocationViewModel;

import java.util.ArrayList;

public class ProfileNextLocationLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private ProfileLocationAdapter mAdapter;

    public ProfileNextLocationLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(provider, "选择地区", (AppCompatActivity) provider.getActivity());
        initRecyclerView();
        addCall();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_profile_next_location);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new ProfileLocationAdapter(R.layout.item_location, null);
        rv.setAdapter(mAdapter);
    }

    private void addCall() {
        ProfileDetailActivity act = (ProfileDetailActivity) ActivityUtils.getTopActivity();
        ProfileLocationViewModel model = new ViewModelProvider(act).get(ProfileLocationViewModel.class);
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        model.obtainNextLocation(instance.get(Contact.PROFILE_DETAIL_TYPE), instance.get(Contact.ID));
        model.getNextLocation().observe(act, s -> {
            ArrayList<MultipleItemEntity> convert = new ProfileLocationConvert().setJsonData(s).convert();
            mAdapter.setNewInstance(convert);
        });
        CacheMemoryUtils locationCache = CacheMemoryUtils.getInstance();
        ProfileLocationViewModel.ELocation location = locationCache.get(Contact.PROFILE_DETAIL_TYPE);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CacheMemoryUtils.getInstance().put(Contact.ID, mAdapter.getData().get(position).getField(Contact.ID));
            if (location == ProfileLocationViewModel.ELocation.CITY) {
                CacheMemoryUtils.getInstance().put(Contact.SELECTED_CITY, mAdapter.getData().get(position));
                locationCache.put(Contact.PROFILE_DETAIL_TYPE, ProfileLocationViewModel.ELocation.AREA);
                toFragment(ProfileNextLocationFragment.newInstance());
            } else {
                UiMessageUtils.getInstance().send(Contact.SELECTED_ADDRESS);
                CacheMemoryUtils.getInstance().put(Contact.SELECTED_AREA, mAdapter.getData().get(position));
                FragmentUtils.popTo(mProvider.getParentFragmentManager(), ProfileLocationFragment.class, true, true);
            }

        });
    }

    private void toFragment(Fragment fragment) {
        FragmentUtils.replace(FragmentUtils.getTopShow(mProvider.getParentFragmentManager())
                , fragment, true);
    }
}
