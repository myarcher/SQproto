package com.nimi.sqprotos.base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
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


public class ViHolder  {
    private View mView;

    private SActivity mActivity;

    private SparseArray<View> mViews;   // 包含了View引用的SparseArray  


    public ViHolder(View convertView) {
        this.mView = convertView;
    }

    public ViHolder(SActivity activity) {
        this.mActivity = activity;
    }


    public <T extends View> T get(int viewId) {//通过ViewId得到View
        if (mActivity == null) {// ListAdapter的ViewHolder
            SparseArray<View> viewHolder = (SparseArray<View>) mView.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                mView.setTag(viewHolder);
            }
            View childView = viewHolder.get(viewId);
            if (childView == null) {
                childView = mView.findViewById(viewId);
                viewHolder.put(viewId, childView);
            }
            return (T) childView;

        } else { // Activity的ViewHolder  

            if (mViews == null) {
                mViews = new SparseArray<View>();
            }
            View childView = mViews.get(viewId);
            if (childView == null) {
                childView = mActivity.findViewById(viewId);
                mViews.put(viewId, childView);
            }
            return (T) childView;
        }
    }

    public View getHolderView() {
        return mView;
    }

    public ViHolder setText(int viewId, String text) {// 通过ViewId设置Text  
        ((TextView) get(viewId)).setText(text);
        return this;
    }

    public ViHolder setText(int viewId, Spanned text) {// 通过ViewId设置Text  
        ((TextView) get(viewId)).setText(text);
        return this;
    }

    public ViHolder setTextColor(int viewId, int color) {//通过ViewId设置TextColor  
        ((TextView) get(viewId)).setTextColor(color);
        return this;
    }

    public ViHolder setImageResource(int viewId, int resId) {//通过ViewId设置图片  
        ((ImageView) get(viewId)).setImageResource(resId);
        return this;
    }

    public ViHolder setImageBitmap(int viewId, Bitmap bm) {// 通过ViewId设置图片  
        ((ImageView) get(viewId)).setImageBitmap(bm);
        return this;
    }

    public ViHolder setImageDrawable(int viewId, Drawable drawable) {//通过ViewId设置图片  
        ((ImageView) get(viewId)).setImageDrawable(drawable);
        return this;
    }

    public ViHolder setImageUrl(int id, String url) {//通过ViewId设置网络图片  
        //        Rosemary.loadImage(url, (ImageView) get(id));  
        return this;
    }

    public ViHolder setImageUrl(int id, String url, int resId) {// 通过ViewId设置网络图片  
        //        Rosemary.loadImage(url, (ImageView) get(id), resId);  
        return this;
    }

    public ViHolder setVisibility(int viewId, int visibility) {//通过ViewId设置隐藏和显示  
        get(viewId).setVisibility(visibility);
        return this;
    }

    public ViHolder setOnClickListener(int viewId, View.OnClickListener l) {//通过ViewId设置点击监听  
        get(viewId).setOnClickListener(l);
        return this;
    }
}
