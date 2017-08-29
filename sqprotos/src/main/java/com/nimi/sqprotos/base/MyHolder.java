package com.nimi.sqprotos.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.nimi.sqprotos.listener.CallBackListener;

import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date 2017/6/27
 */

public  class MyHolder extends RvHolder {
    public CallBackListener mListener;
    public MyHolder(SActivity context, int layoutId,CallBackListener mListener) {
        super(context, layoutId);
        this.mListener=mListener;
    }

    public MyHolder(SActivity context, View itemView,CallBackListener mListener) {
        super(context, itemView);
        this.mListener=mListener;
    }

    

    public  void bindItemHolder(RvHolder holder, Map<String, Object> data, int position,MyAdapter adapter){
        
    }


    //默认是竖直排列，显示一行（和listview的排列）
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

}
