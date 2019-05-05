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
import android.widget.ImageView;

import com.lucky.deer.R;
import com.lucky.deer.common.WebViewActivity;
import com.lucky.deer.configuration.HttpConstant;
import com.lucky.model.common.WebViewBean;

/**
 * 资料完善Dialog
 */
public class AdvertDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String link;

    protected String mEntity = HttpConstant.ENTITY;

    public AdvertDialog(@NonNull Context context,String link, int theme) {
        super(context,theme);
        this.context=context;
        this.link=link;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    //初始化控件
    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_user_date, null);
        setContentView(view);
        ImageView btnClose=findViewById(R.id.img);//点击图片跳转

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        lp.height = (int) (displayMetrics.heightPixels * 0.8f);
        dialogWindow.setAttributes(lp);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img:
                dismiss();
                WebViewBean webViewBean=new WebViewBean();
                webViewBean.setWebTitle("百度");
                webViewBean.setWebUrl(link);
                context.startActivity(new Intent(context, WebViewActivity.class).putExtra(mEntity, webViewBean));
            case R.id.btn_close:
                dismiss();
                break;
        }
    }
}
