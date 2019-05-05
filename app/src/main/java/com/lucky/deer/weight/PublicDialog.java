package com.lucky.deer.weight;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.lucky.deer.R;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.mine.OptionsWindowHelper;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.view.ContainsEmojiEditText;
import com.lucky.deer.weight.adapter.PublicListViewAdapter;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.deer.weight.interfaces.OnItemClickListener;
import com.lucky.model.common.ListPopupEntity;
import com.lucky.model.common.dialog.WheeInfoBean;
import com.lucky.model.response.userinfo.RefereeUserInfo;
import com.lucky.model.util.DisplayUtils;
import com.lucky.model.util.GlideUtils;
import com.lucky.model.util.HintUtil;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jeesoft.widget.pickerview.CharacterPickerView;

/**
 * 公共弹出框
 */
public class PublicDialog {
    private static PublicDialog instance;
    private static Context mContext;
    private QMUIBottomSheet build = null;

    private OnClickListener mListener;
    private OnItemClickListener mItemListener;

    public static PublicDialog getInstance() {
        if (instance == null) {
            synchronized (PublicDialog.class) {
                if (instance == null) {
                    instance = new PublicDialog();
                }
            }
        }
        return instance;
    }

    /**
     * 自定义View底部弹出框
     */
    public QMUIBottomSheet addHeaderView(View view) {
        return addHeaderView(mContext, view);
    }

    /**
     * 自定义View底部弹出框
     */
    public QMUIBottomSheet addHeaderView(Context context, View view) {
        mContext = context;
        if (build != null && build.isShowing()) {
            build.dismiss();
        }
        build = new QMUIBottomSheet
                .BottomListSheetBuilder(mContext)
                .addHeaderView(view)
                .build();
        return build;
    }

    /**
     * 自定义View底部弹出框
     */
    public QMUIBottomSheet addHeaderView2(View view) {
        return addHeaderView2(mContext, view);
    }

    /**
     * 自定义View底部弹出框2
     */
    public QMUIBottomSheet addHeaderView2(Context context, View view) {
        mContext = context;
        if (build != null && build.isShowing()) {
            build.dismiss();
        }
        build = new QMUIBottomSheet
                .BottomListSheetBuilder(mContext)
                .addHeaderView(view)
                .build();
        return build;
    }

    /**
     * 设置自定义使徒弹出框
     *
     * @param view 自定义View
     */
    public QMUIDialog setCustomizeView(View view) {
        return setCustomizeView(view, false, KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);
    }

    /**
     * 设置自定义中间弹出框
     *
     * @param view 自定义View
     */
    public QMUIDialog setCustomizeView(View view, float heigth) {
        return setCustomizeView(view, false, heigth);
    }

    /**
     * 设置自定义中间弹出框
     *
     * @param view   自定义View
     * @param heigth 设置占屏幕的百分比
     */
    public QMUIDialog setCustomizeView(View view, boolean isCancelable, float heigth) {
        QMUIDialog qmuiDialog = new QMUIDialog(mContext);
        qmuiDialog.setContentView(view);
        qmuiDialog.setCancelable(isCancelable);
        if (qmuiDialog.isShowing()) {
            qmuiDialog.dismiss();
        }
        qmuiDialog.show();
        Window window = qmuiDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        /*获取屏幕宽、高用*/
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        /* 宽度设置为屏幕的0.8*/
        lp.width = (int) (d.widthPixels / 1.2);
        /*高度设置为屏幕的0.6*/
        if (!isCancelable) {
            lp.height = (int) (d.heightPixels / heigth);
        }
        window.setAttributes(lp);
        return qmuiDialog;
    }

    /**
     * 设置悬浮窗弹出框
     *
     * @param context   上下文
     * @param direction 显示的方向
     * @param adapter   适配器
     * @param listener  点击监听
     */

    public QMUIListPopup setListPopup(Context context, int direction, BaseAdapter adapter, AdapterView.OnItemClickListener listener) {
        QMUIListPopup dialog = new QMUIListPopup(context, direction, adapter);
        /*获取屏幕宽、高*/
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        int width = d.widthPixels / 3;
        /*高度设置为屏幕的0.6*/
        int height = d.heightPixels / 5;
        dialog.create(
                width,
                height,
                listener);
        return dialog;
    }

    /**
     * 提示弹出框(默认点击空白处可以取消)
     *
     * @param context   上下文S
     * @param title     标题
     * @param messageId 提示
     * @param rightText 右边按钮
     */
    public void setMessageDialog(Context context, String title, int messageId, String rightText) {
        setMessageDialog(context, title, context.getString(messageId), null, rightText, true);
    }

    /**
     * 提示弹出框(默认点击空白处可以取消)
     *
     * @param context   上下文
     * @param title     标题
     * @param message   提示
     * @param rightText 右边按钮
     */
    public void setMessageDialog(Context context, String title, String message, String rightText) {
        setMessageDialog(context, title, message, null, rightText, true);
    }

    /**
     * 提示弹出框
     *
     * @param context      上下文
     * @param title        标题
     * @param message      提示
     * @param rightText    右边按钮
     * @param isCancelable 是否点击空白取消
     */
    public void setMessageDialog(Context context, String title, String message, String rightText, Boolean isCancelable) {
        setMessageDialog(context, title, message, null, rightText, isCancelable);
    }

    /**
     * 提示弹出框
     *
     * @param context      上下文
     * @param title        标题
     * @param message      提示
     * @param leftText     左边按钮
     * @param rightText    右边按钮
     * @param isCancelable 是否点击空白取消
     */
    public void setMessageDialog(Context context, String title, String message, String leftText, String rightText, Boolean isCancelable) {
        QMUIDialog.MessageDialogBuilder messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(isCancelable);
        if (!TextUtils.isEmpty(leftText)) {
            messageDialogBuilder.addAction(leftText, (dialog, index) -> dialog.dismiss());
        }
        if (!TextUtils.isEmpty(rightText)) {
            messageDialogBuilder.addAction(rightText, (dialog, index) -> dialog.dismiss());
        }
        messageDialogBuilder.show();
    }

    public BaseAdapter videoFeaturesAdapter(Context context, List<ListPopupEntity> data) {
        return new BaseAdapter() {

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LinearLayout.inflate(context, R.layout.item_pop_video_features, null);
                ImageView ivFunctionImg = view.findViewById(R.id.iv_function_img);
                TextView tvFunctionName = view.findViewById(R.id.tv_function_name);
                ivFunctionImg.setImageResource(data.get(position).getImg());
                tvFunctionName.setText(data.get(position).getText());
                return view;
            }
        };
    }


    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initEtOrTvView(Context context, String text, boolean isPhoneNumber) {
        return initEtOrTvView(context, text, null, isPhoneNumber);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initEtOrTvView(Context context, String text, String btnRight, boolean isPhoneNumber) {
        return initEtOrTvView(context, text, null, btnRight, isPhoneNumber);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initEtOrTvView(Context context, String text, String btnLeft, String btnRight, boolean isPhoneNumber) {
        return initEtOrTvView(context, text, 0, btnLeft, btnRight, isPhoneNumber);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param context     上下文
     * @param text        要显示的内容
     * @param textColorId 内容字体颜色
     * @param btnLeft     左边文字
     * @param btnRight    右边文字
     * @return 返回试图
     */
    public View initEtOrTvView(Context context, String text, int textColorId, String btnLeft, String btnRight, boolean isPhoneNumber) {
        return initEtOrTvView(context, null, 0, text, textColorId, btnLeft, btnRight, KeyConstant.NO_TYPE, isPhoneNumber, false);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param context     上下文
     * @param text        要显示的内容
     * @param textColorId 内容字体颜色
     * @param btnLeft     左边文字
     * @param btnRight    右边文字
     * @return 返回试图
     */
    public View initEtOrTvView(Context context, String text, int textColorId, String btnLeft, String btnRight, String useType, boolean isPhoneNumber) {
        return initEtOrTvView(context, null, 0, text, textColorId, btnLeft, btnRight, useType, isPhoneNumber, false);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initTitleEtOrTvView(Context context, String title, String text, boolean isPhoneNumber) {
        return initTitleEtOrTvView(context, title, text, KeyConstant.NO_TYPE, isPhoneNumber);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initTitleEtOrTvView(Context context, String title, String text, String useType, boolean isPhoneNumber) {
        return initTitleEtOrTvView(context, title, 0, text, 0, null, useType, isPhoneNumber);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initTitleEtOrTvView(Context context, String title, int titleColorId, String text, int textColorId, boolean isPhoneNumber) {
        return initTitleEtOrTvView(context, title, titleColorId, text, textColorId, null, isPhoneNumber);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initTitleEtOrTvView(Context context, String title, int titleColorId, String text, int textColorId, boolean isPhoneNumber, boolean isShowOneBtn) {
        return initEtOrTvView(context, title, titleColorId, text, textColorId, null, null, KeyConstant.NO_TYPE, isPhoneNumber, isShowOneBtn);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initTitleEtOrTvView(Context context, CharSequence title, int titleColorId, CharSequence text, int textColorId, String btnRight, boolean isPhoneNumber) {
        return initTitleEtOrTvView(context, title, titleColorId, text, textColorId, btnRight, KeyConstant.NO_TYPE, isPhoneNumber);
    }

    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
    public View initTitleEtOrTvView(Context context, CharSequence title, int titleColorId, CharSequence text, int textColorId, String btnRight, String useType, boolean isPhoneNumber) {
        return initEtOrTvView(context, title, titleColorId, text, textColorId, null, btnRight, useType, isPhoneNumber, false);
    }


    /**
     * 昵称和可拨电话的试图
     *
     * @param text 要显示的内容
     * @return 返回试图
     */
//    public View initEtOrTvView(Context context, String title, String text, String btnLeft, String btnRight, boolean isPhoneNumber) {
//    public View initEtOrTvView(Context context, CharSequence title, int titleColorID, CharSequence text, int textColorID, String btnLeft, String btnRight, boolean isPhoneNumber) {
    private View initEtOrTvView(Context context, CharSequence title, int titleColorID, CharSequence text, int textColorID, String btnLeft, String btnRight, String useType, boolean isPhoneNumber, boolean isShowOneBtn) {
        mContext = context;
        View view = LinearLayout.inflate(mContext, R.layout.dialog_public_view, null);
        TextView tvDialogTitle = view.findViewById(R.id.tv_dialog_title);
        LinearLayout llEdittextNickName = view.findViewById(R.id.ll_edittext_nick_name);
        EditText etNickName = view.findViewById(R.id.et_nick_name);
        LinearLayout llTwoButton = view.findViewById(R.id.ll_two_button);
        TextView tvText = view.findViewById(R.id.tv_text);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvOneButton = view.findViewById(R.id.tv_one_button);

        if (!TextUtils.isEmpty(btnLeft)) {
            tvCancel.setText(btnLeft);
        }
        TextView tvOk = view.findViewById(R.id.tv_ok);
        if (!TextUtils.isEmpty(btnRight)) {
            tvOk.setText(btnRight);
        }
        if (TextUtils.isEmpty(title)) {
            tvDialogTitle.setVisibility(View.GONE);
        } else {
            tvDialogTitle.setVisibility(View.VISIBLE);
            tvDialogTitle.setText(title);
            /*设置提示标题字体颜色*/
            if (titleColorID > 0) {
                tvDialogTitle.setTextColor(mContext.getResources().getColor(titleColorID));
            }
        }
        if (isPhoneNumber) {
            llEdittextNickName.setVisibility(View.GONE);
            tvText.setVisibility(View.VISIBLE);
        } else {
            llEdittextNickName.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.GONE);
        }
        if (isShowOneBtn) {
            tvOneButton.setVisibility(View.VISIBLE);
            llTwoButton.setVisibility(View.GONE);
        } else {
            tvOneButton.setVisibility(View.GONE);
            llTwoButton.setVisibility(View.VISIBLE);
        }

        etNickName.setHint(text);
        tvText.setText(text);
        /*设置提示内容字体颜色*/
        if (textColorID > 0) {
            tvText.setTextColor(mContext.getResources().getColor(textColorID));
        }
        tvCancel.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onClick(OnClickListener.ClickStatus.CANCEL, useType, isPhoneNumber, etNickName.getText().toString().trim());
            }
        });
        tvOk.setOnClickListener(v -> {
            if (mListener != null) {
                if (isPhoneNumber) {
                    mListener.onClick(OnClickListener.ClickStatus.OK, useType, isPhoneNumber, tvText.getText().toString().trim());
                } else {
                    if (TextUtils.isEmpty(etNickName.getText().toString().trim())) {
                        HintUtil.showErrorWithToast(mContext, R.string.toast_nick_name);
                    } else {
                        mListener.onClick(OnClickListener.ClickStatus.OK, useType, isPhoneNumber, etNickName.getText().toString().trim());
                    }
                }
            }
        });
        tvOneButton.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onClick(OnClickListener.ClickStatus.ONE_BUTTON, useType, isPhoneNumber, isPhoneNumber ? tvText.getText().toString().trim() : etNickName.getText().toString().trim());
            }
        });
        return view;
    }

    /**
     * 推荐人信息
     */
    public View referrerInfoView(Context context, RefereeUserInfo strings) {
        mContext = context;
        View view = LinearLayout.inflate(mContext, R.layout.dialog_referrer_view, null);
        QMUIRadiusImageView ivAvatar = view.findViewById(R.id.iv_avatar);
        TextView tvVip = view.findViewById(R.id.tv_vip);
        TextView tvNickname = view.findViewById(R.id.tv_nickname);
        TextView tvAccountNumber = view.findViewById(R.id.tv_account_number);
        RelativeLayout rlLeaveComments = view.findViewById(R.id.rl_leave_comments);
        ContainsEmojiEditText etLeaveComments = view.findViewById(R.id.et_leave_comments);
        TextView tvLeaveComments = view.findViewById(R.id.tv_leave_comments);
        GlideUtils.loadImage(context, ivAvatar, strings.getUserHead());
        tvVip.setText(strings.getLevelName());
        tvNickname.setText(strings.getNickName());
        if (!TextUtils.isEmpty(strings.isAllphone())) {
            tvAccountNumber.setText("2".equals(strings.isAllphone()) ?
                    StringUtil.replacePhoneNumber(strings.getPhone()) : strings.getPhone());
        }
        tvLeaveComments.setOnClickListener(v -> {
            if (mContext.getString(R.string.leave_comments)
                    .equals(tvLeaveComments.getText().toString().trim())) {
                tvLeaveComments.setText(R.string.determine);
                rlLeaveComments.setVisibility(View.VISIBLE);
            } else {
                if (TextUtils.isEmpty(etLeaveComments.getText().toString().trim())) {
                    HintUtil.showErrorWithToast(mContext, R.string.toast_leave_comments);
                } else {
                    if (isEmoji(etLeaveComments.getText().toString().trim())){
                        ToastUtils.show("不支持输入Emoji表情符号");
                    } else if (mListener != null) {
                        mListener.onClick(OnClickListener.ClickStatus.LEAVE_COMMENTS, KeyConstant.NO_TYPE, false, etLeaveComments.getText().toString().trim());
                    }
                }
            }
        });
        return view;
    }

    public boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    /**
     * 公共列表试图
     *
     * @return
     */
    public View publicListView(Context context, String[] list) {
        return publicListView(context, Arrays.asList(list));
    }

    /**
     * 公共列表试图
     *
     * @return
     */
    public View publicListView(Context context, List<String> list) {
        mContext = context;
        /*获取自定义视图*/
        View view = LinearLayout.inflate(mContext, R.layout.dialog_public_list_view, null);
        /*获取组建*/
        RecyclerView rvList = view.findViewById(R.id.rv_list);
        /*设置列表展示方向*/
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        PublicListViewAdapter publicListViewAdapter = new PublicListViewAdapter();
        rvList.setAdapter(publicListViewAdapter);
        publicListViewAdapter.setNewData(list);
        view.findViewById(R.id.tv_cancel)
                .setOnClickListener(v -> {
                    if (mItemListener != null) {
                        mItemListener.onItemClick(OnItemClickListener.ClickStatus.CANCEL, 0);
                    }
                });
        publicListViewAdapter.setOnItemClickListener((adapter, view1, position) -> {
            if (mItemListener != null) {
                mItemListener.onItemClick(OnItemClickListener.ClickStatus.OK, position);
            }
        });
        rvList.addItemDecoration(new DividerItemDecoration(rvList.getContext(), new LinearLayoutManager(mContext).getOrientation()));
        return view;
    }

    /**
     * 获取滚轮列表视图
     *
     * @param context   上下文
     * @param data      数据
     * @param menuLevel 滚轮等级
     * @return
     */
    public View getWheelList(Context context, List data, int menuLevel) {
        mContext = context;
        View wheelListView = LinearLayout.inflate(context, R.layout.dialog_account_opening_area, null);
        wheelListView.findViewById(R.id.tv_cancel)
                .setOnClickListener(v -> {
                    if (mListener != null) {
                        mListener.onClick(OnClickListener.ClickStatus.CANCEL, KeyConstant.NO_TYPE, false, null);
                    }
                });
        wheelListView.findViewById(R.id.tv_ok)
                .setOnClickListener(v -> {
                    if (mListener != null) {
                        WheeInfoBean bean = new WheeInfoBean();
                        switch (menuLevel) {
                            case 1:
                                bean.setTypd("1");
                                bean.setName(OptionsWindowHelper.getSelectBankpickerName());
                                bean.setCode(OptionsWindowHelper.getSelectBankpickerCode());
                                break;
                            case 3:
                                bean.setTypd("3");
                                bean.setName(OptionsWindowHelper.getNameAll());
                                bean.setCode(OptionsWindowHelper.getCode());
                                break;
                            default:
                        }
                        mListener.onClick(OnClickListener.ClickStatus.OK, KeyConstant.NO_TYPE, false, bean);
                    }
                });
        LinearLayout llView = wheelListView.findViewById(R.id.ll_view);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(mContext, 250));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        CharacterPickerView pickerView = new CharacterPickerView(context);
        pickerView.setMaxTextSize(13);
        llView.addView(pickerView, layoutParams);
        switch (menuLevel) {
            case 1:
                OptionsWindowHelper.setOneLevelLinkagePickerData(pickerView, data);
                break;
            case 3:
                OptionsWindowHelper.setThreeLevelLinkagePickerData(pickerView, data);
                break;
            default:
        }
        return wheelListView;
    }

    /**
     * 添加验证码弹出框样式
     *
     * @param context           上下文
     * @param name              左边名称
     * @param hint              输入框提示语
     * @param canNotIsEmptyHint 不能为空提示语
     * @param useType           类型
     * @return
     */
    public View initVerificationCodeView(Context context, String name, String hint, String canNotIsEmptyHint,int inputType, String useType) {
        return initVerificationCodeView(context, null, name, hint, canNotIsEmptyHint,inputType, useType);
    }

    public View initVerificationCodeView(Context context, String title, String name, String useType) {
        return initVerificationCodeView(context, title, name, null, null, 0,useType);
    }

    /**
     * 添加验证码弹出框样式
     *
     * @param context           上下文
     * @param title             标题
     * @param name              左边名称
     * @param hint              输入框提示语
     * @param canNotIsEmptyHint 不能为空提示语
     * @param useType           类型
     * @return
     */
    public View initVerificationCodeView(Context context, String title, String name, String hint, String canNotIsEmptyHint,int inputType, String useType) {
        mContext = context;
        View verificationCodeView = LinearLayout.inflate(context, R.layout.dialog_verification_code, null);
        /*获取标题控件*/
        TextView tvTitle = verificationCodeView.findViewById(R.id.tv_title);
        /*获取名称控件*/
        TextView tvName = verificationCodeView.findViewById(R.id.tv_name);

        TextView etCode = verificationCodeView.findViewById(R.id.et_code);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(name)) {
            /*设置名称*/
            tvName.setText(name);
        }
        if (!TextUtils.isEmpty(hint)) {
            /*设置提示语*/
            etCode.setHint(hint);
        }
        if (inputType>0) {
            /*设置输入类型*/
            etCode.setInputType(inputType);
        }else {
            /*设置输入类型*/
            etCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        verificationCodeView.findViewById(R.id.tv_cancel)
                .setOnClickListener(v -> {
                    if (mListener != null) {
                        mListener.onClick(OnClickListener.ClickStatus.CANCEL, useType, false, null);
                    }
                });
        verificationCodeView.findViewById(R.id.tv_ok)
                .setOnClickListener(v -> {
                    if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
                        HintUtil.showErrorWithToast(mContext, TextUtils.isEmpty(canNotIsEmptyHint) ? "请输入验证码" : canNotIsEmptyHint);
                        return;
                    }
                    if (mListener != null) {
                        mListener.onClick(OnClickListener.ClickStatus.OK, useType, false, etCode.getText().toString().trim());
                    }
                });
        return verificationCodeView;
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }
}
