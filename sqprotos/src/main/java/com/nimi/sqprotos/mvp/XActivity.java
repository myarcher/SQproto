package com.nimi.sqprotos.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

import com.nimi.sqprotos.manager.AppManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by wanglei on 2016/12/29.
 */

public abstract class XActivity<P extends IPresent> extends RxAppCompatActivity implements IView<P> {

    private VDelegate vDelegate;
    private P p;
    protected Activity context;

    private RxPermissions rxPermissions;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(null);
            bindEvent();
        }
        initData(savedInstanceState);

    }

    @Override
    public void bindUI(View rootView) {
    }

    protected VDelegate getvDelegate() {
        if (vDelegate == null) {
            vDelegate = VDelegateBase.create(context);
        }
        return vDelegate;
    }

    protected P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachV(this);
            }
        }
        return p;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getvDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getvDelegate().pause();
    }

   

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getP() != null) {
            getP().detachV();
        }
        getvDelegate().destory();
        p = null;
        vDelegate = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(AppManager.getInstance().isDebug);
        return rxPermissions;
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public void bindEvent() {

    }
}
