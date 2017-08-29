package com.nimi.sqprotos.manager;

import java.util.LinkedList;
import java.util.Queue;

import android.os.Handler;
import android.os.Message;

import com.nimi.sqprotos.base.SActivity;
import com.nimi.sqprotos.bean.ToastBean;
import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.toast.MyToast;
import com.nimi.sqprotos.toast.ToastCallBackLister;
import com.nimi.sqprotos.toast.Types;

/**
 * 自定义提示dialog的管理类
 * 
 */
public class ToastManager {
    private static ToastManager toastManager;
    private Queue<MyToast> toasts;//使用队列的方式管理弹出框

    /***
     * 确保是单例
     * @return
     */
    public static ToastManager getToastManager() {
        if (toastManager == null) {
            toastManager = new ToastManager();
        }
        return toastManager;
    }

    private ToastManager() {
        toasts = new LinkedList<MyToast>();
    }

    public MyToast showToast(String title, Types type, int flag, Object obj, ToastManagerListener lister) {
        return addToast(new ToastBean(title, type, flag, obj), lister);
    }

    public MyToast showOkToast(String title, int flag, Object obj, ToastManagerListener lister) {
        return addToast(new ToastBean(title, Types.OK, flag, obj), lister);
    }

    public MyToast showErrToast(String title, int flag, Object obj, ToastManagerListener lister) {
        return addToast(new ToastBean(title, Types.ERREY, flag, obj), lister);
    }

    public MyToast showGOToast(int flag, ToastManagerListener lister) {
        return addToast(new ToastBean(null, Types.GO, flag, null), lister);
    }

    public MyToast showNotiToast(String title, int flag, Object obj, ToastManagerListener lister) {
        return addToast(new ToastBean(title, Types.NOTI, flag, obj), lister);
    }

    public MyToast showToast(ToastBean bean, final ToastManagerListener lister) {
        return addToast(bean, lister);
    }

    /***
     * 提示框的显示逻辑 显示弹出框
     * @param bean  显示框显示的数据和回调附带的数据
     * @param lister 回调接口
     * @return  当前显得dialog
     */
    private MyToast addToast(final ToastBean bean, final ToastManagerListener lister) {
        try {
            SActivity activity = SActivityManager.getInstance().currentActivity();

            /**
             * 保证回调的处理逻辑在主线程
             */
            final Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        lister.start((ToastBean) msg.obj);
                    } else if (msg.what == 2) {
                        lister.stop((ToastBean) msg.obj);
                    }
                }
            };
           ToastCallBackLister backLister = new ToastCallBackLister() {

                /***
                 * dialog消失后的回调
                 * @param beans 传递的数据
                 */
                @Override
                public void forwordclick(ToastBean beans) {
                    MyToast toast = toasts.poll();
                    if (toast != null) {
                        toast.dissmiss();
                        toast = null;
                    }
                    if (beans.getFlag() != BaseContanse.MYTOAST_ER) {//默认情况-1是进行回调处理，只是显示，下面判断相同
                        Message message = new Message();
                        message.what = 2;
                        message.obj = beans;
                        mHandler.sendMessage(message);
                    }
                }

                /**
                 * 显示前回调
                 * @param beans 构建前的传递数据
                 */
                @Override
                public void beginClick(ToastBean beans) {
                    if (beans.getFlag() != BaseContanse.MYTOAST_ER) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = beans;
                        mHandler.sendMessage(message);
                    }
                }
            };
            MyToast myToast = new MyToast(activity, bean, backLister);
            toasts.offer(myToast);
            myToast.show();
            return myToast;

        } catch (Exception E) {
         return null;
        }
    }

    public MyToast getCurrent() {//获取队列的第一个弹出框
        return toasts.peek();
    }

    public void finisCurrent() {//从队列中移除当前显示的弹出框
        MyToast toast = toasts.poll();
        if (toast != null) {
            toast.dissmiss();
        }
    }

    public interface ToastManagerListener {//自定义的回调的处理逻辑的接口
        public void start(ToastBean bean);

        public void stop(ToastBean bean);
    }


}
