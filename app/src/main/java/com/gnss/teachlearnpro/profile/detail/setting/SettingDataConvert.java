package com.gnss.teachlearnpro.profile.detail.setting;

import com.ecommerce.common.dataconvert.DataConvert;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;

import java.util.ArrayList;

public class SettingDataConvert extends DataConvert {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        addSwitchItem("系列学习提醒",true);
        addSwitchItem("直播通知提醒",false);
        return ENTITYS;
    }

    private void addSwitchItem(String name,boolean isOpen){
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(Contact.TITLE,name)
                .setField(Contact.SWITCH,isOpen)
                .build();
        ENTITYS.add(entity);
    }
}
