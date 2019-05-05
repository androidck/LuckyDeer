package com.lucky.deer.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

public  class ScrollSwipeRefreshLayout extends SwipeRefreshLayout {
    // 子布局 这里为webview
    private ViewGroup mChildViewGroup;

    public ScrollSwipeRefreshLayout(Context context) {
        super(context);
    }
    public ScrollSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ViewGroup getViewGroup() {
        return mChildViewGroup;
    }
    public void setViewGroup(ViewGroup viewGroup) {
        mChildViewGroup = viewGroup;
    }


    /**
     * @return Whether it is possible for the child view of this layout to
     *         scroll up. Override this if the child view is a custom view.
     */
    @Override
    public boolean canChildScrollUp() {
        if (null != mChildViewGroup) {
            if (mChildViewGroup.getScrollY() > 0) {
                return true;
            }
            return false;
        }
        return super.canChildScrollUp();
    }
}
