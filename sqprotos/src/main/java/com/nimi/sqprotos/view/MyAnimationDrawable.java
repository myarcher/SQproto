package com.nimi.sqprotos.view;

import android.graphics.drawable.AnimationDrawable;

/**
 * @author
 * @version 1.0
 * @date 2017/6/23
 */

public class MyAnimationDrawable extends AnimationDrawable {

    private int repeatCount = 0;
    private boolean isRepeat = true;
    private AnimationEndListener listener;

    public MyAnimationDrawable() {
        super();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        if(!isRepeat) {
            repeatCount --;
            if(repeatCount == 1) {
                super.unscheduleSelf(this);
                if(listener != null) listener.onEnd();
            }
        }
    }

    public void setRepeatCount(int num) {
        if(num == 0) isRepeat = true;
        else {
            isRepeat = false;
            repeatCount = num*getNumberOfFrames();
        }
    }

    public void setAnimationEndListener(AnimationEndListener ls) {
        listener = ls;
    }

    /**
     * 动画播放结束回调接口
     * @author user
     *
     */
    public interface AnimationEndListener {
        public void onEnd();
    }
}
