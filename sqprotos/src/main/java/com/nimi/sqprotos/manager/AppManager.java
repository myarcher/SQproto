package com.nimi.sqprotos.manager;

import android.app.Application;

import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.net.retrofit.SClitent;

import org.xutils.x;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public class AppManager {
    public   String LOG_TAG ="" ;
    private static Application app;
    private String BHTPP_URL = "";
    private int time_out = 3000;
    private int retry_count = 1;

    private volatile static AppManager appManager = null;
    public boolean isDebug = true;
    public SClitent mBClient;

    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    public static void init(Application apps) {
        getInstance();
        app = apps;
        x.Ext.init(apps);
    }

    public void build() {
        x.Ext.setDebug(isDebug);
        mBClient = SClitent.getInstance();
    }


    public String getBHTPP_URL() {
        return BHTPP_URL;
    }

    public void setBHTPP_URL(String BHTPP_URL) {
        this.BHTPP_URL = BHTPP_URL;
    }

    public int getTime_out() {
        return time_out;
    }

    public void setTime_out(int time_out) {
        this.time_out = time_out;
    }

    public int getRetry_count() {
        return retry_count;
    }

    public void setRetry_count(int retry_count) {
        this.retry_count = retry_count;
    }
}
