package com.nimi.sqprotos.until;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author
 * @version 1.0
 * @date 2017/6/2
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public GridSpacingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        if (parent.getChildLayoutPosition(view) %2==1) {
            outRect.left = space/2;
            outRect.right = space;
        }else {
            outRect.left = space;
            outRect.right = space/2;
        }
    }

}
