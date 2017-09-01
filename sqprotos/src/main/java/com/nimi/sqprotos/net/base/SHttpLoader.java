package com.nimi.sqprotos.net.base;


import com.nimi.sqprotos.manager.AppManager;

import org.xutils.common.Callback;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public abstract class SHttpLoader {
    public abstract void load(ApiInfo apiInfo,HttpType type,Map<String,Object> params,SHttpBackListener listener);
  
    public abstract void cancel(String url);
    public abstract void cancelAll(List<String> urls);
    
    public interface SHttpBackListener {
        void onfinish(ApiInfo apiInfo);
        void onstart(ApiInfo apiInfo);
        void onLoad(ApiInfo apiInfo);
        void onCancel(ApiInfo apiInfo);
    }
    public static String checkUlr(String url) {
        if (url.startsWith(".")) {
            url = url.substring(1, url.length());
        }
        if (!url.startsWith("/")) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "/" + url;
                url = AppManager.getInstance().getBHTPP_URL() + url;
            }
        } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = AppManager.getInstance().getBHTPP_URL()+ url;
        }
        return url;
    } 
    
}
