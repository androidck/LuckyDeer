package com.lucky.deer.common.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lucky.deer.R;
import com.lucky.deer.mine.perfectuserInfo.AuthenticationActivity;
import com.lucky.deer.mine.perfectuserInfo.BankCardActivity;
import com.lucky.deer.mine.perfectuserInfo.HeldIdentityActivity;

/**
 * 资料完善Dialog
 */
public class DataperfectiDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String perfectTypeStr;
    private int state;

    public DataperfectiDialog(@NonNull Context context,int state,String perfectTypeStr,int theme) {
        super(context,theme);
        this.context=context;
        this.perfectTypeStr=perfectTypeStr;
        this.state=state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    //初始化控件
    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_date, null);
        setContentView(view);
        TextView perfectType=findViewById(R.id.tv_perfect_type);//完善信息类型
        Button btnPerfect=findViewById(R.id.btn_perfect);//按钮
        ImageButton btnClose=findViewById(R.id.btn_close);//关闭按钮

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        lp.height = (int) (displayMetrics.heightPixels * 0.8f);
        dialogWindow.setAttributes(lp);
        btnClose.setOnClickListener(this);
        btnPerfect.setOnClickListener(this);
        perfectType.setText(perfectTypeStr);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        Intent intent;
        switch (v.getId()){
            case R.id.btn_perfect:
                if (state==1){
                    dismiss();
                    bundle.putInt("registerState",state);
                    intent = new Intent(context, AuthenticationActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else if (state==2){
                    dismiss();
                    bundle.putInt("registerState",state);
                    intent = new Intent(context, HeldIdentityActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else if (state==3){
                    dismiss();
                    bundle.putInt("registerState",state);
                    intent = new Intent(context, BankCardActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }
}
