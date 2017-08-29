package com.nimi.sqprotos.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;

import java.util.Set;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * @author
 * @version 1.0
 * @date 2017/8/21
 */

public class RxActionManager {
    private static RxActionManager sInstance = null;
    private ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();
    private ArrayMap<Object, Disposable> maps;

    public static RxActionManager get() {
        if (sInstance == null) {
            synchronized (RxActionManager.class) {
                if (sInstance == null) {
                    sInstance = new RxActionManager();
                }
            }
        }
        return sInstance;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private RxActionManager() {
        maps = new ArrayMap<>();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void add(Object tag, Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            maps.put(tag, disposable);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if (!maps.get(tag).isDisposed()) {
            maps.get(tag).dispose();
            maps.remove(tag);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
