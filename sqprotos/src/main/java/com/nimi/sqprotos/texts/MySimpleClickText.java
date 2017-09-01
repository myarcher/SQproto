package com.nimi.sqprotos.texts;


import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.nimi.sqprotos.listener.CallBackListener;


/**
 * Created by Administrator on 2016/8/9.
 */
public class MySimpleClickText extends ClickableSpan {
    private Context context;
    private int cor;
    private CallBackListener listener;
    private int po=-1;
    private Object obj;
    private boolean is;
    public MySimpleClickText(Context context, int cor, CallBackListener listener,int po,Object obj,boolean is) {
        super();
        this.context = context;
        this.cor=cor;
        this.po=po;
        this.listener=listener;
        this.obj=obj;
        this.is=is;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //设置文本的颜色
        ds.setColor(context.getResources().getColor(cor));
        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
        ds.setUnderlineText(is);
    }

    @Override
    public void onClick(View widget) {
      // Toast.makeText(context, "发生了点击效果", Toast.LENGTH_SHORT).show();
        if(listener!=null) {
            listener.callBack(po, 1, obj, null);
        }
    }
}
