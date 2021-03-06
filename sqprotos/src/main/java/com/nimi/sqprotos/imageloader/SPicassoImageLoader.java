package com.nimi.sqprotos.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:57
 * 描述:
 */
public class SPicassoImageLoader extends SImageLoader {

    @Override
    public void displayImage(Activity activity, final ImageView imageView, final String finalPath, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final SDisplayImageListener
            listener) {
        Picasso.with(activity).load(finalPath).placeholder(loadingResId).error(failResId).resize(width, height).centerCrop().into(imageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                if (listener != null) {
                    listener.onSuccess(imageView, finalPath,null);
                }
            }
        });
    }

    @Override
    public void downloadImage(Context context, final String finalPath, final SDownloadImageListener listener) {
        Picasso.with(context.getApplicationContext()).load(finalPath).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (listener != null) {
                    listener.onSuccess(finalPath, bitmap);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (listener != null) {
                    listener.onFailed(finalPath);
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

}
