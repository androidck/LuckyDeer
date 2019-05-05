package com.lucky.model.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lucky.model.R;

import java.io.File;

import static com.lucky.model.util.FileUtil.deleteFolderFile;
import static com.lucky.model.util.FileUtil.getFolderSize;
import static com.lucky.model.util.FileUtil.getFormatSize;

/**
 * 图片加载，可以随时替换加载工具
 * <p/>
 * Created by LOVE on 2016/04/18.
 */
public class ImageLoadUtils {
    private static Context context;
    private static ImageView imageView;
    private static String url;
    private static Integer defaultImg;

    /**
     * 加载图片(默认不显示备用图片)
     *
     * @param context   上下文
     * @param imageView 组建
     * @param url       网页链接
     */
    public static void loadImage(Context context, ImageView imageView, String url) {
        loadImage(context, imageView, url, 0);
    }

    /**
     * 加载图片
     *
     * @param context    上下文
     * @param imageView  组建
     * @param url        网页链接
     * @param defaultImg 默认图片
     */
    public static void loadImage(Context context, ImageView imageView, String url, Integer defaultImg) {
        /*R.drawable.shape_blue_circle*/
        if (!TextUtils.isEmpty(url) || defaultImg > 0) {
            Glide
                    .with(context)
                    .load(TextUtils.isEmpty(url) ? defaultImg : url)
                    .asBitmap()
                    .dontAnimate()
                    .error(defaultImg)
                    .placeholder(defaultImg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imageView
     * @param uri
     */
    public static void loadImage(Context context, ImageView imageView, Uri uri) {
        if (null != uri) {
            Glide
                    .with(context)
                    .load(uri)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * 加载网络背景图片
     *
     * @param context 全局上下文
     * @param view    组建
     * @param uri     资源
     */
    public static <T extends View> void loadBackGroundImage(Context context, final T view, String uri) {
        loadBackGroundImage(context, view, 0, uri);
    }

    /**
     * 加载网络背景图片
     *
     * @param context 全局上下文
     * @param view    组建
     * @param uri     资源
     */
    public static <T extends View> void loadBackGroundImage(Context context, final T view, Integer defaultimg, String uri) {
        Glide.with(context)
                .load(TextUtils.isEmpty(uri) ? R.drawable.unadd_card_state : uri)
                .error(defaultimg == 0 ? R.drawable.unadd_card_state : defaultimg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        view.setBackgroundDrawable(resource);
                    }
                });
    }

    /**
     * 加载资源
     *
     * @param context
     * @param imageView
     * @param recId
     */
    public static void loadImage(Context context, ImageView imageView, Integer recId) {
        if (recId > 0) {
            Glide
                    .with(context)
                    .load(recId)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * 有遮罩的，比如原型的，圆角的
     *
     * @param context
     * @param imageView
     * @param url
     * @param bitmapTransformation
     */
    public static void loadImage(Context context, ImageView imageView, String url, BitmapTransformation bitmapTransformation) {
        Glide
                .with(context)
                .load(TextUtils.isEmpty(url) ? R.drawable.shape_blue_circle : url)
                .transform(bitmapTransformation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 有遮罩的，比如原型的，圆角的
     *
     * @param context
     * @param imageView
     * @param url
     * @param bitmapTransformation
     */
    public static void loadImage(Context context, ImageView imageView, String url, BitmapTransformation bitmapTransformation, RequestListener listener) {
        Glide
                .with(context)
                .load(TextUtils.isEmpty(url) ? R.drawable.shape_blue_circle : url)
                .transform(bitmapTransformation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(listener)
                .into(imageView);
    }

    /**
     * 有遮罩的，比如原型的，圆角的
     *
     * @param context
     * @param imageView
     * @param uri
     * @param bitmapTransformation
     */
    public static void loadImage(Context context, ImageView imageView, Uri uri, BitmapTransformation bitmapTransformation) {
        if (null != uri) {
            Glide
                    .with(context)
                    .load(uri)
                    .transform(bitmapTransformation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * 加载失败，图片加载
     *
     * @param context
     * @param imageView
     * @param defaultImg
     * @param url
     */
    public static void loadImage(Context context, ImageView imageView, Integer defaultImg, String url) {
        Glide
                .with(context)
                .load(TextUtils.isEmpty(url) ? defaultImg : url)
                /*.placeHolder() 用一个 drawable(resource) 引用，Glide 将会显示它作为一个占位符，直到你的实际图片准备好。*/
                .placeholder(defaultImg)
                .error(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 有遮罩的，比如原型的，圆角的
     *
     * @param context
     * @param imageView
     * @param defaultImg
     * @param url
     * @param bitmapTransformation
     */
    public static void loadImage(Context context, ImageView imageView, Integer defaultImg, String url, BitmapTransformation bitmapTransformation, RequestListener listener) {
        Glide
                .with(context)
                .load(TextUtils.isEmpty(url) ? defaultImg : url)
                .transform(bitmapTransformation)
                /*.placeHolder() 用一个 drawable(resource) 引用，Glide 将会显示它作为一个占位符，直到你的实际图片准备好。*/
                .placeholder(defaultImg)
                .error(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(listener)
                .into(imageView);
    }

    /**
     * 有遮罩的，比如原型的，圆角的
     *
     * @param context
     * @param imageView
     * @param defaultImg
     * @param url
     * @param bitmapTransformation
     */
    public static void loadImage(Context context, ImageView imageView, Integer defaultImg, String url, BitmapTransformation bitmapTransformation) {
        Glide
                .with(context)
                .load(TextUtils.isEmpty(url) ? defaultImg : url)
                .transform(bitmapTransformation)
                /*.placeHolder() 用一个 drawable(resource) 引用，Glide 将会显示它作为一个占位符，直到你的实际图片准备好。*/
                .placeholder(defaultImg)
                .error(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.override(600, 600)
//                    .crossFade()
                .into(imageView);
    }

    /**
     * 有遮罩的，比如原型的，圆角的
     *
     * @param context
     * @param imageView
     * @param resId
     * @param bitmapTransformation
     */
    public static void loadImage(Context context, ImageView imageView, Integer resId, BitmapTransformation bitmapTransformation) {
        if (resId > 0) {
            Glide
                    .with(context)
                    .load(resId)
                    .transform(bitmapTransformation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * @param context
     * @param imageView
     * @param srcID
     * @param isScroll
     */
    public static void loadGIF(Context context, ImageView imageView, int srcID, boolean isScroll) {
        if (srcID > 0) {
            DrawableRequestBuilder builder = Glide
                    .with(context)
                    .load(srcID)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            if (isScroll) {
                builder.into(new GlideDrawableImageViewTarget(imageView));
            } else {
                builder.into(new GlideDrawableImageViewTarget(imageView, 1));
            }

        }
    }


    /**
     * 获取视频缩略图
     *
     * @param videoPath 视频路径
     * @param width     图片宽度
     * @param height    图片高度
     * @param kind      eg:MediaStore.Video.Thumbnails.MICRO_KIND   MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return
     */

    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    // 加载本地图片
    public static void displayByLocal(Context mContext, String path, ImageView imageView) {
        Glide.with(mContext)
                .load("file://" + path)
                .into(imageView);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////glide cache /////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
        //清除banner的缓存
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 清除图片磁盘缓存
     */
    private void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    private void clearImageMemoryCache(Context context) {
        try {
            /*只能在主线程执行*/
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
