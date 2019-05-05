package com.lucky.deer.home.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lucky.deer.R;
import com.lucky.model.response.home.FindArticleEntity;
import com.lucky.model.util.GlideUtils;

/**
 * 首页发现适配器
 */
public class FindArticleAdapter extends BaseQuickAdapter<FindArticleEntity, BaseViewHolder> {

    public FindArticleAdapter() {
        super(R.layout.item_home_fragment_find);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindArticleEntity item) {
        /*是否显示查看更多*/
        helper.setGone(R.id.rl_head, helper.getAdapterPosition() == 1)
                /*设置文章标题*/
                .setText(R.id.tv_article_title, item.getTitile())
                /*设置日期和阅读量*/
                .setText(R.id.tv_reading, Html.fromHtml(String.format(mContext.getString(R.string.home_item_text_reading), item.getCreateDate(), item.getReadCount())))
                /*添加点击监听*/
                .addOnClickListener(R.id.tv_good_card_see_more);
        /*加载文章图片*/
        GlideUtils.loadImage(mContext, helper.getView(R.id.iv_article_img), item.getCoverPhoto());
    }
}
