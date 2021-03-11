package com.gnss.teachlearnpro.profile.detail;

import androidx.fragment.app.Fragment;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.profile.detail.about.AboutFragment;
import com.gnss.teachlearnpro.profile.detail.favorite.FavoriteFragment;
import com.gnss.teachlearnpro.profile.detail.leavemsg.LeaveMsgFragment;
import com.gnss.teachlearnpro.profile.detail.profileinfo.ProfileInfoFragment;
import com.gnss.teachlearnpro.profile.detail.record.RecordFragment;
import com.gnss.teachlearnpro.profile.detail.setting.SettingFragment;
import com.gnss.teachlearnpro.profile.detail.share.ShareAppFragment;

public class ProfileDetailLogic extends BaseLogic {
    private ActivityProvider mProvider;

    public ProfileDetailLogic(ActivityProvider provider, int type) {
        this.mProvider = provider;
        Fragment fragment = getFragment(type);
        loadFragment(provider, getFragment(type), R.id.root_profile_detail, fragment.getClass().getName());
    }


    private Fragment getFragment(int type) {
        switch (type) {
            case Contact.PROFILE_INFO_TYPE:
                return ProfileInfoFragment.newInstance();
            case Contact.PROFILE_FAVORITE_TYPE:
                return FavoriteFragment.newInstance();
            case Contact.PROFILE_LIVE_RECORD_TYPE:
                return RecordFragment.newInstance(Contact.RECORD_LIVE);
            case Contact.PROFILE_COURSE_RECORD_TYPE:
                return RecordFragment.newInstance(Contact.RECORD_COURSE);
            case Contact.PROFILE_SETTING_TYPE:
                return SettingFragment.newInstance();
            case Contact.PROFILE_SHARE_APP_TYPE:
                return ShareAppFragment.newInstance();
            case Contact.PROFILE_ABOUT_ME_TYPE:
                return AboutFragment.newInstance();
            case Contact.PROFILE_LEAVE_MSG_TYPE:
                return LeaveMsgFragment.newInstance();
            default:
                break;
        }
        return null;
    }
}
