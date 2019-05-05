package com.lucky.deer.update;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lucky.deer.R;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.vector.update_app.service.DownloadService;
import com.vector.update_app.view.NumberProgressBar;

public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TIPS = "请授权访问存储空间权限，否则App无法更新";
    public static boolean isShow = false;
    private TextView mContentTextView;
    private Button mUpdateOkButton;
    private UpdateAppBean mUpdateApp;
    private NumberProgressBar mNumberProgressBar;
    private Button mIvClose;
    private TextView mTitleTextView;

    final static String INTENT_KEY = "update_dialog_values";
    final static String THEME_KEY = "theme_color";
    final static String TOP_IMAGE_KEY = "top_resId";
    private final static String UPDATE_APP_KEY = "UPDATE_APP_KEY";

    private OnClickDown onClickDown;

    /**
     * 回调
     */

    private View ivLine;
    //默认色
    private int mDefaultColor = 0xffe94339;
    private int mDefaultPicResId = R.mipmap.lib_update_app_top_bg;
    private TextView mIgnore;
    private IUpdateDialogFragmentListener mUpdateDialogFragmentListener;
    private DownloadService.DownloadBinder mDownloadBinder;
    private Activity mActivity;

    public UpdateDialogFragment setUpdateDialogFragmentListener(IUpdateDialogFragmentListener updateDialogFragmentListener,OnClickDown onClickDown) {
        this.mUpdateDialogFragmentListener = updateDialogFragmentListener;
        this.onClickDown=onClickDown;
        return this;
    }


    public static UpdateDialogFragment newInstance(Bundle args) {
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShow = true;
//        setStyle(DialogFragment.STYLE_NO_TITLE | DialogFragment.STYLE_NO_FRAME, 0);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.UpdateAppDialog);
        mActivity = getActivity();



    }

    @Override
    public void onStart() {
        super.onStart();
        //点击window外的区域 是否消失
        getDialog().setCanceledOnTouchOutside(false);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //禁用
                    if (mUpdateApp != null && mUpdateApp.isConstraint()) {
                        //返回桌面
                        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        Window dialogWindow = getDialog().getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        lp.height = (int) (displayMetrics.heightPixels * 0.8f);
        dialogWindow.setAttributes(lp);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lib_update_app_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        //提示内容
        mContentTextView = view.findViewById(R.id.tv_update_info);
        //标题
        mTitleTextView = view.findViewById(R.id.tv_title);
        //更新按钮
        mUpdateOkButton = view.findViewById(R.id.btn_ok);
        //进度条
        mNumberProgressBar = view.findViewById(R.id.npb);
        //关闭按钮
        mIvClose = view.findViewById(R.id.iv_close);
        //关闭按钮+线 的整个布局

      /*  //顶部图片
        mTopIv = view.findViewById(R.id.iv_top);*/
        //忽略
        mIgnore = view.findViewById(R.id.tv_ignore);

        ivLine=view.findViewById(R.id.iv_line);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mUpdateApp = (UpdateAppBean) getArguments().getSerializable(UpdateDialogFragment.INTENT_KEY);
        //设置主题色
        initTheme();

        if (mUpdateApp != null) {
            //弹出对话框
            final String dialogTitle = mUpdateApp.getUpdateDefDialogTitle();
            final String newVersion = mUpdateApp.getNewVersion();
            final String targetSize = mUpdateApp.getTargetSize();
            final String updateLog = mUpdateApp.getUpdateLog();

            String msg = "";

            if (!TextUtils.isEmpty(targetSize)) {
                msg = "新版本大小：" + targetSize + "\n\n";
            }

            if (!TextUtils.isEmpty(updateLog)) {
                msg += updateLog;
            }
            //更新内容
            mContentTextView.setText(msg);
            //标题
            mTitleTextView.setText("V"+newVersion);
            //强制更新
            if (mUpdateApp.isConstraint()) {
                mIvClose.setVisibility(View.GONE);
                ivLine.setVisibility(View.GONE);

            } else {
                //不是强制更新时，才生效
                if (mUpdateApp.isShowIgnoreVersion()) {
                    mIgnore.setVisibility(View.VISIBLE);
                }
            }

            initEvents();
        }
    }

    /**
     * 初始化主题色
     */
    private void initTheme() {


        final int color = getArguments().getInt(UpdateDialogFragment.THEME_KEY, -1);

        final int topResId = getArguments().getInt(UpdateDialogFragment.TOP_IMAGE_KEY, -1);


    /*    if (-1 == topResId) {
            if (-1 == color) {
                //默认红色
                setDialogTheme(mDefaultColor, mDefaultPicResId);
            } else {
                setDialogTheme(color, mDefaultPicResId);
            }

        } else {
            if (-1 == color) {
                //自动提色
//                Palette.from(AppUpdateUtils.drawableToBitmap(this.getResources().getDrawable(topResId))).generate(new Palette.PaletteAsyncListener() {
//                    @Override
//                    public void onGenerated(Palette palette) {
//                        int mDominantColor = palette.getDominantColor(mDefaultColor);
//                        setDialogTheme(mDominantColor, topResId);
//                    }
//                });
                setDialogTheme(mDefaultColor, topResId);
            } else {
                //更加指定的上色
                setDialogTheme(color, topResId);
            }
        }*/


    }

    /**
     * 设置
     *

     */
  /*  private void setDialogTheme(int color, int topResId) {
        mTopIv.setImageResource(topResId);
        mUpdateOkButton.setBackgroundDrawable(DrawableUtil.getDrawable(AppUpdateUtils.dip2px(4, getActivity()), color));
        mNumberProgressBar.setProgressTextColor(color);
        mNumberProgressBar.setReachedBarColor(color);
        //随背景颜色变化
        mUpdateOkButton.setTextColor(ColorUtil.isTextColorDark(color) ? Color.BLACK : Color.WHITE);
    }*/

    private void initEvents() {
        mUpdateOkButton.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mIgnore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                dismiss();
                onClickDown.onClick();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }



    public interface OnClickDown{
        void onClick();
    }

    public void setOnClickDown(OnClickDown onClickDown){
        this.onClickDown=onClickDown;
    }


}
