package com.nimi.sqprotos.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.statusview.MultipleStatusView;
import com.nimi.sqprotos.toast.MyToast;
import com.nimi.sqprotos.xrecyclerview.ProgressStyle;
import com.nimi.sqprotos.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date 2017/8/21
 */

public class BaseListStatusView extends LinearLayout implements XRecyclerView.LoadingListener {
    public MultipleStatusView status_view;
    public XRecyclerView list_recycler;
    public TextView empt_tv;
    public ImageView empt_img;
    public LinearLayout empt_view;
    public Context context;
    public boolean isShowEmpt = true;//是否显示空视图
    public int index = 1;
    public MyAdapter mAdapter;
    public StatusLister mStatusLister;
    public MyHolder mHolder;
    public boolean isLoad[] = new boolean[]{true, true};
    public RecyclerView.LayoutManager mManager;

    public BaseListStatusView(Context context) {
        super(context);
        initview(context);
    }

    public BaseListStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public BaseListStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    @SuppressLint("NewApi")
    public BaseListStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initview(context);
    }

    public void initview(Context context) {
        this.context = context;
        status_view = (MultipleStatusView) findViewById(R.id.status_view);
        list_recycler = (XRecyclerView) findViewById(R.id.list_recycler);
        list_recycler.setLoadingListener(this);
    }

    public MyAdapter getAdapter() {
        return new MyAdapter(context, getHolder());
    }

    public BaseListStatusView setStatusLister(StatusLister mstatuslistener) {
        this.mStatusLister = mstatuslistener;
        return this;
    }

    public BaseListStatusView setEmpt_view(LinearLayout empt_view) {
        this.empt_view = empt_view;
        return this;
    }

    public LinearLayout getEmpt_view() {
        if (mStatusLister != null) {
            empt_view = mStatusLister.setEmpt(empt_view);
        }
        return empt_view;
    }

    public BaseListStatusView setIsLoad(boolean[] isLoad) {
        this.isLoad = isLoad;
        return this;
    }

    public void builder() {
        if (isLoad == null) {
            isLoad = new boolean[]{true, true};
        }
        setIsResh();
        mAdapter = getAdapter();
        list_recycler.setLayoutManager(getManager());
        if (isShowEmpt) {
            list_recycler.setEmptyView(getEmpt_view());
        }
        list_recycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        list_recycler.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        status_view.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInfo();
            }
        });
        list_recycler.setAdapter(mAdapter);
    }


    public BaseListStatusView setShowEmpt(boolean showEmpt) {
        isShowEmpt = showEmpt;
        return this;
    }

    public RecyclerView.LayoutManager getManager() {
        if (mManager == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mManager = layoutManager;
        }
        return mManager;
    }

    public BaseListStatusView setManager(RecyclerView.LayoutManager manager) {
        mManager = manager;
        return this;
    }

    public BaseListStatusView setManager(int type, int count) {
        if (type == 1) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mManager = layoutManager;
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(context, count);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mManager = layoutManager;
        }
        return this;
    }

    public MyHolder getHolder() {
        if (mHolder == null) {
            if (mStatusLister != null) {
                mHolder = mStatusLister.getHolder();
            }
            if (mHolder == null) {
                mHolder = new MyHolder((SActivity) context, 0, null);
            }
        }
        return mHolder;
    }

    public void setHolder(MyHolder holder) {
        mHolder = holder;
    }

    //默认刷新和加载
    public void setIsResh() {
        list_recycler.setPullRefreshEnabled(isLoad[0]);
        list_recycler.setLoadingMoreEnabled(isLoad[1]);
    }

    @Override
    public void onRefresh() {
        index = 1;
        loadInfo();
    }

    public void loadInfo() {
        if (mStatusLister != null) {
            mStatusLister.loadInfos(index);
        }
    }

    @Override
    public void onLoadMore() {
        index++;
        loadInfo();
    }

    public void setData(List<Map<String, Object>> list) {
        if (index == 1) {
            mAdapter.clear();
        }
        mAdapter.addList(list);
        mAdapter.notifyDataSetChanged();
    }


    public void stopReLoad() {
        list_recycler.loadMoreComplete();
        list_recycler.refreshComplete();
    }

    public void showStausViewOrToast() {
        status_view.showLoading();
    }

    public void dissStausViewOrToast(int posi, int type) {
        if (type == 1) {
            status_view.showContent();
        } else if (type == 2) {
            status_view.showContent();
        } else if (type == BaseContanse.HTTP_ERROR) {
            status_view.showError();
        } else if (type == BaseContanse.PARSE_ERROR) {
            if (mStatusLister != null) {
                mStatusLister.showToast("解析数据错误", posi, null);
            }
        } else if (type == BaseContanse.NETWORD_ERROR) {
            status_view.showNoNetwork();
        } else if (type == BaseContanse.STATUS_ERROR) {
            if (mStatusLister != null) {
                mStatusLister.showToast("其他错误", posi, null);
            }
        }
    }

    public interface StatusLister {
        public void loadInfos(int index);
        public LinearLayout setEmpt(LinearLayout mView);
        public void showToast(String mess, int posi, Object obj);
        public MyHolder getHolder();
    }
}
