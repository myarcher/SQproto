package com.nimi.sqprotos.net.xuntil;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.manager.AppManager;
import com.nimi.sqprotos.net.base.ApiInfo;
import com.nimi.sqprotos.net.base.HttpType;
import com.nimi.sqprotos.net.base.SHttpLoader;
import com.nimi.sqprotos.until.Units;

import org.apache.http.protocol.HTTP;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public class SXUtilsHttpLoader extends SHttpLoader {
    Callback.Cancelable cancelable;

    private ArrayMap<String, Callback.Cancelable> maps;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public SXUtilsHttpLoader() {
        maps = new ArrayMap<>();
    }

    @Override
    public void load(ApiInfo apiInfo, HttpType type, Map<String, Object> map, SHttpBackListener listener) {
        String url = apiInfo.url;
        url = checkUlr(url);
        RequestParams params = getParams(url, map);
        Callback.ProgressCallback callback;
        if (type == HttpType.GET) {
            callback = new SProgressCallback<String>(apiInfo, listener);
            cancelable = x.http().get(params, callback);
        } else if (type == HttpType.POST) {
            callback = new SProgressCallback<String>(apiInfo, listener);
            cancelable = x.http().post(params, callback);
        } else if (type == HttpType.UP) {
            params.setMultipart(true);
            callback = new SProgressCallback<File>(apiInfo, listener);
            cancelable = x.http().post(params, callback);
        } else if (type == HttpType.DOWN) {
            params = new RequestParams(url);
            params.setAutoResume(false);
            params.setCancelFast(false);
            params.setAutoResume(true);
            params.setSaveFilePath(apiInfo.save_path);
            callback = new SProgressCallback<String>(apiInfo, listener);
            cancelable = x.http().post(params, callback);
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void cancel(String url) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(url) == null) {
            return;
        }
        if (!maps.get(url).isCancelled()) {
            maps.get(url).cancel();
            maps.remove(url);
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void cancelAll(List<String> keys) {
        if (maps.isEmpty()) {
            return;
        }
        for (String apiKey : keys) {
            cancel(apiKey);
        }
    }
    
    private static RequestParams getParams(String url, Map<String, Object> map) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(AppManager.getInstance().getTime_out());
        params.setCharset(HTTP.UTF_8);
        params.setMaxRetryCount(AppManager.getInstance().getRetry_count());
        //  params.setRequestProperty("Accept-Encoding", "identity");
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        return params;
    }
}
