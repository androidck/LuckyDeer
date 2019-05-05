package com.lucky.deer.find.business.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lucky.deer.R;
import com.lucky.deer.find.business.view.NineGridView;
import com.lucky.deer.find.business.view.util.Utils;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class NineImageAdapter implements NineGridView.NineGridAdapter<String> {

    private List<String> mImageBeans;

    private Context mContext;

    private RequestOptions mRequestOptions;

    private DrawableTransitionOptions mDrawableTransitionOptions;

    /**
     * @param context                   上下文
     * @param requestOptions            请求选项
     * @param drawableTransitionOptions 可绘制的过渡选项
     * @param imageBeans                图片地址
     */
    public NineImageAdapter(Context context, RequestOptions requestOptions, DrawableTransitionOptions drawableTransitionOptions, List<String> imageBeans) {
        this.mContext = context;
        this.mDrawableTransitionOptions = drawableTransitionOptions;
        this.mImageBeans = imageBeans;
        int itemSize = (Utils.getScreenWidth() - 2 * Utils.dp2px(4) - Utils.dp2px(54)) / 3;
        this.mRequestOptions = requestOptions.override(itemSize, itemSize);
    }

    @Override
    public int getCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    @Override
    public String getItem(int position) {
        return mImageBeans == null ? null :
                position < mImageBeans.size() ? mImageBeans.get(position) : null;
    }

    @Override
    public View getView(int position, View itemView) {
        ImageView imageView;
        if (itemView == null) {
            imageView = new ImageView(mContext);
//            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.base_F2F2F2));
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_hint_d8d8d8));
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            imageView = (ImageView) itemView;
        }
        String url = mImageBeans.get(position);
        Glide.with(mContext).load(url).apply(mRequestOptions).transition(mDrawableTransitionOptions).into(imageView);
        return imageView;
    }

    @Override
    public List<String> getImageList() {
        return mImageBeans;
    }
}
