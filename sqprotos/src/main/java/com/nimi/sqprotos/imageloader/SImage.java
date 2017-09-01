package com.nimi.sqprotos.imageloader;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:02
 * 描述:
 */
public class SImage {
    private static SImageLoader sImageLoader;

    private SImage() {
    }

    private static final SImageLoader getImageLoader() {
        if (sImageLoader == null) {
            synchronized (SImage.class) {
                if (sImageLoader == null) {
                    if (isClassExists("org.xutils.x")) {
                        sImageLoader = new SXUtilsImageLoader();
                    } else if (isClassExists("com.bumptech.glide.Glide")) {
                        sImageLoader = new SGlideImageLoader();
                    } else if (isClassExists("com.squareup.picasso.Picasso")) {
                        sImageLoader = new SPicassoImageLoader();
                    } else if (isClassExists("com.nostra13.universalimageloader.core.ImageLoader")) {
                        sImageLoader = new SUILImageLoader();
                    }else {
                        throw new RuntimeException("必须在你的 build.gradle 文件中配置「Glide、Picasso、universal-image-loader、XUtils3」中的某一个图片加载库的依赖,或者检查是否添加了图库的混淆配置");
                    }
                }
            }
        }
        return sImageLoader;
    }

    private static final boolean isClassExists(String classFullName) {
        try {
            Class.forName(classFullName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void displayImage(Activity activity, ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final SImageLoader.SDisplayImageListener delegate) {
        getImageLoader().displayImage(activity, imageView, path, loadingResId, failResId, width, height, delegate);
    }

    public static void downloadImage(Context context, String path, final SImageLoader.SDownloadImageListener delegate) {
        getImageLoader().downloadImage(context, path, delegate);
    }
}