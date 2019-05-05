package com.lucky.model.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理工具类
 *
 * @author wangxiangyi
 * @date 2018/11/05
 */
public class ImgUtils {
    /**
     * 选择图片
     */
    public static final String IMAGE = "image/*";
    /**
     * 选择音频
     */
    public static final String AUDIO = "audio/*";
    /**
     * 选择视频（mp4 3gp 是android支持的视频格式）
     */
    public static final String VIDEO = "video/*";


    private static ImgUtils instance;

    public static ImgUtils getInstance() {
        if (instance == null) {
            synchronized (ImgUtils.class) {
                if (instance == null) {
                    instance = new ImgUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 选择选择本地数据
     *
     * @param context 上下文
     * @param type    类型<p>
     *                IMAGE：图片<p>
     *                AUDIO：音频<p>
     *                VIDEO：视频
     * @return
     */
    public Intent selectLocalData(Context context, String... type) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        StringBuffer stringBuffer = new StringBuffer();
        if (type.length > 0) {
            for (String mType : type) {
                if (type.length == 1) {
                    stringBuffer.append(mType);
                } else {
                    stringBuffer.append(mType);
                    stringBuffer.append(";");
                }
            }
            /*设置选择条件*/
            intent.setType(stringBuffer.toString());
        } else {
            /*无类型限制*/
            intent.setType("*/*");
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    /**
     * 获取选中的图片地址
     *
     * @param context 上下文
     * @param data    数据
     * @return 地址
     */
    public String getImgPath(Context context, Intent data) {
        String path;
        Uri uri = data.getData();
        /* 使用第三方应用打开*/
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        /*4.4以后*/
        else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            path = getPath(context, uri);
        } else {
            /*4.4以下下系统调用方法*/
            path = getRealPathFromURI(context, uri);
        }
        return path;
    }

    /**
     * 保存文件到指定路径
     *
     * @param context 上下文
     * @param push    图片地址
     * @return
     */
    public boolean saveImageToGallery(Context context, final String push) {
        if (push.contains("http://")) {
//            return saveImageToGallery(context, decodeUriAsBitmapFromNet(push));
            return false;
        } else {
            return saveImageToGallery(context, decodeUriAsBitmap(context, Uri.parse(push)));
        }
    }

    /**
     * 保存文件到指定路径
     */
    public boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param uri：图片的本地url地址
     * @return Bitmap；
     */
    private Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    private String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(columnIndex);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        /* MediaStore (and general)*/
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        /**/
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
    /**
     * 生成视图的预览
     * @param activity
     * @param v
     * @return  视图生成失败返回null
     *          视图生成成功返回视图的绝对路径
     */
    public static String saveImage(Activity activity, View v) {
        Bitmap bitmap;
        String path =  Environment.getExternalStorageDirectory().getAbsolutePath()  + "preview.png";
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        try {
            bitmap = Bitmap.createBitmap(bitmap, location[0], location[1], v.getWidth(), v.getHeight());
            FileOutputStream fout = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Logger.e( "生成预览图片失败：" + e);
        } catch (IllegalArgumentException e) {
            Logger.e( "width is <= 0, or height is <= 0");
        } finally {
            // 清理缓存
            view.destroyDrawingCache();
        }
        return null;

    }
}
