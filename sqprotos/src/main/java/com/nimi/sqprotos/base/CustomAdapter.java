package com.nimi.sqprotos.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by wanglei on 2016/11/29.
 */

public abstract class CustomAdapter extends RecyclerView.Adapter<RvHolder> {
    
    public List<Map<String, Object>> mList = new ArrayList<>();
    public SActivity mContext;
    public int itemLayOutId = 0;
    public ViHolder holder;

    public CustomAdapter(SActivity context, int itemLayOutId) {
        super();
        this.mContext = context;
        this.itemLayOutId = itemLayOutId;
    }

    public CustomAdapter(SActivity context, int itemLayOutId, ViHolder holder) {
        super();
        this.mContext = context;
        this.itemLayOutId = itemLayOutId;
    }

    public CustomAdapter(SActivity context, List<Map<String, Object>> list, int itemLayOutId) {
        super();
        this.mContext = context;
        this.itemLayOutId = itemLayOutId;
        mList = list;
    }

    public List<Map<String, Object>> getList() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RvHolder.createViewHolder(mContext, parent, itemLayOutId);
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
    public void onBindViewHolder(RvHolder holder, int position) {
        Map<String, Object> data = mList.get(position);
        bindItemHolder(holder, data, position);
    }

    public void bindItemHolder(RvHolder holder, Map<String, Object> data, int position) {
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
