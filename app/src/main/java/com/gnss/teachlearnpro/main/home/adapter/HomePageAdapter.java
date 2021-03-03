package com.gnss.teachlearnpro.main.home.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.HomePageBean;
import com.gnss.teachlearnpro.course.activity.CourseActivity;
import com.gnss.teachlearnpro.main.MainActivity;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomePageAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, BaseViewHolder> implements OnItemClickListener {

    public HomePageAdapter(@Nullable List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.BANNER_TYPE, R.layout.item_banner);
        addItemType(ItemType.GRID_TYPE, R.layout.item_grid_layout);
        addItemType(ItemType.LIVE_TYPE, R.layout.item_live);
        addItemType(ItemType.RECENT_GRID_TYPE, R.layout.item_recent_study);
        addItemType(ItemType.HOT_COURSE_TYPE, R.layout.item_hot_course);
        //定制推荐和最新课程公用一个类型
        addItemType(ItemType.NEW_COURSE_TYPE, R.layout.item_new_course);
        addItemType(ItemType.ARTICLE_TYPE, R.layout.item_airticel_info);
        addItemType(ItemType.STUDENT_WITNESS_TYPE, R.layout.item_student_witness);
        addChildClickViewIds(R.id.tv_item_recent_study_look_all, R.id.tv_item_airticle_info_look_all,
                R.id.iv_item_recent_course, R.id.iv_item_live_recent_study_live);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        switch (itemViewType) {
            case ItemType.BANNER_TYPE:
                createBanner(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.GRID_TYPE:
                createClasses(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.LIVE_TYPE:
                createLive(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.RECENT_GRID_TYPE:
                createRecentStudy(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.HOT_COURSE_TYPE:
                createHotCourse(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.NEW_COURSE_TYPE:
                createNewCourse(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.ARTICLE_TYPE:
                createArticle(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.STUDENT_WITNESS_TYPE:
                createStudentWitness(baseViewHolder, multipleItemEntity);
                break;
            default:
                break;

        }
    }

    private HomeInfoAdapter homeInfoAdapter;

    private void createArticle(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        RecyclerView rv = baseViewHolder.getView(R.id.rv_item_airticle_info);

        if (homeInfoAdapter == null) {
            homeInfoAdapter = new HomeInfoAdapter(R.layout.item_article_item_info, multipleItemEntity.getField(Contact.ARRAY));
        }
        //LinearLayoutManager 不能复用每次只能new出来 复用LinearLayoutManager is already attached to a RecyclerView 错误
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(homeInfoAdapter);
    }

    private void createStudentWitness(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        ImageView iv = baseViewHolder.getView(R.id.iv_item_student_witness);
        TextView tvTitle = baseViewHolder.getView(R.id.tv_item_student_witness);
        TextView tvContent = baseViewHolder.getView(R.id.tv_item_student_witness_content);
        String logoUrl = multipleItemEntity.getField(Contact.LOGO_URL);
        Glide.with(iv.getContext()).load(logoUrl).placeholder(R.mipmap.ic_launcher_round).into(iv);
        String title = multipleItemEntity.getField(Contact.TITLE);
        String contentTitle = multipleItemEntity.getField(Contact.CONTENT_TITLE);
        tvTitle.setText(title);
        tvContent.setText(contentTitle);
    }

    private void createNewCourse(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        ImageView ivNew = baseViewHolder.getView(R.id.iv_item_new_course_course);
        TextView tvContentTitle = baseViewHolder.getView(R.id.tv_item_new_course_content);
        TextView tvContent = baseViewHolder.getView(R.id.tv_item_new_course_other);
        TextView tvTitle = baseViewHolder.getView(R.id.tv_item_new_course);
        String title = multipleItemEntity.getField(Contact.TITLE);
        String contentTitle = multipleItemEntity.getField(Contact.CONTENT_TITLE);
        String content = multipleItemEntity.getField(Contact.CONTENT_SUB_TITLE);
        String logoUrlSub = (String) multipleItemEntity.getField(Contact.LOGO_URL);
        String logoUrl = Contact.BASE_PIC_URL + logoUrlSub;

        if (ObjectUtils.isNotEmpty(logoUrlSub)) {
            Glide.with(ivNew.getContext()).load(logoUrl).placeholder(R.drawable.test_new_course).into(ivNew);
        }
        tvContentTitle.setText(contentTitle);
        tvContent.setText(content);
        tvTitle.setText(title);
    }


    private void createHotCourse(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        ImageView ivLogo = baseViewHolder.getView(R.id.iv_item_hot_course);
        TextView tvContentTitle = baseViewHolder.getView(R.id.tv_item_hot_course_content);
        TextView tvContentOther = baseViewHolder.getView(R.id.tv_item_hot_course_other);

        String logoUrl = Contact.BASE_URL.substring(0, Contact.BASE_URL.length() - 5) + multipleItemEntity.getField(Contact.LOGO_URL);
        MeLog.e("createHotCourse:" + logoUrl);
        String contentTitle = multipleItemEntity.getField(Contact.CONTENT_TITLE);
        String contentSubTitle = multipleItemEntity.getField(Contact.CONTENT_SUB_TITLE);
        Glide.with(ivLogo.getContext()).load(logoUrl).into(ivLogo);
        tvContentTitle.setText(contentTitle);
        tvContentOther.setText(contentSubTitle);
    }


    private void createRecentStudy(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        ImageView ivCourse = baseViewHolder.getView(R.id.iv_item_recent_course);
        ImageView ivLive = baseViewHolder.getView(R.id.iv_item_live_recent_study_live);
        TextView tvCourse = baseViewHolder.getView(R.id.tv_item_recent_study_course);
        TextView tvLive = baseViewHolder.getView(R.id.tv_item_recent_study_live);
        List<HomePageBean.DataBean.StudyBean> studys = multipleItemEntity.getField(Contact.ARRAY);
        if (ObjectUtils.isNotEmpty(studys)) {
            HomePageBean.DataBean.StudyBean studyBean = studys.get(0);
            Glide.with(ivCourse.getContext()).load(studyBean.getLogo()).into(ivCourse);
            tvCourse.setText(studyBean.getTitle());

            HomePageBean.DataBean.StudyBean studyBeanLive = studys.get(1);
            Glide.with(ivCourse.getContext()).load(studyBeanLive.getLogo()).into(ivLive);
            tvLive.setText(studyBeanLive.getTitle());
        }
    }


    private void createLive(BaseViewHolder baseViewHolder, MultipleItemEntity item) {
        TextView title = baseViewHolder.getView(R.id.tv_item_live_content_title);
        title.setText(item.getField(Contact.CONTENT_TITLE));
        TextView liveStartTime = baseViewHolder.getView(R.id.tv_item_live_time);
        liveStartTime.setText(item.getField(Contact.SUB_TITLE));
        ImageView ivLogo = baseViewHolder.getView(R.id.iv_item_live);
        Glide.with(ivLogo.getContext()).load(Contact.BASE_PIC_URL + item.getField(Contact.LOGO_URL)).placeholder(R.drawable.test_live).into(ivLogo);
        TextView makeNumTv = baseViewHolder.getView(R.id.tv_item_live_count);
        String content = item.getField(Contact.CONTENT_SUB_TITLE);
        makeNumTv.setText(content.split("-")[0]);
    }


    private void createClasses(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        RecyclerView rv = (RecyclerView) baseViewHolder.itemView;

        List<HomePageBean.DataBean.ClassBean> classes = multipleItemEntity.getField(Contact.ARRAY);
        if (rv.getLayoutManager() == null) {
            rv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        if (rv.getAdapter() == null) {
            HomeClassesAdapter adapter = new HomeClassesAdapter(R.layout.item_grid, classes);
            adapter.setOnItemClickListener(this);
            rv.setAdapter(adapter);
        }

    }

    private void createBanner(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        Banner banner = baseViewHolder.getView(R.id.banner);
        MainActivity activity = (MainActivity) banner.getContext();
        banner.setAdapter(new BannerImageAdapter<HomePageBean.DataBean.InformationBean>(multipleItemEntity.getField(Contact.BANNER_URL)) {


            @Override
            public void onBindView(BannerImageHolder holder, HomePageBean.DataBean.InformationBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.getBanner())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(activity)//添加生命周期观察者
                .setIndicator(new CircleIndicator(activity));
        ;
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        HomePageBean.DataBean.ClassBean classBean = (HomePageBean.DataBean.ClassBean) adapter.getData().get(position);
        CacheMemoryUtils.getInstance().put(Contact.ID, classBean.getId());
        CacheMemoryUtils.getInstance().put(Contact.TITLE, classBean.getName());
        ActivityUtils.startActivity(CourseActivity.class);
    }
}
