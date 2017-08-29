package com.nimi.sqprotos.bean;

import android.graphics.Bitmap;

/**
 * 图片选择的实体
 */
public class BitmapPhoto implements Comparable<BitmapPhoto>{
    private Bitmap mBitmap;//图片
    private String imagePath;  // 路径
    private int sort;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int compareTo(BitmapPhoto another) {
        return getSort()-another.getSort();
    }
}
