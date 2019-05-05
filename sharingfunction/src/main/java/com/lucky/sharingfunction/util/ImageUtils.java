package com.lucky.sharingfunction.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    private static File file;

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        return saveImageToGallery(context, bmp, "");
    }

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp, String fileName) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        file = new File(appDir, (TextUtils.isEmpty(fileName) ? System.currentTimeMillis() : fileName) + ".jpg");
        if (file.exists()) {
            appDir.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
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

    public static String getImagePath() {
        if (TextUtils.isEmpty(file.getAbsolutePath())) {
            return "";
        } else {
            return file.getAbsolutePath();
        }
    }
}
