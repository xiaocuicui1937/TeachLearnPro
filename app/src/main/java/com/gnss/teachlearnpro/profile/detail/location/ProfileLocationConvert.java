package com.gnss.teachlearnpro.profile.detail.location;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.common.dataconvert.DataConvert;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.LocationResBean;

import java.util.ArrayList;
import java.util.List;

public class ProfileLocationConvert extends DataConvert {


    @Override
    public ArrayList<MultipleItemEntity> convert() {
        LocationResBean res = GsonUtils.fromJson(mJsonData, LocationResBean.class);
        if (!res.isSuccess()) {
            ToastUtils.showShort(res.getMsg());
            return null;
        }
        List<LocationResBean.DataBean> datas = res.getData();
        if (ObjectUtils.isEmpty(datas)) {
            return null;
        }
        for (LocationResBean.DataBean param : datas) {
            addItemContent(param.getName(), "", String.valueOf(param.getId()));
        }
        return ENTITYS;
    }


    /**
     * 添加可以选中
     */
    private void addItemContent(String title, String content, String id) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.STV_TYPE)
                .setField(Contact.TITLE, title)
                .setField(Contact.CONTENT_TITLE, content)
                .setField(Contact.ID, id)
                .build();
        ENTITYS.add(entity);
    }
}
