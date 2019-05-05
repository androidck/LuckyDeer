package com.lucky.deer.mine.message;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.base.BaseActivity;
import com.lucky.deer.configuration.KeyConstant;
import com.lucky.deer.util.PhoneUtils;
import com.lucky.deer.util.StringUtil;
import com.lucky.deer.weight.PublicDialog;
import com.lucky.deer.weight.interfaces.OnClickListener;
import com.lucky.model.response.mine.MessageBoardList;
import com.lucky.model.util.GlideUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 留言详情
 *
 * @author admin
 * @date 2018/12/3
 */
public class MessageDetailsActivity extends BaseActivity {
    /**
     * 标题
     */
    @BindView(R.id.top_bar)
    QMUITopBar topBar;
    /**
     * 头像
     */
    @BindView(R.id.iv_avatar)
    QMUIRadiusImageView ivAvatar;
    /**
     * 昵称
     */
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    /**
     * 时间
     */
    @BindView(R.id.tv_date)
    TextView tvDate;
    /**
     * 手机号
     */
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    /**
     * 留言内容
     */
    @BindView(R.id.tv_message_content)
    TextView tvMessageContent;
    /**
     * 是否查看
     */
    @BindView(R.id.cb_view_status)
    CheckBox cbViewStatus;
    private QMUIDialog qmuiDialog;
    private PublicDialog inistanceView;

    @Override
    protected int initLayout() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void initView() {
        initTopBar(topBar, R.string.title_activity_message_details);
        /*获取单利*/
        inistanceView = PublicDialog.getInstance();
    }

    @Override
    protected void initData() {
        if (getSerializableData() != null) {
            MessageBoardList data = (MessageBoardList) getSerializableData();
            GlideUtils.loadImage(mContext, ivAvatar, data.getUserHead());
            tvNickname.setText(data.getNickName());
            tvDate.setText(StringUtil.stampToDate(data.getUpdateDate(), StringUtil.ymdhms));
            tvPhone.setText(data.getPhone());
            tvMessageContent.setText(data.getLeaveMessageContent());
            String mTypeText = "";
            boolean mReadStatus = false;
            if (!TextUtils.isEmpty(data.getReadState())) {
                switch (data.getReadState()) {
                    /*未查看*/
                    case "0":
                        mTypeText = mContext.getString(R.string.not_viewed);
                        mReadStatus = false;
                        break;
                    /*已查看*/
                    case "1":
                        mTypeText = mContext.getString(R.string.viewed);
                        mReadStatus = true;
                        break;
                    /*已查看，静等回复*/
                    case "2":
                        mTypeText = mContext.getString(R.string.viewed_wait_reply);
                        mReadStatus = true;
                        break;
                    default:
                }
            }
            cbViewStatus.setChecked(mReadStatus);
            cbViewStatus.setText(mTypeText);
        }
    }

    @Override
    protected void initListener() {
        /*获取修改昵称监听*/
        inistanceView.setOnClickListener((OnClickListener<String>)(status,useType, isPhoneNumber, text) -> {
            switch (status) {
                case CANCEL:
                    qmuiDialog.dismiss();
                    break;
                case OK:
                    qmuiDialog.dismiss();
                    PhoneUtils.callPhoneDirectly(mActivity, text);
                    break;
                default:
            }
        });
    }

    @OnClick(R.id.tv_phone)
    public void onViewClicked() {
        /*初始化拨打客服电话弹出框*/
        qmuiDialog = inistanceView.setCustomizeView(
                inistanceView.initEtOrTvView(mActivity,
                        tvPhone.getText().toString().trim(),
                        "呼叫",
                        true),
                true,
                KeyConstant.CUSTOMIZE_DIALOG_SCREEN_HEIGHT_5);

    }
}
