package com.nimi.sqprotos.net.base;

import com.nimi.sqprotos.net.retrofit.SRetrofitHttpLoader;
import com.nimi.sqprotos.net.xuntil.SXUtilsHttpLoader;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public class SHttpManager {
    private static SHttpLoader sHttpLoader;

    private SHttpManager() {
    }

    private static final SHttpLoader getsHttpLoader() {
        if (sHttpLoader == null) {
            synchronized (SHttpManager.class) {
                if (sHttpLoader == null) {
                    if (isClassExists("org.xutils.x")) {
                        sHttpLoader = new SXUtilsHttpLoader();
                    }else if(isClassExists("retrofit2.Retrofit")){
                        sHttpLoader=new SRetrofitHttpLoader();
                    }else {
                        throw new RuntimeException("必须在你的 build.gradle 文件中配置「XUtils3」中的某一个网络加载库的依赖,或者检查是否添加了图库的混淆配置");
                    }
                }
            }
        }
        return sHttpLoader;
    }

    private static final boolean isClassExists(String classFullName) {
        try {
            Class.forName(classFullName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    
    public static void http(ApiInfo apiInfo, HttpType type, Map<String,Object> params, SHttpLoader.SHttpBackListener listener) {
        getsHttpLoader().load(apiInfo,type,params,listener);
    }
    public static void cancel(String url){
        getsHttpLoader().cancel(url);
    }

    public static void cancelAll(List<String> urls) {
        getsHttpLoader().cancelAll(urls);
    }
}
