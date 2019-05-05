package com.lucky.deer.common.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dykj.requestcore.util.RequestUtils;
import com.hjq.toast.ToastUtils;
import com.lucky.deer.R;
import com.lucky.deer.execute.RequestInternet;
import com.lucky.model.request.quickpay.AddCardReq;
import com.lucky.model.util.HintUtil;

/**
 * 昵称
 */
public class NickNameDialog extends Dialog implements View.OnClickListener{

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;

    private String nickName;

    private OnNickNameListenter onNickNameListenter;
    private EditText edNickName;

    protected RequestInternet mNetworkRequestInstance;

    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public NickNameDialog(Context context, String nickName, boolean isCancelable, OnNickNameListenter onNickNameListenter) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.nickName=nickName;
        this.onNickNameListenter=onNickNameListenter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_nickname);//这行一定要写在前面
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

    @SuppressLint("WrongViewCast")
    private void initView() {
        TextView tvEsc=findViewById(R.id.btn_esc);
        TextView tvOk=findViewById(R.id.btn_ok);
        tvEsc.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        edNickName=findViewById(R.id.ed_nickname);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_esc:
                dismiss();
                break;
            case R.id.btn_ok:
                updataNickname(nickName);

                break;
        }
    }

    @SuppressLint("CheckResult")
    private void updataNickname(String creditId) {
        if (TextUtils.isEmpty(edNickName.getText().toString().trim())){
            ToastUtils.show("昵称不能为空");
        }else {
            AddCardReq addCardReq = new AddCardReq();
            addCardReq.setCreditId(creditId);
            addCardReq.setCreditAlias(edNickName.getText().toString().trim());
            mNetworkRequestInstance.updataNickname(addCardReq)
                    .subscribe(returnData -> {
                        if (RequestUtils.isRequestSuccess(returnData)) {
                            ToastUtils.show("昵称修改成功");
                            onNickNameListenter.setNickName(edNickName.getText().toString().trim());
                            dismiss();
                        } else {
                            ToastUtils.show("昵称修改失败");
                        }
                    });
        }

    }






    public interface OnNickNameListenter{
        void setNickName(String str);
    }

    public void setOnNickNameListenter(OnNickNameListenter onNickNameListenter){
        this.onNickNameListenter=onNickNameListenter;
    }


}
