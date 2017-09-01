package com.nimi.sqprotos.until;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.listener.CallBackListener;


/**
 * Created by Administrator on 2016/9/13.
 */
public class PopWindoUntil {
    private View content;
    private View v;
    int[] location;
    private PopupWindow popupWindow;
    private Context context;
    private float x;
    private float y;
    private CallBackListener mListener;

    public void setListener(CallBackListener listener) {
        mListener = listener;
    }

    public PopWindoUntil(Context context, View content, View v) {
        this.content = content;
        this.v = v;
        this.context = context;
        x = context.getResources().getDimension(R.dimen.dimen_80px);
        y = context.getResources().getDimension(R.dimen.dimen_70px);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    public PopWindoUntil bulider() {
        popupWindow = new PopupWindow(content, (int) x, (int) y);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置监听  
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mListener!=null){
                    mListener.callBack(12,1,null,null);
                }
            }
        });
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        location = new int[2];
        v.getLocationOnScreen(location);
        return this;
    }

    public void showPopUp(Positions po) {//po 1左边,2 下方 3 右边 ，4上方

        if (po == Positions.LEFT) {
            float x1 = context.getResources().getDimension(R.dimen.dimen_5px);
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - popupWindow.getWidth(), location[1] - (int) x1);
        } else if (po == Positions.BOTTOM) {
            popupWindow.showAsDropDown(v);
        } else if (po == Positions.RIGHT) {
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] + v.getWidth(), location[1]);
        } else if (po == Positions.TOP) {
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
        }

    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public enum Positions {
        LEFT(1), BOTTOM(2), RIGHT(3), TOP(4);
        int po;

        Positions(int po) {
            this.po = po;
        }

        public int getPo() {
            return po;
        }

        public void setPo(int po) {
            this.po = po;
        }
    }
}
