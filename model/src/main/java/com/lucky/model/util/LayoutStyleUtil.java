package com.lucky.model.util;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * 设置控件样式
 */
public class LayoutStyleUtil {
    /**
     * 设置布局圆角大小
     *
     * @param colorStr 16进制颜色值
     * @return
     */
    public static Drawable setShapeDrawable(String colorStr) {
        return setShapeDrawable(Color.parseColor(colorStr));
    }

    /**
     * 设置布局圆角大小
     *
     * @param colorStr 16进制颜色值
     * @return
     */
    public static Drawable setShapeDrawable(int colorStr) {
        /*设置圆角样式*/
        RoundRectShape rr = new RoundRectShape(new float[]{15, 15, 15, 15, 15, 15, 15, 15},
                null, null);
        /*创建Drawable样式*/
        ShapeDrawable drawable = new ShapeDrawable(rr);
        /*指定填充颜色*/
        drawable.getPaint().setColor(colorStr);
        /*指定填充模式*/
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }

}
