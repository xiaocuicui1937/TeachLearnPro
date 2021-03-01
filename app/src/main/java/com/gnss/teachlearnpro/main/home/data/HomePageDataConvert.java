package com.gnss.teachlearnpro.main.home.data;

import androidx.annotation.DrawableRes;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.common.dataconvert.DataConvert;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.ArticleBean;
import com.gnss.teachlearnpro.common.bean.HomePageBean;
import com.gnss.teachlearnpro.common.bean.InfoListResBean;
import com.gnss.teachlearnpro.common.bean.StudentWitnessResBean;
import com.gnss.teachlearnpro.main.MainActivity;
import com.gnss.teachlearnpro.main.home.HomePageViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomePageDataConvert extends DataConvert {


    @Override
    public ArrayList<MultipleItemEntity> convert() {
        if (ObjectUtils.isEmpty(getJsonData())) {
            throw new RuntimeException("please call setJsonData first !");
        }
        HomePageBean homePage = GsonUtils.fromJson(getJsonData(), HomePageBean.class);
        HomePageBean.DataBean data = homePage.getData();
        //banner
        List<HomePageBean.DataBean.InformationBean> informations = data.getInformation();
        addBanner(informations);

        //坑位
        List<HomePageBean.DataBean.ClassBean> classX = data.getClassX();
        addClass(classX);
        //直播
        HomePageBean.DataBean.LiveBean live = data.getLive();
        addLive("01", 0, "直播", live.getTime_start(), "如何抓住青春的机遇？", "1234人-已预约-开启提醒", "查看全部", "");
        //最近在学
        List<HomePageBean.DataBean.StudyBean> studys = data.getStudy();
        //String.valueOf(param.getId()), 0, "最近在学",param.getLogo(),param.getTitle(), "查看全部"
        addRecent(studys);

        //热门课程
        List<HomePageBean.DataBean.CourseBean> course = data.getCourse();
        for (HomePageBean.DataBean.CourseBean param : course) {
            addHotCourse(String.valueOf(param.getId()), "热门课程", param.getLogo(), param.getTitle(), param.getPlatform());
        }
        //最新课程
        HomePageBean.DataBean.CourseNewBean course_new = data.getCourse_new();
        addNewCourse(String.valueOf(course_new.getId()), "最新课程", course_new.getLogo(), course_new.getTitle(), "");
        //定制推荐
        HomePageBean.DataBean.CourseBean made_new = data.getMade_new();
        addNewCourse(String.valueOf(course_new.getId()), "定制推荐", made_new.getLogo(), made_new.getTitle(), "");
        //话题文章
        MainActivity activity = (MainActivity) ActivityUtils.getTopActivity();
        addInfoList(activity);
        //学员见证
//        List<HomePageBean.DataBean.StudentBean> student = data.getStudent();
        addStudentWitnessList(activity);

//        for (HomePageBean.DataBean.StudentBean param : student) {
//            addStudentWitness(String.valueOf(param.getId()), "学员见证", param.getImg(), param.getTitle());
//        }


        return ENTITYS;
    }


    private void addInfoList(MainActivity activity) {
        HomePageViewModel model = new ViewModelProvider(activity).get(HomePageViewModel.class);
        model.getInfoList().observe(activity, infoListResBean -> {
            if (infoListResBean.isSuccess()) {
                model.obtainStudentWitnessList(1);
                addAirticle(infoListResBean.getData(), "话题文章");
            } else {
                ToastUtils.showShort(infoListResBean.getMsg());
            }
        });
    }

    private void addStudentWitnessList(MainActivity activity) {
        HomePageViewModel model = new ViewModelProvider(activity).get(HomePageViewModel.class);
        model.getStudentWitnessList().observe(activity, studentWitnessResBean -> {
            if (studentWitnessResBean.isSuccess()) {
                List<StudentWitnessResBean.DataBean> data = studentWitnessResBean.getData();
                for (int i = 0; i < data.size(); i++) {
                    addStudentWitness(String.valueOf(data.get(i).getId()), i == 0 ? "学员见证" : "", data.get(i).getImg(), data.get(i).getTitle());
                }
            } else {
                ToastUtils.showShort(studentWitnessResBean.getMsg());
            }

        });
    }

    private void addBanner(List<HomePageBean.DataBean.InformationBean> informations) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.BANNER_TYPE)
                .setField(Contact.BANNER_URL, informations)
                .build();
        ENTITYS.add(entity);
    }

    private void addClass(List<HomePageBean.DataBean.ClassBean> classX) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.GRID_TYPE)
                .setField(Contact.ARRAY, classX)
                .build();
        ENTITYS.add(entity);
    }

    private void addLive(String id, @DrawableRes int res, String... strs) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.LIVE_TYPE)
                .setField(Contact.ID, id)
                .setField(Contact.TITLE, strs[0])
                .setField(Contact.SUB_TITLE, strs[1])
                .setField(Contact.CONTENT_TITLE, strs[2])
                .setField(Contact.CONTENT_SUB_TITLE, strs[3])
                .setField(Contact.RIGHT_TITLE, strs[4])
                .setField(Contact.LOGO_URL, strs[5])
                .setField(Contact.RIGHT_TOP_DRAWABLE, res)
                .build();
        ENTITYS.add(entity);
    }

    private void addRecent(List<HomePageBean.DataBean.StudyBean> studys) {
        if (ObjectUtils.isNotEmpty(studys)) {
            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(ItemType.TYPE, ItemType.RECENT_GRID_TYPE)
                    .setField(Contact.ARRAY, studys)
                    .build();
            ENTITYS.add(entity);
        }
    }

    private void addHotCourse(String id, String... strs) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.HOT_COURSE_TYPE)
                .setField(Contact.ID, id)
                .setField(Contact.TITLE, strs[0])
                .setField(Contact.LOGO_URL, strs[1])
                .setField(Contact.CONTENT_TITLE, strs[2])
                .setField(Contact.CONTENT_SUB_TITLE, strs[3])
                .build();
        ENTITYS.add(entity);
    }

    private void addNewCourse(String id, String... strs) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.NEW_COURSE_TYPE)
                .setField(Contact.ID, id)
                .setField(Contact.TITLE, strs[0])
                .setField(Contact.LOGO_URL, strs[1])
                .setField(Contact.CONTENT_TITLE, strs[2])
                .setField(Contact.CONTENT_SUB_TITLE, strs[3])
                .build();
        ENTITYS.add(entity);
    }

    private void addAirticle(List<InfoListResBean.DataBean> infos, String title) {
        List<ArticleBean> arrays = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(infos)) {
            for (int i = 0; i < infos.size(); i++) {
                arrays.add(new ArticleBean(String.valueOf(i + 1), infos.get(i).getTitle()));
            }
            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(ItemType.TYPE, ItemType.ARTICLE_TYPE)
                    .setField(Contact.TITLE, title)
                    .setField(Contact.ARRAY, arrays)
                    .build();
            ENTITYS.add(entity);
        }

    }

    private void addStudentWitness(String id, String... strs) {
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .setField(ItemType.TYPE, ItemType.STUDENT_WITNESS_TYPE)
                .setField(Contact.ID, id)
                .setField(Contact.TITLE, strs[0])
                .setField(Contact.LOGO_URL, strs[1])
                .setField(Contact.CONTENT_TITLE, strs[2])
                .build();
        ENTITYS.add(entity);
    }
}
