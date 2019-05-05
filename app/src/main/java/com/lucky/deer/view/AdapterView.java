package com.lucky.deer.view;

import android.content.res.Resources;
import android.util.TypedValue;

import com.lucky.deer.base.BaseRecyclerViewAdapter;

/**
 * 列表点击效果
 */
public class AdapterView {

    public static void itemClickEffect(BaseRecyclerViewAdapter.ViewHolder holder){
        if (holder.itemView.getBackground() == null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = holder.itemView.getContext().getTheme();
            int top = holder.itemView.getPaddingTop();
            int bottom = holder.itemView.getPaddingBottom();
            int left = holder.itemView.getPaddingLeft();
            int right = holder.itemView.getPaddingRight();
            if (theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                holder.itemView.setBackgroundResource(typedValue.resourceId);
            }
            holder.itemView.setPadding(left, top, right, bottom);
        }
    }
}
