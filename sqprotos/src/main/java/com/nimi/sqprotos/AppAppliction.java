package com.nimi.sqprotos;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.net.retrofit.SClitent;
import com.nimi.sqprotos.until.ShareUntil;

import org.xutils.x;

import java.util.List;

/**
 * @author
 * @version 1.0
 * @date 2017/4/19
 */

public class AppAppliction extends Application {
    public static AppAppliction applictions;
    public static ShareUntil until;
 
  
    @Override
    public void onCreate() {
        super.onCreate();
        applictions = this;
        until = ShareUntil.getInstance(BaseContanse.SHARE_NAME, this);
    }

    
    
}
