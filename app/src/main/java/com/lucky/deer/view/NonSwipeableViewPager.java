package com.lucky.deer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.qmuiteam.qmui.widget.QMUIViewPager;

public class NonSwipeableViewPager extends QMUIViewPager {

    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
