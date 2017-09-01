package com.nimi.sqprotos.net.xuntil;

import com.nimi.sqprotos.net.base.ApiInfo;
import com.nimi.sqprotos.net.base.SHttpLoader;

import org.xutils.common.Callback;

import java.io.File;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public class SProgressCallback<T> implements Callback.ProgressCallback<T> {
    private ApiInfo apiInfo;
    private SHttpLoader.SHttpBackListener listener;
    public SProgressCallback( ApiInfo apiInfo,SHttpLoader.SHttpBackListener listener){
        this.apiInfo=apiInfo;
        this.listener=listener;
    }
    @Override
    public void onWaiting() {
    }
    @Override
    public void onStarted() {
        listener.onstart(apiInfo);
    }
    @Override
    public void onLoading(long total, long current, boolean b) {
        apiInfo.current=current;
        apiInfo.total=total;
        listener.onLoad(apiInfo);
    }
 

    @Override
    public void onSuccess(T t) {
        if(apiInfo.isfile){
            apiInfo.file= (File) t; 
        }else{
            apiInfo.json= (String) t;
        }
        apiInfo.isSuccess=true;
        listener.onfinish(apiInfo);
    }

    @Override
    public void onError(Throwable throwable, boolean b) {
        apiInfo.isSuccess=false;
        apiInfo.throwable=throwable;
        listener.onfinish(apiInfo);
    }
    @Override
    public void onCancelled(CancelledException e) {

    }
    @Override
    public void onFinished() {

    }
}
