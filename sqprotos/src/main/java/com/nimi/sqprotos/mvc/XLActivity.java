package com.nimi.sqprotos.mvc;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.base.MyHolder;
import com.nimi.sqprotos.base.ViHolder;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date 2017/7/11
 */

public abstract class XLActivity extends XActivity {
    public boolean isShowEmpt = true;//是否显示空视图
    public int LIST_INDEXS = 1;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void loadInfos(int index) {

    }

    @Override
    public LinearLayout setEmpt(LinearLayout mView) {
        ViHolder emptholder=new ViHolder(mView);
        emptholder.setText(R.id.empt_tv,"").setImageResource(R.id.empt_img,0);
        return (LinearLayout) emptholder.getHolderView();
    }

    @Override
    public MyHolder getHolder() {
        return null;
    }

    @Override
    public void showToast(String mess, int posi, Object obj) {

    }

    @Override
    public void initStatus() {
        if (mBaseListStatusView != null) {
            mBaseListStatusView.setShowEmpt(isShowEmpt);
            mBaseListStatusView.setStatusLister(this);
            mBaseListStatusView.setIsLoad(new boolean[]{true, true});
            mBaseListStatusView.setManager(1, 0);
            mBaseListStatusView.builder();
        }
    }

    @Override
    public void backInfo(int posi, String mess, Object data) {
        if (data instanceof List && posi == LIST_INDEXS) {
            if (mBaseListStatusView != null) {
                mBaseListStatusView.setData((List<Map<String, Object>>) data);
            }
        }
        super.backInfo(posi, mess, data);
    }

    @Override
    public void dissStausViewOrToast(int posi, int type) {
        if (posi == LIST_INDEXS && mBaseListStatusView != null) {
            mBaseListStatusView.stopReLoad();
        }
        super.dissStausViewOrToast(posi, type);
    }


    @Override
    public void dealPublic(int posi, Map<String, Object> map) {
        if (posi == LIST_INDEXS && mBaseListStatusView != null) {
            mBaseListStatusView.stopReLoad();
        }
        super.dealPublic(posi, map);
    }

}
