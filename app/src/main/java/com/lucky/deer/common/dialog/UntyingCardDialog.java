package com.lucky.deer.common.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.hjq.toast.ToastUtils;
import com.lucky.deer.R;
import com.lucky.deer.execute.RequestInternet;
import com.lucky.model.request.quickpay.AddCardReq;

/**
 * 解绑信用卡dialog
 */
public class UntyingCardDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    private String bankId;
    private OnUntyingCardListener onUntyingCardListener;
    protected RequestInternet mNetworkRequestInstance;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public UntyingCardDialog(Context context, boolean isCancelable, String bankId, OnUntyingCardListener onUntyingCardListener) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.bankId=bankId;
        this.onUntyingCardListener=onUntyingCardListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_untrying_tip);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        mNetworkRequestInstance = RequestInternet.getInstance();
        initView();
    }

    private void initView() {
        TextView tvEsc=findViewById(R.id.tv_esc);
        TextView tvLogin=findViewById(R.id.tv_login);
        TextView tvLoginOut=findViewById(R.id.tv_tip);
        tvEsc.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvLogin.setText("解绑");
        tvLoginOut.setText("信用卡解绑后，将无法正常使用业务，确定要解绑吗？");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_esc:
                dismiss();
                break;
            case R.id.tv_login:
                untiedCreditCard(bankId);
                break;
        }
    }
    /**
     * 解绑信用卡接口
     *
     * @param creditId
     */
    @SuppressLint("CheckResult")
    private void untiedCreditCard(String creditId) {
        AddCardReq addCardReq = new AddCardReq();
        addCardReq.setCreditId(creditId);
        mNetworkRequestInstance.delCreditCard(addCardReq)
                .subscribe(returnData -> {
                    if (RequestUtils.isRequestSuccess(returnData)) {
                        onUntyingCardListener.onSuccess("解绑成功");
                        ToastUtils.show("解绑成功");
                        dismiss();
                    } else {
                        ToastUtils.show(returnData.getMsg());
                    }
                });
    }


    public interface OnUntyingCardListener{
        void onSuccess(String msg);
    }

}
