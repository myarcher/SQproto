package com.nimi.sqprotos.toast;

import com.nimi.sqprotos.bean.ToastBean;

/**
 * @author
 * @version 1.0
 * @date 2017/8/23
 */

public interface ToastCallBackLister {
    public void beginClick(ToastBean bean);
    public void forwordclick(ToastBean bean);
}
