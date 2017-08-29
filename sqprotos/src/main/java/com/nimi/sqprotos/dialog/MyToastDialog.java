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

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.listener.CallBackListener;


/**
 * Created by Administrator on 2016/11/9.
 */
public class MyToastDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private CallBackListener backListener;
    private TextView mytoast_dialog_cancel;
    private TextView mytoast_dialog_ok;
    private TextView mytoast_dialog_title;
    private TextView mytoast_dialog_content;
    private LinearLayout mytoast_dialog_linear;
    private Object obj;
    private int pos;
   private String title;
    private String content;
    int w,h;
    public MyToastDialog(Context context, CallBackListener backListener, Object obj) {
        this(context, backListener, obj, -1,"标题","内容");
    }

    public MyToastDialog(Context context, CallBackListener backListener, Object obj, int pos,String title,String content,int w,int h) {
        super(context, R.style.dataselectstyle);
        this.context = context;
        this.backListener = backListener;
        this.obj = obj;
        this.pos = pos;
        this.title=title;
        this.content=content;
        this.w=w;
        this.h=h;
    }
    public MyToastDialog(Context context, CallBackListener backListener, Object obj, int pos,String title,String content) {
        this(context,backListener,obj,pos,title,content,0,0);
    }

    public MyToastDialog(Context context, int theme) {
        super(context, theme);
    }

    public MyToastDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mytoast_dailog);
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
        mytoast_dialog_title.setText(title);
        mytoast_dialog_content.setText(content);
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
