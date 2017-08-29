package com.nimi.sqprotos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.bean.ToastBean;
import com.nimi.sqprotos.toast.MyToast;
import com.nimi.sqprotos.toast.ToastCallBackLister;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author
 * @version 1.0
 * @date 2017/7/20
 */

public class MyDialogs implements View.OnClickListener {
    private TextView mydialogs_tv;

    private Context context;
    private ToastCallBackLister backLister;
    private LayoutInflater inflater;
    private Dialog dialog;
    private ToastBean bean;
    private MyToast.CancelLister mLister;

    public void setLister(MyToast.CancelLister lister) {
        mLister = lister;
    }

    public MyDialogs(Context context, ToastBean bean, ToastCallBackLister backLister) {
        this.context = context;
        this.bean = bean;
        this.backLister = backLister;
        inflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        dialog = new Dialog(context, R.style.EvaluateDialogStyle);
        View view = inflater.inflate(R.layout.mydialogs, null);
        mydialogs_tv = (TextView) view.findViewById(R.id.mydialogs_tv);
        mydialogs_tv.setText(bean.getTitle());
        dialog.setContentView(view);
        Window window=dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        int w= (int) context.getResources().getDimension(R.dimen.dimen_320px);
        params.width=w;
        params.y=w/2;
        window.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        window.setAttributes(params);
    }

    public void setCanceListener(DialogInterface.OnCancelListener listener) {
        dialog.setOnCancelListener(listener);
    }

    public void setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
    }
    
  
    public void show() {
        if (backLister != null) {
            backLister.beginClick(bean);
        }
        dialog.show();
        if (bean.getType().getIndexs() != 3) {
            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    dissmiss();
                    if (backLister != null) {
                        backLister.forwordclick(bean);
                    }
                }
            }, bean.getType().getTime());
        }
    }

    public void dissmiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (dialog.isShowing() && backLister != null) {
            mLister.cancel(bean);
        }
    }

    public interface CancelLister {
        public void cancel(ToastBean bean);
    }

    public boolean isShow() {
        if (dialog == null) {
            return false;
        }
        return dialog.isShowing();
    }
}
