package com.nimi.sqprotos.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:51
 * 描述:
 */
public class SGlideImageLoader extends SImageLoader {

    @Override
    public void displayImage(Activity activity, final ImageView imageView, final String finalPath, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final SDisplayImageListener
            listener) {
        Glide.with(activity).load(finalPath).asBitmap().placeholder(loadingResId).error(failResId).override(width, height).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (listener != null) {
                    Drawable drawable =new BitmapDrawable(resource);
                    listener.onSuccess(imageView, finalPath,drawable);
                }
                return false;
            }
        }).into(imageView);
    }

    @Override
    public void downloadImage(Context context, final String finalPath, final SDownloadImageListener listener) {
        Glide.with(context.getApplicationContext()).load(finalPath).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (listener != null) {
                    listener.onSuccess(finalPath, resource);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                if (listener != null) {
                    listener.onFailed(finalPath);
                }
            }
        });
    }

}