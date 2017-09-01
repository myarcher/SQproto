package com.nimi.sqprotos.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.manager.SActivityManager;
import com.nimi.sqprotos.manager.ToastManager;
import com.nimi.sqprotos.bean.ToastBean;
import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.dialog.MyDialogs;
import com.nimi.sqprotos.listener.CallBackListener;
import com.nimi.sqprotos.toast.ToastCallBackLister;
import com.nimi.sqprotos.toast.Types;


/**
 * 所有activity的基类 ，实现了弹出框，自定义的回调接口
 *
 * @author
 * @version 1.0
 * @date 2017/5/2
 */

public abstract class SActivity extends AppCompatActivity implements ToastManager.ToastManagerListener, CallBackListener {
  
    /**
     * layout转换器
     */
    public LayoutInflater mInflater;

  
    /**
     * 全局应用的环境
     */
    public AppAppliction abApplication = null;
    /**
     * layout的实际父控件
     */
    public RelativeLayout ab_base = null;
    /**
     * ButterKnife
     */
    private MyDialogs mMyDialogs;

    protected ViHolder mHolder;

    public <T> T $(int viewID) {
        return (T) mHolder.get(viewID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SActivityManager.getInstance().addActivity(this);
        mInflater = LayoutInflater.from(this);
        ab_base = new RelativeLayout(this);
        ab_base.setBackgroundColor(Color.rgb(255, 255, 255));

        abApplication = AppAppliction.applictions;
        mHolder = new ViHolder(this);
        if (getLayoutId() > 0) {
            ab_base.removeAllViews();
            ab_base.addView(mInflater.inflate(getLayoutId(), null), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            setContentView(ab_base, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        }
        initData(savedInstanceState);
    }

    public abstract void initData(Bundle savedInstanceState);//初始化数据

    public abstract int getLayoutId();//返回layout的xml

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //   overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }



    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        //  overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    /**
     * 移除注解，和请求
     */
    @Override
    @CallSuper
    protected void onDestroy() {
        SActivityManager.getInstance().finishActivity(this);

        super.onDestroy();
    }



    /**
     * 显示自定义的弹出框 (每个页面的弹出框统一处理毁掉逻辑)
     *
     * @param mess  提示信息
     * @param flag  和回调的getflag对应，用于判断是第几个弹出框
     * @param types 暂时没啥用
     * @param obj   附带数据，不用在activity里面定义变量，防止数据不对或者数据丢失
     */

   /* public void showMess(String mess, int flag, MyToast.Types types, Object obj) {
        ToastBean bean = new ToastBean(mess, types, flag, obj);
        ToastManager.getToastManager().finisCurrent();
        ToastManager.getToastManager().showToast(bean, this);
    }*/
    public void showMess(String mess, int flag, Types types, Object obj) {
        ToastBean bean = new ToastBean(mess, types, flag, obj);
        if (mMyDialogs != null && mMyDialogs.isShow()) {
            mMyDialogs.dissmiss();
            mMyDialogs = null;
        }
        mMyDialogs = new MyDialogs(this, bean, new ToastCallBackLister() {
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
                if (mMyDialogs != null && mMyDialogs.isShow()) {
                    mMyDialogs.dissmiss();
                    mMyDialogs = null;
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
     *
     * @param bean
     */
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                start((ToastBean) msg.obj);
            }
            else if (msg.what == 2) {
                stop((ToastBean) msg.obj);
            }
        }
    };

    @Override
    public void start(ToastBean bean) {

    }

    /**
     * 弹出框的消失时的回调
     *
     * @param bean
     */
    @Override
    public void stop(ToastBean bean) {

    }



    /**
     * 自定义接口的实现方法
     */
    @Override
    public void callBack(long code, long stat, Object value1, Object value2) {

    }

    /**
     * 权限的结果处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 返回键的处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            if (mMyDialogs != null && mMyDialogs.isShow()) {
                mMyDialogs.dissmiss();
                mMyDialogs = null;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
}
