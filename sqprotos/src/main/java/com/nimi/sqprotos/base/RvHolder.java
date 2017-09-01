package com.nimi.sqprotos.base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nimi.sqprotos.imageloader.SImage;
import com.nimi.sqprotos.imageloader.SImageLoader;

/**
 * @author
 * @version 1.0
 * @date 2017/8/21
 */
/**
 * 自定义的viewholder，用于处理列表的item
 */

public class RvHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mView;
    public SActivity mActivity;

    public RvHolder(SActivity context, int layoutId) {
        this(context, LayoutInflater.from(context).inflate(layoutId, null));
    }

    public RvHolder(SActivity context, View itemView) {
        super(itemView);
        mActivity = context;
        mView = itemView;
        mViews = new SparseArray<View>();
    }

    

    public static RvHolder createViewHolder(SActivity context, View itemView) {
        RvHolder holder = new RvHolder(context, itemView);
        return holder;
    }

    public static RvHolder createViewHolder(SActivity context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        RvHolder holder = new RvHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getV(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public View getItemView() {
        return mView;
    }


    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public RvHolder setText(int viewId, String text) {
        TextView tv = getV(viewId);
        tv.setText(text);
        return this;
    }

    public RvHolder setImageResource(int viewId, int resId) {
        ImageView view = getV(viewId);
        view.setImageResource(resId);
        return this;
    }

    public RvHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getV(viewId);
        view.setImageBitmap(bitmap);

        return this;
    }

    public RvHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getV(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public RvHolder setImageUrl(int viewId, String url, int w, int h, int default_img,SImageLoader.SDisplayImageListener li) {
        ImageView view = getV(viewId);
        SImage.displayImage(mActivity,view, url,default_img,default_img,w,h,li);//加载网络图片（因为返回的url格式不固定需要处理在进行加载）
        return this;
    }


    public RvHolder setBackgroundColor(int viewId, int color) {
        View view = getV(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RvHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getV(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public RvHolder setTextColor(int viewId, int textColor) {
        TextView view = getV(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public RvHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getV(viewId);
        view.setTextColor(mActivity.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public RvHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getV(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getV(viewId).startAnimation(alpha);
        }
        return this;
    }

    public RvHolder setVisible(int viewId, boolean visible) {
        View view = getV(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public RvHolder setVisible(int viewId, int visible) {
        View view = getV(viewId);
        view.setVisibility(visible);
        return this;
    }

    public RvHolder linkify(int viewId) {
        TextView view = getV(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public RvHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getV(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public RvHolder setProgress(int viewId, int progress) {
        ProgressBar view = getV(viewId);
        view.setProgress(progress);
        return this;
    }

    public RvHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getV(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public RvHolder setMax(int viewId, int max) {
        ProgressBar view = getV(viewId);
        view.setMax(max);
        return this;
    }

    public RvHolder setRating(int viewId, float rating) {
        RatingBar view = getV(viewId);
        view.setRating(rating);
        return this;
    }

    public RvHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getV(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public RvHolder setTag(int viewId, Object tag) {
        View view = getV(viewId);
        view.setTag(tag);
        return this;
    }

    public RvHolder setTag(int viewId, int key, Object tag) {
        View view = getV(viewId);
        view.setTag(key, tag);
        return this;
    }

    public RvHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getV(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public RvHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getV(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RvHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getV(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public RvHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getV(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
    
}
