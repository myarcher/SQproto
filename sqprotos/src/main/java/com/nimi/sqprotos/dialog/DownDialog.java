package com.nimi.sqprotos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nimi.sqprotos.R;

/**
 * Created by Administrator on 2016/12/21.
 */

public class DownDialog {
  /*  implements
} View.OnClickListener {
    private ImageView down_dialog_cencel;
    private TextView down_dialog_title;
    private TextView down_dialog_process;
    private ProgressBar down_dialog_progress;
    private Context context;
    int w, h;
    private Dialog dialog;
    private View view;

    protected void builder() {

        view = LayoutInflater.from(context).inflate(R.layout.down_dialog, null);
        init();
        dialog = new Dialog(context, R.style.dataselectstyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (w == 0 || h == 0) {
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        } else {
            lp.width = w;
            lp.height = h;
        }
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);

    }

    public DownDialog(Context context, int w, int h) {
        this.context = context;
        this.h = h;
        this.w = w;
        builder();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public void init() {
        down_dialog_process = (TextView) view.findViewById(R.id.down_dialog_process);
        down_dialog_title = (TextView) view.findViewById(R.id.down_dialog_title);
        down_dialog_progress = (ProgressBar) view.findViewById(R.id.down_dialog_progress);
        down_dialog_cencel = (ImageView) view.findViewById(R.id.down_dialog_cencel);
        setProgress(0);
        setMax(0);
        down_dialog_cencel.setOnClickListener(this);
    }


    public void setProgress(long current) {
        down_dialog_progress.setProgress((int) current);
    }

    public void setMax(long total) {
        down_dialog_progress.setMax((int) total);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.down_dialog_cencel) {

        }
    }

    public int getMax() {
        return  down_dialog_progress.getMax();
    }

    public void setSText(String formatStr) {
        down_dialog_process.setText(formatStr);
    }*/
}
