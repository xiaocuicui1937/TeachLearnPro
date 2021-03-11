package com.gnss.teachlearnpro.group;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.common.dataconvert.DataConvert;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.GroupStudyBean;

import java.util.ArrayList;
import java.util.List;

public class GroupStudyDataConvert extends DataConvert {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        GroupStudyBean item = GsonUtils.fromJson(mJsonData, GroupStudyBean.class);
        if (item.isSuccess()) {
            List<GroupStudyBean.DataBean> datas = item.getData();
            for (GroupStudyBean.DataBean param : datas) {
                //param.getStatus() == 2
                addLivePlanItem(param.isCover(), param.getId(),item.getCount(), param.getPassword(),param.getTitle(), param.getTime(), param.getTime_start_ming() + "-" +
                        param.getTime_end_ming(), "Zoom:" + param.getRoom_id() +
                        ",密码:" + param.getPassword(), param.getTeacher_name(),param.getWechat_img());
            }
        } else {
            ToastUtils.showShort(item.getMsg());
        }
//        addLivePlanItem(true,"清晨良言","每周一二三四五","6:00AM - 7:00AM","Zoom：1234567890， 密码：2020","讲师组");
//        addLivePlanItem(false,"祂路主日","每周一二三四五","6:00AM - 7:00AM","Zoom：1234567890， 密码：2020","讲师组");
        return ENTITYS;
    }

    private void addLivePlanItem(boolean isCover, int id, int count,int pwd,String... lives) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(Contact.TITLE, lives[0])
                .setField(Contact.PlAN_DATE, lives[1])
                .setField(Contact.PlAN_TIME, lives[2])
                .setField(Contact.PlAN_LOCATION, lives[3])
                .setField(Contact.PlAN_GROUP, lives[4])
                .setField(Contact.BLUR, isCover)
                .setField(Contact.ID, id)
                .setField(Contact.COUNT_LIST,count)
                .setField(Contact.LOGO_URL,lives[5])
                .setField(Contact.PWD,pwd)
                .build();
        ENTITYS.add(entity);
    }
}
