package com.nimi.sqprotos.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date 2017/6/27
 */

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    public List<Map<String, Object>> mList = new ArrayList<>();
    public Context mContext;
    public int itemLayOutId = 0;
    public MyHolder holder;



    public MyAdapter(Context context,MyHolder holder) {
        super();
        this.holder=holder;
        this.mContext = context;
    }

    public MyAdapter(Context context, List<Map<String, Object>> list,MyHolder holder) {
        super();
        this.mContext = context;
        this.holder=holder;
        mList = list;
    }

    public List<Map<String, Object>> getList() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  holder;
    }

    public void setList(List<Map<String, Object>> mList) {
        if (mList != null) {
            this.mList = mList;
            notifyDataSetChanged();
        }
    }

    public void addList(List<Map<String, Object>> mList) {
        if (mList != null) {
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
    }

    public void setItem(int po, Map<String, Object> item) {
        if (mList != null) {
            mList.set(po, item);
        }
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Map<String, Object> data = mList.get(position);
        holder.bindItemHolder(holder, data, position,this);
    }

   

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
