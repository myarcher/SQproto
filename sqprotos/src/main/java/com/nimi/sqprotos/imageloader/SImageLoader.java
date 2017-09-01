package com.nimi.sqprotos.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午5:58
 * 描述:
 */
public abstract class SImageLoader {
    
    public abstract void displayImage(Activity activity, ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, SDisplayImageListener 
            displayImageListener);

    public abstract void downloadImage(Context context, String path, SDownloadImageListener downloadImageListener);

    public interface SDisplayImageListener {
        void onSuccess(View view, String path, Drawable drawable);
    }

    public interface SDownloadImageListener {
        void onSuccess(String path, Bitmap bitmap);

        void onFailed(String path);
    }
}