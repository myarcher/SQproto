package com.nimi.sqprotos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.R;
import com.nimi.sqprotos.listener.CallBackListener;

/**
 * @author
 * @version 1.0
 * @date 2017/6/7
 */

public class UpdataDilog extends Dialog implements View.OnClickListener {
    private Context context;
    private CallBackListener backListener;
    private TextView mytoast_dialog_cancel;
    private TextView mytoast_dialog_ok;
    private TextView mytoast_dialog_title;
    private TextView mytoast_dialog_content;
    private LinearLayout mytoast_dialog_linear;
    private Object obj;
    private int pos;
    int w,h;
    public UpdataDilog(Context context, CallBackListener backListener, Object obj) {
        this(context, backListener, obj, -1);
    }

    public UpdataDilog(Context context, CallBackListener backListener, Object obj, int pos,int w,int h) {
        super(context, R.style.dataselectstyle);
        this.context = context;
        this.backListener = backListener;
        this.obj = obj;
        this.pos = pos;
        this.w=w;
        this.h=h;
    }
    public UpdataDilog(Context context, CallBackListener backListener, Object obj, int pos) {
        this(context,backListener,obj,pos,0,0);
    }

    public UpdataDilog(Context context, int theme) {
        super(context, theme);
    }

    public UpdataDilog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.updata_dialog);
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = m.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();

        if(w==0||h==0) {
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }else{
            params.width = w;
            params.height = h;
        }
        getWindow().setAttributes(params);
        getWindow().setGravity(Gravity.CENTER);
        mytoast_dialog_cancel = (TextView) findViewById(R.id.mytoast_dialog_cancel);
        mytoast_dialog_ok = (TextView) findViewById(R.id.mytoast_dialog_ok);
        mytoast_dialog_title = (TextView) findViewById(R.id.mytoast_dialog_title);
        mytoast_dialog_content = (TextView) findViewById(R.id.mytoast_dialog_content);
        mytoast_dialog_linear= (LinearLayout) findViewById(R.id.mytoast_dialog_linear);
      //  mytoast_dialog_title.setText(title);
       // mytoast_dialog_content.setText(content);
        mytoast_dialog_ok.setOnClickListener(this);
        mytoast_dialog_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mytoast_dialog_cancel) {
            this.dismiss();
        }
        else if (v.getId() == R.id.mytoast_dialog_ok) {
            backListener.callBack(pos,1,obj,null);
            this.dismiss();
        }
    }
}
