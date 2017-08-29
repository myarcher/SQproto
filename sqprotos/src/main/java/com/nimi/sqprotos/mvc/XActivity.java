package com.nimi.sqprotos.mvc;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.KeyEvent;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.base.BaseListStatusView;
import com.nimi.sqprotos.base.SActivity;
import com.nimi.sqprotos.manager.AppManager;
import com.nimi.sqprotos.service.BService;
import com.nimi.sqprotos.toast.MyToast;
import com.nimi.sqprotos.toast.Types;

import java.util.Map;

import retrofit2.Call;

/**
 * @author
 * @version 1.0
 * @date 2017/7/10
 */

public abstract class XActivity extends SActivity implements XContract.View,BaseListStatusView.StatusLister {
    public BaseListStatusView mBaseListStatusView;
    /**
     * 防止网路请求内存泄漏
     */
    public BService mService;
    public int isFirst;//1 显示状态视图 2，弹出框 3，都不显示
    public MyToast mToasts;
    public boolean isResumeoad = false; //是否在onresume方法加载数据 默认实在oncreate中加载 true 
    public BXPresenter presenter;
    public BXPresenter setPresenter() {
        return new BXPresenter(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBaseListStatusView=$(R.id.mBaseListStatusView);
        mService = AppManager.getInstance().mBClient.create(BService.class);
        presenter = setPresenter();
        if (!isResumeoad) {
            mBaseListStatusView.loadInfo();
        }
        initStatus();
    }

    public void initStatus() {
        
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isResumeoad) {
            mBaseListStatusView.loadInfo();
        }
    }



    @Override
    @CallSuper
    protected void onDestroy() {
        presenter.unsubscribe();
        presenter=null;
        super.onDestroy();
    }

    @Override
    public void showStausViewOrToast(int posi, int type) {
        if (type == 1) {
            if (isFirst == 1) {
                mBaseListStatusView.showStausViewOrToast();
            } else if (isFirst == 2) {
                mToasts = new MyToast(this, null, null);
                mToasts.show();
            }
        }
    }

    @Override
    public void dissStausViewOrToast(int posi, int type) {
        if (isFirst == 1) {
            mBaseListStatusView.dissStausViewOrToast(posi,type);
        } else if (isFirst == 2) {
            if (mToasts != null && mToasts.isShow()) {
                mToasts.dissmiss();
            }
        }
    }

    @Override
    public void backInfo(int posi,String mess,Object data) {
        
    }
    @Override
    public void dealPublic(int posi, Map<String, Object> map) {
      
    }

    @Override
    public XModel getXmodel() {
        return new XModel();
    }


    @Override
    public void showErrToast(String title, int posi, Object object) {
        showMess(title + "", -1, Types.ERREY, null);
    }
    

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            if (mToasts != null && mToasts.isShow()) {
                mToasts.dissmiss();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
