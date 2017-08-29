package com.nimi.sqprotos.texts;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.listener.CallBackListener;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/13.
 */
public class TextBeans implements Serializable {
    private int po;
    private String str;
    private boolean isClick = false;
    private int cor;
    private CallBackListener listener;
    private Context context;
    private Object obj;
 
    public boolean is;

    public int getPo() {
        return po;
    }

    public void setPo(int po) {
        this.po = po;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public CallBackListener getListener() {
        return listener;
    }

    public void setListener(CallBackListener listener) {
        this.listener = listener;
    }

    public TextBeans() {
    }

    public TextBeans(Context context) {
        this.context = context;
    }

    public TextBeans(Context context, CallBackListener listener, String str) {
        this(context, listener, R.color.actionsheet_gray, false, str, -2, null,false);
    }

    public TextBeans(Context context, CallBackListener listener, String str, int po,boolean is) {
        this(context, listener, R.color.actionsheet_gray, false, str, po, null,is);
    }

    public TextBeans(Context context, CallBackListener listener, int cor, String str, int po, Object obj,boolean is) {
        this(context, listener, cor, false, str, po, obj,is);
    }

    public TextBeans(Context context, CallBackListener listener, int cor, boolean isClick, String str, int po, Object obj,boolean is) {
        this.listener = listener;
        this.cor = cor;
        this.isClick = isClick;
        this.str = str;
        this.po = po;
        this.context = context;
        this.obj = obj;
        this.is=is;
    }

    public SpannableString init() {
        SpannableString spans = null;
        if (!isClick) {
            spans = new SpannableString(Html.fromHtml(str));
        } else {
            spans = new SpannableString(str);
            spans.setSpan(new MySimpleClickText(context, cor, listener, po, obj,is), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spans;
    }

}
