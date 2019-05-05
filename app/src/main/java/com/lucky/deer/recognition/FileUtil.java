/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.lucky.deer.recognition;

import android.content.Context;

import com.dykj.requestcore.util.SystemUtil;

import java.io.File;

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "img_" + SystemUtil.getInstance().getCurrentTimeMillis() + ".jpg");
        return file;
    }

    public static File getBankFile(Context context) {
        File file = new File(context.getFilesDir(), "bankCard_" + SystemUtil.getInstance().getCurrentTimeMillis() + ".jpg");
        return file;
    }
}
