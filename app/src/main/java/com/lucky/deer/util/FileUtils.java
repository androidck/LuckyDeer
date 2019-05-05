package com.lucky.deer.util;

import android.content.Context;

import java.io.File;

/**
 * Created by Mark on 2017/11/21.
 */

public class FileUtils {

    public static File getFrontSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "front.jpg");
        return file;
    }

    public static File getBackSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "back.jpg");
        return file;
    }

    public static File getBankSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "bank.jpg");
        return file;
    }
}
