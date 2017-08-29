package com.nimi.sqprotos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.base.SActivity;
import com.nimi.sqprotos.listener.CallBackListener;
import com.nimi.sqprotos.toast.Types;
import com.nimi.sqprotos.view.ClearEditText;
import com.nimi.sqprotos.toast.MyToast;

public class ToastEDDialog extends Dialog implements View.OnClickListener {
    private TextView title;
    private TextView mess;
    private ClearEditText ed;
    private TextView ok_bt;
    private TextView canel_bt;
    private CallBackListener backListener;
    private String titles, messs;
    private SActivity activity;

    public ToastEDDialog(SActivity activity, String title, String mess,CallBackListener backListener) {
        super(activity, R.style.dataselectstyle);
        this.titles = title;
        this.messs = mess;
        this.activity = activity;
        this.backListener = backListener;
    }

    public ToastEDDialog(Context context, int theme) {
        super(context, theme);
    }

    public ToastEDDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.toast_dailog_eds);
        WindowManager m = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = m.getDefaultDisplay();
        LayoutParams params = getWindow().getAttributes();
        params.width = (int) (display.getWidth() * 0.75);
        params.height = LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        getWindow().setGravity(Gravity.CENTER);
        title = (TextView) findViewById(R.id.toast_dialog_title);
        mess = (TextView) findViewById(R.id.toast_dialog_mess);
        ok_bt = (TextView) findViewById(R.id.toast_dialog_ok);
        canel_bt = (TextView) findViewById(R.id.toast_dialog_canel);
        ed = (ClearEditText) findViewById(R.id.toast_dialog_ed);
        ok_bt.setOnClickListener(this);
        canel_bt.setOnClickListener(this);
        title.setText(titles);
        mess.setText(messs);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toast_dialog_ok) {
            String edstr = ed.getText().toString();
            if (TextUtils.isEmpty(edstr)) {
                activity.showMess("昵称不能为空",-1, Types.NOTI, null);
            } else {
                backListener.callBack(10,1, edstr, null);
            }
        }
        else if (v.getId() == R.id.toast_dialog_canel) {
            this.dismiss();
        }
    }
}
