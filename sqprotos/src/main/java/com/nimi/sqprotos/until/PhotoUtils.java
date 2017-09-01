package com.nimi.sqprotos.until;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nimi.sqprotos.bean.BitmapPhoto;
import com.nimi.sqprotos.imageselector.MultiImageSelectorActivity;
import com.nimi.sqprotos.until.Units;
import com.nimi.sqprotos.view.DeleteImgView;
import com.nimi.sqprotos.view.FlowLayout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhotoUtils {
    public static ViewGroup.MarginLayoutParams getParams(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int margin = 10;
        int wdithView = (dm.widthPixels - Units.dpTopx(context, 115)) / 4;
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(wdithView, wdithView);
        params.leftMargin = margin;
        params.topMargin = margin;
        params.rightMargin = margin;
        params.bottomMargin = margin;
        return params;
    }

    public static void bindView(final Activity activity, final ImageView addImage, final FlowLayout flowlayout, final List<BitmapPhoto> bitmapPhotos, final ArrayList<String> mSelectPath) {
        flowlayout.removeAllViews();
        for (int i = 0; i < bitmapPhotos.size(); i++) {
            BitmapPhoto bitmapPhoto = bitmapPhotos.get(i);
            DeleteImgView imageView = new DeleteImgView(activity);
            imageView.setLayoutParams(getParams(activity));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setTag(i);
            imageView.setDeleteListener(new DeleteImgView.IDeleteListener() {
                @Override
                public void delete(View v) {
                    int index = (Integer) v.getTag();
                    mSelectPath.remove(index);
                    bitmapPhotos.remove(index);
                    showimage(activity, addImage, flowlayout, bitmapPhotos, mSelectPath);
                }
            });
            //	            imageView.setOnClickListener(new View.OnClickListener() {
            //	                @Override
            //	                public void onClick(View v) {
            //	                    int index = (int) v.getTag();
            //	                    mSelectPath.remove(index);
            //	                    bitmapPhotos.remove(index);
            //	                    showimage(activity,addImage,flowlayout,bitmapPhotos,mSelectPath);
            //	                }
            //	            });
            imageView.setImageBitmap(bitmapPhoto.getmBitmap());
            flowlayout.addView(imageView);
        }
        if (bitmapPhotos.size() < 9) {
            flowlayout.addView(addImage);
        }

        /**
         * 打工选择的图片个数为0时隐藏
         */
        if (bitmapPhotos.size() == 0) {
            flowlayout.setVisibility(View.VISIBLE);
        } else {
            flowlayout.setVisibility(View.VISIBLE);
        }
    }

    public static int findMaxSort(List<BitmapPhoto> bitmapPhotos) {
        if (bitmapPhotos != null && bitmapPhotos.size() > 0) {
            int maxSort = bitmapPhotos.get(0).getSort();
            for (int i = 1; i < bitmapPhotos.size(); i++) {
                if (bitmapPhotos.get(i).getSort() != Integer.MAX_VALUE) {
                    if (maxSort < bitmapPhotos.get(i).getSort()) {
                        maxSort = bitmapPhotos.get(i).getSort();
                    }
                }
            }
            return maxSort;
        }
        return -1;
    }

    public static void setectImage(Activity activity, Class<?> cls, ArrayList<String> mSelectPath, int requestCode) {
        Intent intent = new Intent(activity, cls);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 5);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void setectSigleImage(Activity activity, Class<?> cls, ArrayList<String> mSelectPath, int requestCode) {
        Intent intent = new Intent(activity, cls);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static int findBitmapByPath(String imagePath, List<BitmapPhoto> bitmapPhotos) {
        for (int i = 0; i < bitmapPhotos.size(); i++) {
            if (imagePath.equals(bitmapPhotos.get(i).getImagePath())) {
                return i;
            }
        }
        return -1;
    }

    public static int findSelectPathByPath(String imagePath, ArrayList<String> mSelectPath) {
        for (int i = 0; i < mSelectPath.size(); i++) {
            if (imagePath.equals(mSelectPath.get(i))) {
                return i;
            }
        }
        return -1;
    }
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 480)
                    && (options.outHeight >> i <= 480)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        int digree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        if (digree != 0) {
            // 旋转图片
            Matrix m = new Matrix();
            m.postRotate(digree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
        }
        return bitmap;
    }

    public static void showimage(Activity activity, ImageView addView, FlowLayout flowLayout, List<BitmapPhoto> bitmapPhotos, ArrayList<String> mSelectPath) {
        for (int i = bitmapPhotos.size() - 1; i >= 0; i--) {
            BitmapPhoto bitmapPhoto = bitmapPhotos.get(i);
            if (bitmapPhoto.getSort() != Integer.MAX_VALUE) {
                int index = findSelectPathByPath(bitmapPhoto.getImagePath(), mSelectPath);
                if (index == -1) {
                    bitmapPhotos.remove(i);
                }
            }
        }
        try {

            for (int i = 0; i < mSelectPath.size(); i++) {
                String imagePath = mSelectPath.get(i);
                Bitmap bitmap = null;
                int location = findBitmapByPath(imagePath, bitmapPhotos);
                if (location == -1) {
                    bitmap = revitionImageSize(mSelectPath.get(i));
                    BitmapPhoto bitmapPhoto = new BitmapPhoto();
                    bitmapPhoto.setmBitmap(bitmap);
                    bitmapPhoto.setImagePath(imagePath);
                    bitmapPhoto.setSort(findMaxSort(bitmapPhotos) + 1);
                    bitmapPhotos.add(bitmapPhoto);
                }
            }
            Collections.sort(bitmapPhotos);
            bindView(activity, addView, flowLayout, bitmapPhotos, mSelectPath);
        } catch (Exception e) {

        }
    }

    public static Bitmap createScaledBitmap(Context context, Bitmap bitmap, int getWidth) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        //	        int width = dm.widthPixels+1;
        //	        int height = dm.heightPixels+1;
        int t_width;
        int t_height;
        if (bitmap.getWidth() > getWidth || bitmap.getHeight() > getWidth) {
            t_width = getWidth;
            t_height = bitmap.getHeight() * getWidth / bitmap.getWidth();
            if (t_height > getWidth) {
                t_width = t_width * getWidth / t_height;
                t_height = getWidth;
            }
        } else if (bitmap.getWidth() < getWidth && bitmap.getHeight() < getWidth) {
            t_width = getWidth;
            t_height = bitmap.getHeight() * getWidth / bitmap.getWidth();
            if (t_height > getWidth) {
                t_width = t_width * getWidth / t_height;
                t_height = getWidth;
            }
        } else {
            t_width = bitmap.getWidth();
            t_height = bitmap.getHeight();
        }
        return bitmap = Bitmap.createScaledBitmap(bitmap, t_width, t_height, true);
    }


    public static void showimages(Activity activity, ImageView addView, FlowLayout flowLayout, List<BitmapPhoto> bitmapPhotos, ArrayList<String> mSelectPath) {
        for (int i = bitmapPhotos.size() - 1; i >= 0; i--) {
            BitmapPhoto bitmapPhoto = bitmapPhotos.get(i);
            if (bitmapPhoto.getSort() != Integer.MAX_VALUE) {
                int index = findSelectPathByPath(bitmapPhoto.getImagePath(), mSelectPath);
                if (index == -1) {
                    bitmapPhotos.remove(i);
                }
            }
        }
        try {

            for (int i = 0; i < mSelectPath.size(); i++) {
                String imagePath = mSelectPath.get(i);
                Bitmap bitmap = null;
                int location = findBitmapByPath(imagePath, bitmapPhotos);
                if (location == -1) {
                    bitmap = revitionImageSize(mSelectPath.get(i));
                    BitmapPhoto bitmapPhoto = new BitmapPhoto();
                    bitmapPhoto.setmBitmap(bitmap);
                    bitmapPhoto.setImagePath(imagePath);
                    bitmapPhoto.setSort(findMaxSort(bitmapPhotos) + 1);
                    bitmapPhotos.add(bitmapPhoto);
                }
            }
            Collections.sort(bitmapPhotos);
            bindViews(activity, addView, flowLayout, bitmapPhotos, mSelectPath);
        } catch (Exception e) {

        }
    }

    public static void bindViews(final Activity activity, final ImageView addImage, final FlowLayout flowlayout, final List<BitmapPhoto> bitmapPhotos, final ArrayList<String> mSelectPath) {
        flowlayout.removeAllViews();
        for (int i = 0; i < bitmapPhotos.size(); i++) {
            BitmapPhoto bitmapPhoto = bitmapPhotos.get(i);
            DeleteImgView imageView = new DeleteImgView(activity);
            imageView.setLayoutParams(getParams(activity));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setTag(i);
            imageView.setDeleteListener(new DeleteImgView.IDeleteListener() {
                @Override
                public void delete(View v) {
                    int index = (Integer) v.getTag();
                    mSelectPath.remove(index);
                    bitmapPhotos.remove(index);
                    showimage(activity, addImage, flowlayout, bitmapPhotos, mSelectPath);
                }
            });
            //	            imageView.setOnClickListener(new View.OnClickListener() {
            //	                @Override
            //	                public void onClick(View v) {
            //	                    int index = (int) v.getTag();
            //	                    mSelectPath.remove(index);
            //	                    bitmapPhotos.remove(index);
            //	                    showimage(activity,addImage,flowlayout,bitmapPhotos,mSelectPath);
            //	                }
            //	            });
            imageView.setImageBitmap(bitmapPhoto.getmBitmap());
            flowlayout.addView(imageView);
        }
        if (bitmapPhotos.size() < 9) {
            flowlayout.addView(addImage);
        }

        /**
         * 打工选择的图片个数为0时隐藏
         */
        if (bitmapPhotos.size() == 0) {
            flowlayout.setVisibility(View.GONE);
        } else {
            flowlayout.setVisibility(View.VISIBLE);
        }
    }
}
