package com.gnss.teachlearnpro.common.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.BaseResBean;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.w3c.dom.Comment;

public class CommentViewModel extends BaseViewModel {
    private MutableLiveData<CommentBean> mUtableCommentDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> mUtableCollect = new MutableLiveData<>();
    private MutableLiveData<Boolean> mUtableWriteComment = new MutableLiveData<>();
    private MutableLiveData<Boolean> mUtableAddRecording = new MutableLiveData<>();


    public void obtainCommentList(CommentType type, String id, int pageIndex, boolean lookAll) {
        EasyHttp.post(getUrl(type))
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params(getIdKey(type), id)
                .params("type", String.valueOf(lookAll ? 1 : 2))
                .params("page", String.valueOf(pageIndex))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, getErrorTip(type));
                    }

                    @Override
                    public void onSuccess(String s) {
                        CommentBean res = GsonUtils.fromJson(s, CommentBean.class);
                        mUtableCommentDetail.postValue(res);
                    }
                });
    }

    private String getUrl(CommentType type) {
        if (type == CommentType.LIVE) {
            return "Home/getLiveComment";
        } else if (type == CommentType.COURSE) {
            return "Course/getCourseComment";
        } else if (type == CommentType.GROUP) {
            return "Team/getTeamComment";
        }
        return "";
    }


    private String getIdKey(CommentType type) {
        if (type == CommentType.LIVE) {
            return "id";
        } else if (type == CommentType.COURSE) {
            return "id";
        } else if (type == CommentType.GROUP) {
            return "team_id";
        }
        return "";

    }


    private String getErrorTip(CommentType type) {
        if (type == CommentType.LIVE) {
            return "访问获取直播评论接口失败";
        } else if (type == CommentType.COURSE) {
            return "访问获取课程评论接口失败";
        } else if (type == CommentType.GROUP) {
            return "访问获取小组学习评论接口失败";
        }
        return "";
    }


    public enum CommentType {
        LIVE, GROUP, COURSE
    }

    public LiveData<CommentBean> getCommentList() {
        return mUtableCommentDetail;
    }

    private String getType(CommentType type) {
        if (type == CommentType.LIVE) {
            return "2";
        } else if (type == CommentType.COURSE) {
            return "1";
        } else if (type == CommentType.GROUP) {
            return "3";
        }
        return "";
    }


    public void setCollectStatus(CommentType type, String id) {
        EasyHttp.post("Collection/addCollection")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("common_id", id)
                .params("type", getType(type))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, "访问收藏接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        CommentBean res = GsonUtils.fromJson(s, CommentBean.class);
                        boolean isCollect = res.getCode() == 2;
                        mUtableCollect.postValue(isCollect);
                        ToastUtils.showShort(res.getMsg());
                    }
                });
    }

    public LiveData<Boolean> getCollect() {
        return mUtableCollect;
    }


    public void writeComment(CommentType type, String id, String content) {
        EasyHttp.post("Comment/addInfo")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("common_id", id)
                .params("type", getType(type))
                .params("content", content)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mUtableWriteComment.postValue(false);
                        tipError(e, "访问添加评论接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        BaseResBean res = GsonUtils.fromJson(s, BaseResBean.class);
                        mUtableWriteComment.postValue(res.isSuccess());
                        ToastUtils.showShort(res.getMsg());
                    }
                });
    }

    public LiveData<Boolean> getWriteComment() {
        return mUtableWriteComment;
    }

    /**
     * 点赞评论
     *
     * @param type
     * @param id
     */
    public void addRecording(CommentType type, String id) {
        EasyHttp.post("Recording/addRecording")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("common_id", id)
                .params("type", getType(type))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mUtableAddRecording.postValue(false);
                        tipError(e, "访问点赞评论接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        BaseResBean res = GsonUtils.fromJson(s, BaseResBean.class);
                        boolean recording = res.getMsg().contains("点赞");
                        mUtableAddRecording.postValue(recording);
                        ToastUtils.showShort(res.getMsg());
                    }
                });
    }

    public LiveData<Boolean> getAddRecording() {
        return mUtableAddRecording;
    }
}