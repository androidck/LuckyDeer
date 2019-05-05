package com.demo.cloudstorage.qiniuyun.adapter;

import android.content.Context;

import com.demo.cloudstorage.R;
import com.orhanobut.logger.Logger;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.utils.UrlSafeBase64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetKeyGeneratorAdapter implements KeyGenerator {

    private final Context mContext;
    private final boolean mDebug;
    private final String mPath;

    public GetKeyGeneratorAdapter(Context context, boolean debug, String path) {
        this.mContext = context;
        this.mDebug = debug;
        this.mPath = path;
    }


    @Override
    public String gen(String key, File file) {
        /*该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名*/
        String path = key + "_._" + new StringBuffer(file.getAbsolutePath());
        if (mDebug) {
            Logger.w(mContext.getString(R.string.file_name) + path);
        }
        File f = new File(mPath, UrlSafeBase64.encodeToString(path));
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(f));
            String tempString = null;
            int line = 1;
            try {
                while ((tempString = reader.readLine()) != null) {
                    line++;
                    if (mDebug) {
                        Logger.w(mContext.getString(R.string.temporary_file_name) + line + ":" + tempString);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return path;

    }
}
