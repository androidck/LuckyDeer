package com.lucky.deer.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.dykj.requestcore.util.RequestUtils;
import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.common.adapter.MyFragmentPagerAdapter;
import com.lucky.deer.common.dialog.AdvertDialog;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.home.fragment.FindFragment;
import com.lucky.deer.home.fragment.MallFragment;
import com.lucky.deer.home.fragment.HomeNewFragment;
import com.lucky.deer.home.fragment.MineFragment;
import com.lucky.deer.home.fragment.PromotionFragment;
import com.lucky.deer.login.LoginActivity;
import com.lucky.deer.update.HProgressDialogUtils;
import com.lucky.deer.update.UpdateDialogFragment;
import com.lucky.deer.util.ExampleUtil;
import com.lucky.deer.util.UpdateAppHttpUtil;
import com.lucky.deer.weight.enums.PublicEnum;
import com.lucky.mapfeatures.positioning.MapLocationListener;
import com.lucky.mapfeatures.positioning.PositioningFeatures;
import com.lucky.model.request.version.VersionUpdataReq;
import com.lucky.model.util.DeviceInfoUtils;
import com.lucky.model.util.HawkUtil;
import com.lucky.model.util.HintUtil;
import com.lucky.sharingfunction.dialog.SharingFunctionDialog;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.vector.update_app.service.DownloadService;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPage)
    QMUIViewPager viewPager;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_find)
    RadioButton rbFind;
    @BindView(R.id.rb_extension)
    RadioButton rbExtension;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    /**
     * 定位信息
     */
    public static AMapLocation mAMapLocation;

    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;


    final static String INTENT_KEY = "update_dialog_values";
    @BindView(R.id.ly_main)
    RelativeLayout lyMain;

    private IUpdateDialogFragmentListener mUpdateDialogFragmentListener;

    public static boolean isForeground = false;

    public static boolean isSwitch;
    private IWXAPI api;


    @Override
    protected int initLayout() {
        return R.layout.main;
    }

    UpdateAppBean mUpdateApp;




    @Override
    protected void initView() {

      //  lyMain.setFitsSystemWindows(true);

        //禁止viewPage 滑动
        viewPager.setSwipeable(false);
        // HomeFragment homeFragment = new HomeFragment();
        HomeNewFragment homeFragment = new HomeNewFragment();
        MallFragment findFragment = new MallFragment();
        FindFragment promotionFragment = new FindFragment();
        MineFragment mineFragment = new MineFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(homeFragment);
        list.add(findFragment);
        list.add(promotionFragment);
        list.add(mineFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), list));
        boolean isLogin = HawkUtil.getInstance().isContains(HawkUtil.USER_INFO);
        if (isLogin) {
            viewPager.setOffscreenPageLimit(list.size());
        }
        /*控件页面监听*/
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isLogin == false) {
                    radioGroup.check(R.id.rb_home);
                } else {
                    switch (position) {
                        case 0:
                            radioGroup.check(R.id.rb_home);
                            break;
                        case 1:
                            radioGroup.check(R.id.rb_find);
                            break;
                        case 2:
                            radioGroup.check(R.id.rb_extension);
                            break;
                        case 3:
                            radioGroup.check(R.id.rb_my);
                            break;
                        default:
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (!TextUtils.isEmpty(getStringData())) {
            if (KeyConstant.MEMBER_CENTRE.equals(getStringData())) {
                radioGroup.check(R.id.rb_extension);
                viewPager.setCurrentItem(2, false);
            }
        } else if (isSwitch == true) {
            radioGroup.check(R.id.rb_home);
            viewPager.setCurrentItem(0, false);
        }
        getSign(mActivity);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        checkUpdate();
        //注册广播
        registerMessageReceiver();

        boolean islogin = HawkUtil.getInstance().isContains(HawkUtil.USER_INFO);
        if (islogin == false) {
            return;
        } else {
            getAdvert();
        }
        /*获取总行信息并判断是否保存总行信息*/
        boolean bankInfo = HawkUtil.getInstance().isContains(KeyConstant.BANK_INFO);
        if (!bankInfo) {
            /*获取总行信息*/
            mNetworkRequestInstance.getBankInfoVo()
                    .subscribe(listResponseData -> {
                        if (RequestUtils.isRequestSuccess(listResponseData)) {
                            HawkUtil.getInstance().saveData(KeyConstant.BANK_INFO, listResponseData.getData());
                        } else {
                            HintUtil.showErrorWithToast(mContext, listResponseData.getMsg());
                        }
                    });
        }
        if (!HawkUtil.getInstance().isContains(HawkUtil.REGISTRATION_ID) && !TextUtils.isEmpty(JPushInterface.getRegistrationID(mContext))) {
            /*保存推送id*/
            HawkUtil.getInstance().saveData(HawkUtil.REGISTRATION_ID, JPushInterface.getRegistrationID(mContext));
        }

        PositioningFeatures.getInstance(mActivity)
                .setScenesUsed(1001)
                .setOnceLocation(true)
                .create()
                .setLocationListener(new MapLocationListener() {
                    @Override
                    public void onLocationSuccess(AMapLocation amapLocation, int scenesUsed) {
                        if (amapLocation != null && !TextUtils.isEmpty(amapLocation.getCountry()) && "中国".equals(amapLocation.getCountry()) && scenesUsed == 1001) {
                            mAMapLocation = amapLocation;
                            HawkUtil.getInstance().saveData(KeyConstant.SAVE_LOCATION_INFORMATION, amapLocation);
//                            mWebview.loadUrl("javascript:addressnow(" + amapLocation.getCity() + ")");
                        }
                        HawkUtil.getInstance().getSaveData(KeyConstant.SAVE_LOCATION_INFORMATION);
                    }

                    @Override
                    public void onLocationFailure() {
                        PositioningFeatures.getInstance(mActivity).stopLocation();
                    }
                }).startLocation();
    }

    public static MessageCallback messageCallback = new MessageCallback() {
        @Override
        public void onMessage(int message) {
            if (message == 1) {
                isSwitch = true;
            } else {
                isSwitch = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public interface MessageCallback {
        public void onMessage(int message);
    }


    @OnClick({R.id.rb_home, R.id.rb_find, R.id.rb_extension, R.id.rb_my})
    public void onViewClicked(View view) {
        viewPager.getOffscreenPageLimit();
        if (getLoginStatus()) {
            switch (view.getId()) {
                case R.id.rb_home:
                    viewPager.setCurrentItem(0, false);
                    break;
                case R.id.rb_find:
                    viewPager.setCurrentItem(1, false);
                    break;
                case R.id.rb_extension:
                    viewPager.setCurrentItem(2, false);
                    break;
                case R.id.rb_my:
                    viewPager.setCurrentItem(3, false);
                    break;
                default:
            }
        } else {
            obtainLoginStatus((status, useType, isPhoneNumber, text) -> {
                radioGroup.check(R.id.rb_home);
                viewPager.setCurrentItem(0, false);
                switch (status) {
                    case OK:
                        jumpActivity(mActivity, LoginActivity.class);
                        break;
                    default:
                }
            });
        }
//        boolean isLogin = HawkUtil.getInstance().isContains(HawkUtil.USER_INFO);
//        if (isLogin == false) {
//            radioGroup.check(R.id.rb_home);
//            viewPager.setCurrentItem(0, false);
//
//            jumpActivity(mActivity, LoginActivity.class);
//        } else {
//            int state = ((UserInfo) HawkUtil.getInstance().getSaveData(HawkUtil.USER_INFO)).getRegisterState();
//            Bundle bundle = new Bundle();
//            rbHome.setChecked(true);
//            rbFind.setChecked(false);
//            rbExtension.setChecked(false);
//            rbMy.setChecked(false);
//            if (state == 1) {
//                //资料完善页面
//                new DataperfectiDialog(mContext, state, "身份信息完善", R.style.UpdateAppDialog).show();
//            } else if (state == 2) {
//                new DataperfectiDialog(mContext, state, "储蓄卡信息完善", R.style.UpdateAppDialog).show();
//            } else if (state == 3) {
//                new DataperfectiDialog(mContext, state, "手持信息完善", R.style.UpdateAppDialog).show();
//            } else {
//                switch (view.getId()) {
//                    case R.id.rb_home:
//                        viewPager.setCurrentItem(0, false);
//                        break;
//                    case R.id.rb_find:
//                        viewPager.setCurrentItem(1, false);
//                        break;
//                    case R.id.rb_extension:
//                        viewPager.setCurrentItem(2, false);
//                        break;
//                    case R.id.rb_my:
//                        viewPager.setCurrentItem(3, false);
//                        break;
//                    default:
//                }
//            }
//        }
    }


    //自动检查更新
    @SuppressLint("CheckResult")
    public void checkUpdate() {
        VersionUpdataReq updataReq = new VersionUpdataReq();
        updataReq.setVersionNumber(String.valueOf(DeviceInfoUtils.get().getVersionCode()));
        showLoadingDialog();
        //检查跟新接口
        mNetworkRequestInstance.checkTheUpdate(updataReq)
                .subscribe(responseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(responseData)) {
                        Logger.d("responseData==" + responseData.getData().toString());
                        if (responseData.getData() != null && responseData.getData().getVersionNumber() > DeviceInfoUtils.get().getVersionCode()) {
                            checkUpdate("yes", String.valueOf(responseData.getData().getVersionNumber()), responseData.getData().getVersionDescription(), true, responseData.getData().getVersionUrl());
                        }
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

    //获取广告弹窗
    public void getAdvert() {
        mNetworkRequestInstance.getAdvert()
                .subscribe(responseData -> {
                    dismissLoadingDialog();
                    if (RequestUtils.isRequestSuccess(responseData)) {
                        if (responseData.getData() == null || responseData.getData().equals("")) {
                            // HintUtil.showErrorWithToast(mActivity, "暂无数据");
                        } else {
                            if ("1".equals(responseData.getData().getDisplayFlag())) {
                                new AdvertDialog(mContext, responseData.getData().getLink(), R.style.UpdateAppDialog).show();
                            }
                        }

                    }
                });
    }

    /**
     * 文件下载方法
     *
     * @param url
     */
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

        UpdateAppManager.download(MainActivity.this, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                HProgressDialogUtils.showHorizontalProgressDialog(MainActivity.this, "下载进度", false);
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

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.lucky.deer.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
//                    HintUtil.showErrorWithToast(mContext, showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        if (isSwitch == true) {
            radioGroup.check(R.id.rb_home);
            viewPager.setCurrentItem(0, false);
            isSwitch = false;
        }
        /*判断是否是业务员*/
        if (getLoginStatus() && PublicEnum.getEnumIsSalesman(getUserInfo().getUserType(), "是否是业务员") &&
                getUserInfo().isStartOrdering()) {
            /*是业务员*/
            isOpenIntervalPositioning(mContext, true);
        }
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void getSign(Context ctx) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.lucky.deer", PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            MessageDigest md1 = MessageDigest.getInstance("MD5");
            md1.update(sign.toByteArray());
            byte[] digest = md1.digest();
            String res = toHexString(digest);
            Logger.e("apk md5 = " + res);
            MessageDigest md2 = MessageDigest.getInstance("SHA1");
            md2.update(sign.toByteArray());
            byte[] digest2 = md2.digest();
            String res2 = toHexString(digest2);
            Logger.e("apk SHA1 = " + res2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }

    private void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*分型享回掉*/
        SharingFunctionDialog.getInstance(mActivity).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跳转指定的fragment
     *
     * @param memberCentre 指定key
     */
    public void jumpFragment(String memberCentre) {
        switch (memberCentre) {
            case KeyConstant.MEMBER_CENTRE:
                radioGroup.check(R.id.rb_extension);
                viewPager.setCurrentItem(1, false);
                break;
            default:
        }
    }

}
