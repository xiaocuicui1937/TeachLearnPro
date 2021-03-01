package com.gnss.teachlearnpro.live.liveplan.detail.model;

import com.ecommerce.common.dataconvert.DataConvert;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.LiveDetailResBean;

import java.util.ArrayList;

public class LiveInnerDetailDataConvert extends DataConvert {
    LiveDetailResBean mLiveDate;

    public LiveInnerDetailDataConvert(LiveDetailResBean liveData) {
        this.mLiveDate = liveData;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        LiveDetailResBean.DataBean data = mLiveDate.getData();
        addLivePlanItem(data.getTitle(), data.getDetails());
        addImageItem(data.getTitle_img(), "直播海报");

        return ENTITYS;
    }

    private void addLivePlanItem(String... lives) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.SIGNlE_TEXT_TYPE)
                .setField(Contact.TITLE, lives[0])
                .setField(Contact.CONTENT_TITLE, lives[1])
                .build();
        ENTITYS.add(entity);
    }

    private void addImageItem(String url, String title) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.IMAGE_TYPE)
                .setField(Contact.PlAN_URL, url)
                .setField(Contact.TITLE, title)
                .build();
        ENTITYS.add(entity);
    }


}
