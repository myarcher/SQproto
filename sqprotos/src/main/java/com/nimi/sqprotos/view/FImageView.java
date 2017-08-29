package com.nimi.sqprotos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.nimi.sqprotos.R;

/**
 * @author
 * @version 1.0
 * @date 2017/6/23
 */

public class FImageView extends ImageView {

    private MyAnimationDrawable animationDrawable; //播放的资源集合

    public FImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimation(context);
    }

    public void initAnimation(Context context) {
        Animation circle_anim = AnimationUtils.loadAnimation(context, R.anim.progressbar);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿  
        circle_anim.setInterpolator(interpolator);
        ImageView img=new ImageView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getWidth(),getHeight());
        img.setLayoutParams(layoutParams);
        if (circle_anim != null) {
            img.startAnimation(circle_anim);  //开始动画  
        }
    }
}
