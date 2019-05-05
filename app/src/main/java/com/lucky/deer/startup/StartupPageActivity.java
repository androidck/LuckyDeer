package com.lucky.deer.startup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.find.business.redEnvelope.RedEnvelopeInfoActivity;
import com.lucky.deer.home.MainActivity;
import com.lucky.model.request.version.VersionUpdataReq;
import com.lucky.model.util.DeviceInfoUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.umengfunction.features.StatisticalFunction;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;


/**
 * 启动页
 *
 * @author wangxiangyi
 * @date 2018/09/18
 */
public class StartupPageActivity extends BaseActivity {
    @BindView(R.id.iv_startup_image)
    ImageView ivStartupImage;
    @BindView(R.id.tv_leapfrog)
    TextView tvLeapfrog;
    /**
     * 最新版本Code
     */
    private int newVersionCode = 1;
    private boolean leapfrog = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_startup_page;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        HawkUtil.getInstance().remove(KeyConstant.IS_ADVERTISING);
//        /*加载网络图片*/
//        ImageLoadUtils.loadImage(mContext, ivStartupImage, "http://img.zcool.cn/community/0125fd5770dfa50000018c1b486f15.jpg@1280w_1l_2o_100sh.jpg");
        // ivStartupImage.setBackgroundResource(R.mipmap.start);
        mPermissions.setPermissionsRequest(
                /*读取电话列表*/
                Manifest.permission.READ_PHONE_STATE
                /*读取通讯录列表*/
                , Manifest.permission.READ_CONTACTS
                /*定位*/
                , Manifest.permission.ACCESS_COARSE_LOCATION
                /*精细定位*/
                , Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(aBoolean -> {
                    if (!aBoolean) {
                        mPermissions.openSettingDetail();
                    } else {
                        Observable.timer(1, TimeUnit.SECONDS)
                                .subscribe(aLong -> {
//                                    checkTheUpdate();
//                                    /*判断是否查看广告*/
//                                    if (0 == HawkUtil.getInstance().getSaveData(KeyConstant.IS_ADVERTISING, 0)) {
//                                        if (DeviceInfoUtils.get().getVersionCode() == newVersionCode) {
//                                            if (leapfrog) {
//                                                jumpActivity(mActivity, MainActivity.class, true);
//                                            }
//                                        } else {
//                                            jumpActivity(mActivity, GuidePageActivity.class, true);
//                                        }
//                                    }

                                    if (getIntent() != null) {
                                        Uri uri = getIntent().getData();
                                        if (uri != null) {
                                            //获取指定参数值
                                            Bundle bundle = new Bundle();
                                            bundle.putString(RedEnvelopeInfoActivity.id, uri.getQueryParameter("id"));
                                            bundle.putString(RedEnvelopeInfoActivity.pageMarkKey, "1");
                                            jumpBundleActivity(mActivity, RedEnvelopeInfoActivity.class, bundle, true);
                                        } else {
                                            if (HawkUtil.getInstance().isContains(KeyConstant.VERSION_CODE)) {
                                                jumpActivity(mActivity, MainActivity.class, true);
                                            } else {
                                                jumpActivity(mActivity, GuidePageActivity.class, true);
                                            }
                                        }
                                    } else {
                                        if (HawkUtil.getInstance().isContains(KeyConstant.VERSION_CODE)) {
                                            jumpActivity(mActivity, MainActivity.class, true);
                                        } else {
                                            jumpActivity(mActivity, GuidePageActivity.class, true);
                                        }
                                    }
                                });
                    }
                });
        /*设置友盟统计功能*/
        StatisticalFunction
                .getInstance(mContext)
                .setScenarioType(StatisticalFunction.E_UM_NORMAL)
                .setSessionContinueMillis(3);
    }

    @OnClick({R.id.iv_startup_image, R.id.tv_leapfrog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_startup_image:
//                WebViewBean webViewBean = new WebViewBean();
//                webViewBean.setWebTitle("美女");
//                webViewBean.setWebUrl("https://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%C3%C0%C5%AE&fr=ala&ala=1&alatpl=adress&pos=0&hs=2&xthttps=111111");
//                jumpActivity(mActivity, WebViewActivity.class, webViewBean, true);
//                HawkUtil.getInstance().saveData(CloudstorageKeyConstant.IS_ADVERTISING, 1);
                break;
            case R.id.tv_leapfrog:
                leapfrog = false;
                jumpActivity(mActivity, MainActivity.class, true);
                break;
            default:
        }
    }

    @SuppressLint("CheckResult")
    public void checkTheUpdate() {
        VersionUpdataReq updataReq = new VersionUpdataReq();
        updataReq.setVersionNumber(String.valueOf(DeviceInfoUtils.get().getVersionCode()));
        mNetworkRequestInstance.checkTheUpdate(updataReq)
                .compose(bindToLifecycle())
                .subscribe(responseData -> {
                    if (RequestUtils.isRequestSuccess(responseData)) {
                        newVersionCode = responseData.getData().getVersionNumber();
                        /*判断是否查看广告*/
                        if (0 == HawkUtil.getInstance().getSaveData(KeyConstant.IS_ADVERTISING, 0)) {
                            if (DeviceInfoUtils.get().getVersionCode() == newVersionCode) {
                                if (leapfrog) {
                                    jumpActivity(mActivity, MainActivity.class, true);
                                }
                            } else {
                                jumpActivity(mActivity, GuidePageActivity.class, true);
                            }
                        }
                    } else {
                        jumpActivity(mActivity, MainActivity.class, true);
                    }
                });
    }
}
