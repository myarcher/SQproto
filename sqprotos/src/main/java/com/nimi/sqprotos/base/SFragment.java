package com.nimi.sqprotos.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.manager.ToastManager;
import com.nimi.sqprotos.bean.ToastBean;
import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.dialog.MyDialogs;
import com.nimi.sqprotos.listener.CallBackListener;
import com.nimi.sqprotos.toast.ToastCallBackLister;
import com.nimi.sqprotos.toast.Types;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;


/**
 * 所有fragmeng的基类，处理和activity基类类似 ，懒加载 （参考sacyivity）
 *
 * @author
 * @version 1.0
 * @date 2017/5/2
 */

public abstract class SFragment extends Fragment implements ToastManager.ToastManagerListener,CallBackListener {
    
    // 当前Fragment 是否初始化
    protected boolean isViewInit = false;
    // 当前Fragment 是否可见
    protected boolean isVisible = false;
    // 是否加载过数据
    protected boolean isLoadData = false;
    //xml转换器（个人定义）
    public LayoutInflater mInflater;
    //fragment对应的 activity
    public SActivity mActivity;
    //全局上下文环境
    public AppAppliction abApplication = null;
    //布局layout的根布局
    public RelativeLayout ab_base = null;
    public boolean isEvent = false;
    //fragment 的页面请求防止内存泄漏
    private ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();
    // 构造函数，一般使用带参数的
    private MyDialogs mMyDialogs;

    protected ViHolder mHolder;
    public SFragment() {
    }

    public SFragment(SActivity baseUi) {
        this.mActivity = baseUi;
        abApplication = AppAppliction.applictions;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (SActivity) activity;
    }

    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        preLoadData(false);
    }

    protected abstract void loadData();//数据请求

    public void preLoadData(boolean forceLoad) {//预加载
        //只有在页面可见，view已经初始化好，没有加载过数据，或者强制刷新的时候在加载数据
        if (isViewInit && isVisible && (!isLoadData || forceLoad)) {
            loadData();
            isLoadData = true;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isViewInit = true;//view已经初始化好
        initData(savedInstanceState);
        // 防止一开始加载的时候未 调用 preLoadData 方法， 因为setUserVisibleHint 比 onActivityCreated 触发 前
        if (getUserVisibleHint()) {
            preLoadData(false);
        }
    }

    /**
     * 提示框显示（同activity）
     *
     * @param mess  提示消息
     * @param flag  判断是第几个弹出框
     * @param types 暂时不用管，没用到
     * @param obj   回调附带的数据
     */
   /* public void showMess(String mess, int flag, MyToast.Types types, Object obj) {
        ToastBean bean = new ToastBean(mess, types, flag, obj);
        ToastManager.getToastManager().finisCurrent();
        ToastManager.getToastManager().showToast(bean, this);
    }*/

    public void showMess(String mess, int flag, Types types, Object obj) {
        ToastBean bean = new ToastBean(mess, types, flag, obj);
        if(mMyDialogs!=null&&mMyDialogs.isShow()){
            mMyDialogs.dissmiss();
            mMyDialogs=null;
        }
        mMyDialogs=new MyDialogs(mActivity, bean, new ToastCallBackLister() {
            @Override
            public void beginClick(ToastBean bean) {
                if (bean.getFlag() != BaseContanse.MYTOAST_ER) {//默认情况-1是进行回调处理，只是显示，下面判断相同
                    Message message = new Message();
                    message.what = 1;
                    message.obj = bean;
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void forwordclick(ToastBean bean) {
                if(mMyDialogs!=null&&mMyDialogs.isShow()){
                    mMyDialogs.dissmiss();
                    mMyDialogs=null;
                }
                if (bean.getFlag() != BaseContanse.MYTOAST_ER) {
                    Message message = new Message();
                    message.what = 2;
                    message.obj = bean;
                    mHandler.sendMessage(message);
                }
            }
        });
        mMyDialogs.show();
    }
    /**
     * 弹出框的未显示时的回调
     * @param bean
     */
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                start((ToastBean) msg.obj);
            } else if (msg.what == 2) {
                stop((ToastBean) msg.obj);
            }
        }
    };
    //显示提示框之前的回调
    @Override
    public void start(ToastBean bean) {

    }
    //显示提示框后的回调
    @Override
    public void stop(ToastBean bean) {

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //   mActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        // mActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        
        if (ab_base != null) {
            ViewGroup parent = (ViewGroup) ab_base.getParent();
            if (null != parent) {
                parent.removeView(ab_base);
            }
        } else {
            ab_base = new RelativeLayout(mActivity);
            ab_base.setBackgroundColor(Color.rgb(255, 255, 255));
            if (getLayoutId() > 0) {
                ab_base.removeAllViews();
                ab_base.addView(inflater.inflate(getLayoutId(), container, false), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                initView();
            }
        }
        mHolder = new ViHolder(ab_base);
        return ab_base;
    }

    public void initView() {//初始化数据

    }

    public abstract int getLayoutId();//返回layout布局文件


    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    @CallSuper
    public void onDestroyView() {

        if(mMyDialogs!=null&&mMyDialogs.isShow()){
            mMyDialogs.dissmiss();
            mMyDialogs=null;
        }
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        clear();
        super.onDestroy();
    }


    public void addDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            listCompositeDisposable.add(disposable);
        }
    }


    protected void clear() {
        if (!listCompositeDisposable.isDisposed()) {
            listCompositeDisposable.clear();
        }
    }

    @Override
    public void callBack(long code, long stat, Object value1, Object value2) {

    }

    

    public <T> T $(int viewID) {
        return (T) mHolder.get(viewID);
    }

}
