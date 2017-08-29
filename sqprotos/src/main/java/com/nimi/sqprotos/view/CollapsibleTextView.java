package com.nimi.sqprotos.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nimi.sqprotos.R;

/**
 * @author
 * @version 1.0
 * @date 2017/5/25
 */

public class CollapsibleTextView extends LinearLayout {
    String tag = "MyTextView";
    TextView contentTextView;
    int initLines = 2;
    boolean PICK_UP = true;
    boolean SEE_ALL = false;
    TextView tipTextView;
    String contentText;
    Context context;
    int lineNum;
    OnClickListener contentTextViewOnClickListener;
    OnClickListener tipTextViewOnClickListener;
    boolean flag = SEE_ALL;

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.my_text_view, this);
        contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        tipTextView = (TextView) view.findViewById(R.id.tipTextView);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.my_text_view);
        contentText = typedArray.getString(R.styleable.my_text_view_contentText);
        tipTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tipTextViewOnClick();
            }
        });
        initFlag();
        getLineNum();
    }

    /**
     * 在此实现对contentTextView的显示控制
     */
    private void tipTextViewOnClick() {
        if (lineNum <= initLines) {
            return;
        }
        if (SEE_ALL == flag) {
            tipTextView.setText("收起");
            flag = PICK_UP;
            // 点击全文
            contentTextView.setMaxLines(lineNum);
        } else if (PICK_UP == flag) {
            tipTextView.setText("全文");
            flag = SEE_ALL;
            // 点击收起
            contentTextView.setMaxLines(initLines);
        }
    }

    /**
     * 初始化MyTextView
     */
    private void initMyTextView() {
        if (lineNum > initLines) {
            if (tipTextView.getVisibility() != VISIBLE) {
                tipTextView.setVisibility(VISIBLE);
            }
            contentTextView.setMaxLines(initLines);
        } else if (lineNum <= initLines) {
            if (tipTextView.getVisibility() != GONE) {
                tipTextView.setVisibility(GONE);
            }

        }
    }

    /**
     * 初始化flag
     */
    private void initFlag() {
        String content = tipTextView.getText().toString();
        if (!("全文".equals(content))) {
            tipTextView.setText("全文");
            flag = SEE_ALL;
        }
    }

    /**
     * 获取contentTextView的最大行数
     */
    private void getLineNum() {
        /** 新方法 */
        //        lineNum=contentTextView.getHeight()/contentTextView.getLineHeight();
        //        Log.d(tag,"lineNum = "+lineNum);
        //        initMyTextView();
        /** 新方法结束 */
        contentTextView.post(new Runnable() {
            @Override
            public void run() {
                lineNum = contentTextView.getLineCount();
                Log.d(tag, "lineNum=" + lineNum);
                //                lineNum=11;
                initMyTextView();
            }
        });
    }

    public TextView getContentTextView() {
        return contentTextView;
    }
    
    
    public void setContentTextView(String contentText) {
        this.contentText=contentText;
        contentTextView.setText(contentText);
        getLineNum();
        invalidate();
    }

    public TextView getTipTextView() {
        return tipTextView;
    }

    public void setContentTextViewOnClickListener(OnClickListener contentTextViewOnClickListener) {
        contentTextView.setOnClickListener(contentTextViewOnClickListener);
    }
}
