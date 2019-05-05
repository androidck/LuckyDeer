package com.lucky.deer.mine.adapter;

import android.text.TextUtils;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.find.VideoListEntity;
import com.lucky.model.util.GlideUtils;

/**
 * 视频列表适配器
 *
 * @author wangxiangyi
 * @date 2018/11/20
 */
public class VideoListAdapter extends BaseQuickAdapter<VideoListEntity, BaseViewHolder> {
    private boolean mIsShow = false;
    /**
     * 默认显示标题和详情
     */
    private boolean mIsShowContent = true;

    public VideoListAdapter() {
        super(R.layout.item_video_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoListEntity item) {
        if (item.getAddCover() > 0) {
            GlideUtils.loadImage(mContext, helper.getView(R.id.iv_video_img), item.getAddCover());
        } else {
            GlideUtils.loadImage(mContext, helper.getView(R.id.iv_video_img), item.getCover());
        }
        helper.setGone(R.id.iv_video_start, item.getAddCover() <= 0)
                .setGone(R.id.cb_selection, mIsShow)
                .setChecked(R.id.cb_selection, item.isChecked())
                .setGone(R.id.ll_video_content, mIsShowContent)
                .setText(R.id.tv_video_title, item.getTitle())
                .setVisible(R.id.tv_video_title, !TextUtils.isEmpty(item.getTitle()))
                .setText(R.id.tv_video_introduction, item.getDesc())
                .setVisible(R.id.tv_video_introduction, !TextUtils.isEmpty(item.getTitle()))
                .addOnClickListener(R.id.cb_selection);
        CheckBox cbSelection = helper.getView(R.id.cb_selection);
        item.setChecked(cbSelection.isChecked());
        if (mIsShow) {
        } else {
            item.setChecked(mIsShow);
        }
    }

    /**
     * 是否显示多选
     *
     * @param isShow
     */
    public void isShowMultipleSelection(boolean isShow) {
        mIsShow = isShow;
    }

    /**
     * 是否显示标题和详情
     *
     * @param isShowContent
     */
    public void isShowContent(boolean isShowContent) {
        mIsShowContent = isShowContent;
    }
}
