package com.gnss.teachlearnpro.live.model;

import com.ecommerce.common.dataconvert.DataConvert;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;

import java.util.ArrayList;

public class LiveBackDataConvert extends DataConvert {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        addImageItem("");
        addLivePlanItem("清晨良言","每周一二三四五","6:00AM - 7:00AM","Zoom：1234567890， 密码：2020","讲师组");
        addLivePlanItem("祂路主日","每周一二三四五","6:00AM - 7:00AM","Zoom：1234567890， 密码：2020","讲师组");
        return ENTITYS;
    }

    private void addImageItem(String url) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.IMAGE_TYPE)
                .setField(Contact.PlAN_URL, url)
                .build();
        ENTITYS.add(entity);
    }

    private void addLivePlanItem(String... lives) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.LIVE_PLAN_TYPE)
                .setField(Contact.TITLE, lives[0])
                .setField(Contact.PlAN_DATE, lives[1])
                .setField(Contact.PlAN_TIME, lives[2])
                .setField(Contact.PlAN_LOCATION, lives[3])
                .setField(Contact.PlAN_GROUP, lives[4])
                .build();
        ENTITYS.add(entity);
    }
}
