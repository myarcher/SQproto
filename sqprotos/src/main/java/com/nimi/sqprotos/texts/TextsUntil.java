package com.nimi.sqprotos.texts;

import android.graphics.Paint;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/13.
 */
public class TextsUntil {
    public static void setTextSpans(TextView tv, SpannableString... spans) {
        setTextSpan(tv, spans);
    }

    public static void setTextSpan(TextView tv, SpannableString... spans) {
        tv.setText("");
        if (TextUtils.isEmpty(tv.getText().toString())) {
            tv.setText(spans[0]);
        } else {
            tv.append(spans[0]);
        }
        for (int i = 1; i < spans.length; i++) {
            tv.append(spans[i]);
        }
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
}
