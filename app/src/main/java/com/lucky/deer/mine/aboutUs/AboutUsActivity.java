package com.lucky.deer.mine.aboutUs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.update.HProgressDialogUtils;
import com.lucky.deer.update.UpdateDialogFragment;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.util.UpdateAppHttpUtil;
import com.lucky.model.request.version.VersionUpdataReq;
import com.lucky.model.util.DeviceInfoUtils;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.vector.update_app.service.DownloadService;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 *
 * @author wangxiangyi
 * @date 2018/11/1
 */
public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    @BindView(R.id.tv_app_name_version)
    TextView tvAppNameVersion;

    final static String INTENT_KEY = "update_dialog_values";
    final static String THEME_KEY = "theme_color";
    final static String TOP_IMAGE_KEY = "top_resId";

    UpdateAppBean mUpdateApp;

    private IUpdateDialogFragmentListener mUpdateDialogFragmentListener;

    boolean isForceUpdate = false;//默认不强制跟新

    @Override
    protected int initLayout() {
        return R.layout.activity_about_us;
    }


    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_about_us);
        tvAppNameVersion.setText(getString(R.string.app_name) + " v " + DeviceInfoUtils.get().getVersionName());
    }

    @OnClick({R.id.rl_feedback, R.id.rl_check_updates, R.id.rl_copy_public_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*意见反馈*/
            case R.id.rl_feedback:
                jumpActivity(mContext, FeedbackOpinionActivity.class);
                break;
            /*检查更新*/
            case R.id.rl_check_updates:
                examineRequiredVerification();
                break;
            /*复制微信公众号*/
            case R.id.rl_copy_public_number:
                if (StringUtil.copy(mContext, getString(R.string.wechat_public_number_content))) {
                    HintUtil.showErrorWithToast(mContext, "已复制到粘贴板");
                } else {
                    HintUtil.showErrorWithToast(mContext, "复制失败");
                }
                break;
            default:
        }
    }

    @Override
    protected boolean examineRequiredVerification() {

        return super.examineRequiredVerification();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void startRequestInterface() {
        VersionUpdataReq updataReq = new VersionUpdataReq();
        updataReq.setVersionNumber(String.valueOf(DeviceInfoUtils.get().getVersionCode()));
        showLoadingDialog();
        //检查跟新接口
        mNetworkRequestInstance.checkTheUpdate(updataReq)
                .subscribe(responseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(responseData)) {
                        if (responseData.getData() != null && responseData.getData().getVersionNumber() > DeviceInfoUtils.get().getVersionCode()) {
                            checkUpdate("yes", String.valueOf(responseData.getData().getVersionNumber()), responseData.getData().getVersionDescription(), true, responseData.getData().getVersionUrl());
                        }
                        Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, responseData.getMsg(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    /**
     * 检查跟新
     *
     * @param isUpdate    是否跟新
     * @param VersionName //版面号码
     * @param desc        //描述
     * @param constraint  //是否强制更新
     * @param updateUrl   //app 下载地址
     */
    private void checkUpdate(String isUpdate, String VersionName, String desc, boolean constraint, String updateUrl) {

        Bundle bundle = new Bundle();

        mUpdateApp = new UpdateAppBean();
        mUpdateApp.setUpdate(isUpdate);
        mUpdateApp.setNewVersion(VersionName);
        mUpdateApp.setTargetSize("25.42M");
        mUpdateApp.setUpdateLog(desc);
        mUpdateApp.setNewMd5("b97bea014531123f94c3ba7b7afbaad2");
        mUpdateApp.setConstraint(constraint);
        mUpdateApp.setApkFileUrl(updateUrl);
        bundle.putSerializable(INTENT_KEY, mUpdateApp);

        UpdateDialogFragment
                .newInstance(bundle)
                .setUpdateDialogFragmentListener(mUpdateDialogFragmentListener, clickDown)
                .show(((FragmentActivity) mActivity).getSupportFragmentManager(), "dialog");


    }

    UpdateDialogFragment.OnClickDown clickDown = new UpdateDialogFragment.OnClickDown() {
        @Override
        public void onClick() {
            onlyDownload(mUpdateApp.getApkFileUrl());
        }
    };

    public void getPermission() {


    }

    public void onlyDownload(String url) {
        UpdateAppBean updateAppBean = new UpdateAppBean();

        //设置 apk 的下载地址
        updateAppBean.setApkFileUrl(url);

        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
            try {
                path = getExternalCacheDir().getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        } else {
            path = getCacheDir().getAbsolutePath();
        }

        //设置apk 的保存路径
        updateAppBean.setTargetPath(path);
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new UpdateAppHttpUtil());

        UpdateAppManager.download(AboutUsActivity.this, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                HProgressDialogUtils.showHorizontalProgressDialog(AboutUsActivity.this, "下载进度", false);
            }

            @Override
            public void onProgress(float progress, long totalSize) {
                HProgressDialogUtils.setProgress(Math.round(progress * 100));

            }

            @Override
            public void setMax(long totalSize) {
            }

            @Override
            public boolean onFinish(File file) {
                HProgressDialogUtils.cancel();
                return true;
            }

            @Override
            public void onError(String msg) {
                HProgressDialogUtils.cancel();
            }

            @Override
            public boolean onInstallAppAndAppOnForeground(File file) {
                return false;
            }
        });
    }

}
