package com.demo.cloudstorage.qiniuyun;

import android.content.Context;

import com.demo.cloudstorage.CloudstorageKeyConstant;
import com.demo.cloudstorage.R;
import com.demo.cloudstorage.qiniuyun.adapter.GetKeyGeneratorAdapter;
import com.orhanobut.logger.Logger;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.android.utils.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 七牛云存储执行器
 *
 * @author wangxiangyi
 * @date 2018/09/05
 */
public class InitFileExecutor {
    private static InitFileExecutor instance;
    private static Context mContext;
    private static String mAk, mSk;
    private static boolean mDebug;

    public static void onCreate(Context context, String ak, String sk, Boolean debug) {
        mContext = context;
        mAk = ak;
        mSk = sk;
        mDebug = debug;
    }

    public static InitFileExecutor getInstance() {
        if (instance == null) {
            synchronized (InitFileExecutor.class) {
                if (instance == null) {
                    instance = new InitFileExecutor();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化请求方式
     *
     * @param prefix 前缀名称
     * @param suffix 后缀名称
     */
    public  UploadManager initUploadManager(String prefix, String suffix) {
        Configuration.Builder config = new Configuration.Builder()
                // recorder 分片上传时，已上传片记录器
                // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .recorder(getFileRecorder(onCreateTempFile(CloudstorageKeyConstant.SPARE_PATH, prefix, suffix)),
                        new GetKeyGeneratorAdapter(mContext, mDebug, onCreateTempFile(CloudstorageKeyConstant.SPARE_PATH, prefix, suffix))
                );
        // 实例化一个上传的实例
        return new UploadManager(config.build());
    }

    /**
     * 创建临时文件
     *
     * @param path   备用路径
     * @param prefix 文件前缀名
     * @param suffix 文件后缀名
     * @return
     */
    private static String onCreateTempFile(String path, String prefix, String suffix) {
        if (StringUtils.isBlank(suffix)) {
            return mContext.getString(R.string.input_Suffix_name);
        }
        try {
            /*创建临时文件*/
            File f = File.createTempFile(
                    StringUtils.isBlank(prefix) ? "qiniu" : prefix,
                    StringUtils.isBlank(suffix) ? ".png" : suffix
            );
            if (mDebug) {
                Logger.w(mContext.getString(R.string.file_absolute_path) + f.getAbsolutePath());
            }
            /*获取文件路径*/
            path = f.getParent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 获取文件记录器
     *
     * @param dirPath 文件地址
     * @return 返回记录器
     */
    private static FileRecorder getFileRecorder(String dirPath) {
        try {
            return new FileRecorder(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getAk() {
        return mAk;
    }

    public static String getSk() {
        return mSk;
    }

    public static boolean isDebug() {
        return mDebug;
    }

}
