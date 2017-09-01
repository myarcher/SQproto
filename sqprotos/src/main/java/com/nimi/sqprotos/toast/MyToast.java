package com.nimi.sqprotos.toast;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.bean.ToastBean;

import java.util.Timer;
import java.util.TimerTask;


public class MyToast implements View.OnClickListener {
    private TextView mytoast_tv;
    ImageView mytoast_img;
    private ImageView mytoast_canel;
    private ProgressBar mytoast_pro;
    private int defalut;
    private Context context;
    private ToastCallBackLister backLister;
    private LayoutInflater inflater;
    private Dialog dialog;
    private ToastBean bean;
    int[] imgs;
    private CancelLister mLister;

    public void setLister(CancelLister lister) {
        mLister = lister;
    }

    public MyToast(Context context, ToastBean bean, ToastCallBackLister backLister) {
       // imgs = new int[]{R.drawable.ok, R.drawable.cenel, R.drawable.noti};
        imgs = new int[]{R.drawable.rights, R.drawable.ic_prompt_close, R.drawable.ic_prompt_info};
        this.bean = bean;
        if (bean.getType().getIndexs() != 3) {
            this.defalut = imgs[bean.getType().getIndexs()];
        }
        this.context = context;
        this.backLister = backLister;
        inflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        dialog = new Dialog(context, R.style.EvaluateDialogStyle);
        View view = inflater.inflate(R.layout.mytoast, null);
        mytoast_img = (ImageView) view.findViewById(R.id.mytoast_img);
        mytoast_tv = (TextView) view.findViewById(R.id.mytoast_tv);
        mytoast_pro = (ProgressBar) view.findViewById(R.id.mytoast_pro);
        mytoast_canel = (ImageView) view.findViewById(R.id.mytoast_canel);
        mytoast_canel.setOnClickListener(this);

        if (bean.getType().getIndexs() != 3) {
            mytoast_pro.setVisibility(View.GONE);
            mytoast_img.setVisibility(View.VISIBLE);
            mytoast_canel.setVisibility(View.GONE);
            mytoast_img.setImageResource(defalut);
            mytoast_tv.setText(bean.getTitle());
        } else {
            mytoast_tv.setText(bean.getTitle());
            mytoast_canel.setVisibility(View.GONE);
            mytoast_img.setVisibility(View.GONE);
            mytoast_pro.setVisibility(View.VISIBLE);
        }
        dialog.setContentView(view);
    }

    public void setCanceListener(DialogInterface.OnCancelListener listener) {
        dialog.setOnCancelListener(listener);
    }

    public void setCancelable(boolean cancel) {
        mytoast_canel.setClickable(cancel);
        mytoast_canel.setFocusable(cancel);
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
        return dialog.isShowing();
    }
}
