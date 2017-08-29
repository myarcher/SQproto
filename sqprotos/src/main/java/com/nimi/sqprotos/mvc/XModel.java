package com.nimi.sqprotos.mvc;

import com.nimi.sqprotos.net.base.ApiInfo;
import com.nimi.sqprotos.net.base.HttpType;
import com.nimi.sqprotos.net.base.SHttpLoader;
import com.nimi.sqprotos.net.base.SHttpManager;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author
 * @version 1.0
 * @date 2017/7/10
 */

public class XModel {
    ApiInfo apiInfo;
    HttpType type;
    Map<String, Object> params;
    SHttpLoader.SHttpBackListener listener;

    public Map<String, Object> getParams() {
        if(params==null){
            params=new HashMap<>();
        }
        return params;
    }

    public void builder() {
        if(apiInfo!=null) {
            SHttpManager.http(apiInfo, type, params, listener);
        }
    }

    public XModel setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
        return this;
    }

    public XModel setType(HttpType type) {
        this.type = type;
        return this;
    }

    public XModel setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public XModel setListener(SHttpLoader.SHttpBackListener listener) {
        this.listener = listener;
        return this;
    }
    
    public XModel setPosi(int posi){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.posi=posi;
        return this;
    }
    public XModel setIsFile(boolean isFile){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.isfile=isFile;
        return this;
    }
    public XModel set(int posi){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.posi=posi;
        return this;
    }
    public XModel setStart(boolean start){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.start=start;
        return this;
    }
    public XModel setStop(boolean stop){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.stop=stop;
        return this;
    }
    public XModel setSavepath(String save_path){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.save_path=save_path;
        return this;
    }
  
    public XModel setCall(Call mCall){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.mCall=mCall;
        return this;
    }
    public XModel setUrl(String url){
        if(apiInfo==null){
            apiInfo=new ApiInfo();
        }
        apiInfo.url=url;
        return this;
    }
    public XModel putPara(String key,Object value){
        if(params==null){
            params=new HashMap<>();
        }
        params.put(key,value);
        return this;
    }
    public void cancel(String url){
        SHttpManager.cancel(url);
    }
    public void cancel(List<String> urls){
        SHttpManager.cancelAll(urls);
    }
}
